/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import info.nordbyen.Ziputils.commons.FileUtils;
import info.nordbyen.Ziputils.commons.FilenameUtils;
import info.nordbyen.Ziputils.commons.IOUtils;
import info.nordbyen.Ziputils.transform.ZipEntryTransformer;
import info.nordbyen.Ziputils.transform.ZipEntryTransformerEntry;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipUtil.
 */
public final class ZipUtil {

	/**
	 * The Class ByteArrayUnpacker.
	 */
	private static class ByteArrayUnpacker implements ZipEntryCallback {

		/** The bytes. */
		private byte[] bytes;

		/**
		 * Gets the bytes.
		 *
		 * @return the bytes
		 */
		public byte[] getBytes() {
			return bytes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			bytes = IOUtils.toByteArray(in);
		}
	}

	/**
	 * The Class FileUnpacker.
	 */
	private static class FileUnpacker implements ZipEntryCallback {

		/** The file. */
		private final File file;

		/**
		 * Instantiates a new file unpacker.
		 *
		 * @param file
		 *            the file
		 */
		public FileUnpacker(File file) {
			this.file = file;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			FileUtils.copy(in, file);
		}
	}

	// Use / instead of . to work around an issue with Maven Shade Plugin
	// private static final Logger log =
	// LoggerFactory.getLogger("org/zeroturnaround/zip/ZipUtil".replace('/',
	// '.')); // NOSONAR
	/**
	 * The Class InPlaceAction.
	 */
	private abstract static class InPlaceAction {

		/**
		 * Act.
		 *
		 * @param tmpFile
		 *            the tmp file
		 * @return true, if successful
		 */
		abstract boolean act(File tmpFile);
	}

	/* Extracting single entries from ZIP files. */
	/**
	 * The Class RepackZipEntryCallback.
	 */
	private static final class RepackZipEntryCallback implements
			ZipEntryCallback {

		/** The out. */
		private ZipOutputStream out;

		/**
		 * Instantiates a new repack zip entry callback.
		 *
		 * @param dstZip
		 *            the dst zip
		 * @param compressionLevel
		 *            the compression level
		 */
		private RepackZipEntryCallback(File dstZip, int compressionLevel) {
			try {
				this.out = new ZipOutputStream(new BufferedOutputStream(
						new FileOutputStream(dstZip)));
				this.out.setLevel(compressionLevel);
			} catch (IOException e) {
				ZipExceptionUtil.rethrow(e);
			}
		}

		/**
		 * Close stream.
		 */
		private void closeStream() {
			IOUtils.closeQuietly(out);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			ZipEntryUtil.copyEntry(zipEntry, in, out);
		}
	}

	/**
	 * The Class SingleZipEntryCallback.
	 */
	private static class SingleZipEntryCallback implements ZipEntryCallback {

		/** The name. */
		private final String name;
		
		/** The action. */
		private final ZipEntryCallback action;
		
		/** The found. */
		private boolean found;

		/**
		 * Instantiates a new single zip entry callback.
		 *
		 * @param name
		 *            the name
		 * @param action
		 *            the action
		 */
		public SingleZipEntryCallback(String name, ZipEntryCallback action) {
			this.name = name;
			this.action = action;
		}

		/**
		 * Found.
		 *
		 * @return true, if successful
		 */
		public boolean found() {
			return found;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			if (name.equals(zipEntry.getName())) {
				found = true;
				action.process(in, zipEntry);
			}
		}
	}

	/**
	 * The Class TransformerZipEntryCallback.
	 */
	private static class TransformerZipEntryCallback implements
			ZipEntryCallback {

		/** The entry by path. */
		private final Map<String, ZipEntryTransformer> entryByPath;
		
		/** The entry count. */
		private final int entryCount;
		
		/** The out. */
		private final ZipOutputStream out;
		
		/** The names. */
		private final Set<String> names = new HashSet<String>();

		/**
		 * Instantiates a new transformer zip entry callback.
		 *
		 * @param entries
		 *            the entries
		 * @param out
		 *            the out
		 */
		public TransformerZipEntryCallback(ZipEntryTransformerEntry[] entries,
				ZipOutputStream out) {
			entryByPath = byPath(entries);
			entryCount = entryByPath.size();
			this.out = out;
		}

		/**
		 * Found.
		 *
		 * @return true, if successful
		 */
		public boolean found() {
			return entryByPath.size() < entryCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			if (names.add(zipEntry.getName())) {
				ZipEntryTransformer entry = entryByPath.remove(zipEntry
						.getName());
				if (entry != null) {
					entry.transform(in, zipEntry, out);
				} else {
					ZipEntryUtil.copyEntry(zipEntry, in, out);
				}
			}
			// else if (log.isDebugEnabled()) {
			// log.debug("Duplicate entry: {}", zipEntry.getName());
			// }
		}
	}

	/**
	 * The Class Unpacker.
	 */
	private static class Unpacker implements ZipEntryCallback {

		/** The output dir. */
		private final File outputDir;
		
		/** The mapper. */
		private final NameMapper mapper;

		/**
		 * Instantiates a new unpacker.
		 *
		 * @param outputDir
		 *            the output dir
		 * @param mapper
		 *            the mapper
		 */
		public Unpacker(File outputDir, NameMapper mapper) {
			this.outputDir = outputDir;
			this.mapper = mapper;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			String name = mapper.map(zipEntry.getName());
			if (name != null) {
				File file = new File(outputDir, name);
				if (zipEntry.isDirectory()) {
					FileUtils.forceMkdir(file);
				} else {
					FileUtils.forceMkdir(file.getParentFile());
					// if (log.isDebugEnabled() && file.exists()) {
					// log.debug("Overwriting file '{}'.", zipEntry.getName());
					// }
					FileUtils.copy(in, file);
				}
			}
		}
	}

	/**
	 * The Class Unwraper.
	 */
	private static class Unwraper implements ZipEntryCallback {

		/** The output dir. */
		private final File outputDir;
		
		/** The mapper. */
		private final NameMapper mapper;
		
		/** The root dir. */
		private String rootDir;

		/**
		 * Instantiates a new unwraper.
		 *
		 * @param outputDir
		 *            the output dir
		 * @param mapper
		 *            the mapper
		 */
		public Unwraper(File outputDir, NameMapper mapper) {
			this.outputDir = outputDir;
			this.mapper = mapper;
		}

		/**
		 * Gets the root name.
		 *
		 * @param name
		 *            the name
		 * @return the root name
		 */
		private String getRootName(final String name) {
			String newName = name
					.substring(FilenameUtils.getPrefixLength(name));
			int idx = newName.indexOf(PATH_SEPARATOR);
			if (idx < 0) {
				throw new ZipException("Entry " + newName
						+ " from the root of the zip is not supported");
			}
			return newName.substring(0, newName.indexOf(PATH_SEPARATOR));
		}

		/**
		 * Gets the unrooted name.
		 *
		 * @param root
		 *            the root
		 * @param name
		 *            the name
		 * @return the unrooted name
		 */
		private String getUnrootedName(String root, String name) {
			return name.substring(root.length());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
		 * java.util.zip.ZipEntry)
		 */
		@Override
		public void process(InputStream in, ZipEntry zipEntry)
				throws IOException {
			String root = getRootName(zipEntry.getName());
			if (rootDir == null) {
				rootDir = root;
			} else if (!rootDir.equals(root)) {
				throw new ZipException(
						"Unwrapping with multiple roots is not supported, roots: "
								+ rootDir + ", " + root);
			}
			String name = mapper.map(getUnrootedName(root, zipEntry.getName()));
			if (name != null) {
				File file = new File(outputDir, name);
				if (zipEntry.isDirectory()) {
					FileUtils.forceMkdir(file);
				} else {
					FileUtils.forceMkdir(file.getParentFile());
					// if (log.isDebugEnabled() && file.exists()) {
					// log.debug("Overwriting file '{}'.", zipEntry.getName());
					// }
					FileUtils.copy(in, file);
				}
			}
		}
	}

	/**
	 * Adds the entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 */
	public static void addEntries(final File zip, final ZipEntrySource[] entries) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				addEntries(zip, entries, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Adds the entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @param destZip
	 *            the dest zip
	 */
	public static void addEntries(File zip, ZipEntrySource[] entries,
			File destZip) {
		// if (log.isDebugEnabled()) {
		// log.debug("Copying '" + zip + "' to '" + destZip + "' and adding " +
		// Arrays.asList(entries) + ".");
		// }
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(destZip)));
			copyEntries(zip, out);
			for (int i = 0; i < entries.length; i++) {
				addEntry(entries[i], out);
			}
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 */
	public static void addEntry(final File zip, final String path,
			final byte[] bytes) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				addEntry(zip, path, bytes, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 * @param destZip
	 *            the dest zip
	 */
	public static void addEntry(File zip, String path, byte[] bytes,
			File destZip) {
		addEntry(zip, new ByteSource(path, bytes), destZip);
	}

	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param file
	 *            the file
	 */
	public static void addEntry(final File zip, final String path,
			final File file) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				addEntry(zip, path, file, tmpFile);
				return true;
			}
		});
	}

	/* Traversing ZIP files */
	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param file
	 *            the file
	 * @param destZip
	 *            the dest zip
	 */
	public static void addEntry(File zip, String path, File file, File destZip) {
		addEntry(zip, new FileSource(path, file), destZip);
	}

	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 */
	public static void addEntry(final File zip, final ZipEntrySource entry) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				addEntry(zip, entry, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Adds the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 * @param destZip
	 *            the dest zip
	 */
	public static void addEntry(File zip, ZipEntrySource entry, File destZip) {
		addEntries(zip, new ZipEntrySource[] { entry }, destZip);
	}

	/**
	 * Adds the entry.
	 *
	 * @param entry
	 *            the entry
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addEntry(ZipEntrySource entry, ZipOutputStream out)
			throws IOException {
		out.putNextEntry(entry.getEntry());
		InputStream in = entry.getInputStream();
		if (in != null) {
			try {
				IOUtils.copy(in, out);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
		out.closeEntry();
	}

	/**
	 * Adds the or replace entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 */
	public static void addOrReplaceEntries(final File zip,
			final ZipEntrySource[] entries) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				addOrReplaceEntries(zip, entries, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Adds the or replace entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @param destZip
	 *            the dest zip
	 */
	public static void addOrReplaceEntries(File zip, ZipEntrySource[] entries,
			File destZip) {
		// if (log.isDebugEnabled()) {
		// log.debug("Copying '" + zip + "' to '" + destZip +
		// "' and adding/replacing entries " + Arrays.asList(entries)
		// + ".");
		// }
		final Map<String, ZipEntrySource> entryByPath = byPath(entries);
		try {
			final ZipOutputStream out = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(destZip)));
			try {
				// Copy and replace entries
				final Set<String> names = new HashSet<String>();
				iterate(zip, new ZipEntryCallback() {

					@Override
					public void process(InputStream in, ZipEntry zipEntry)
							throws IOException {
						if (names.add(zipEntry.getName())) {
							ZipEntrySource entry = entryByPath.remove(zipEntry
									.getName());
							if (entry != null) {
								addEntry(entry, out);
							} else {
								ZipEntryUtil.copyEntry(zipEntry, in, out);
							}
						}
						// else if (log.isDebugEnabled()) {
						// log.debug("Duplicate entry: {}", zipEntry.getName());
						// }
					}
				});
				// Add new entries
				for (Iterator<ZipEntrySource> it = entryByPath.values()
						.iterator(); it.hasNext();) {
					addEntry(it.next(), out);
				}
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Archive equals.
	 *
	 * @param f1
	 *            the f1
	 * @param f2
	 *            the f2
	 * @return true, if successful
	 */
	public static boolean archiveEquals(File f1, File f2) {
		try {
			// Check the files byte-by-byte
			if (FileUtils.contentEquals(f1, f2)) {
				return true;
			}
			// log.debug("Comparing archives '{}' and '{}'...", f1, f2);
			long start = System.currentTimeMillis();
			boolean result = archiveEqualsInternal(f1, f2);
			long time = System.currentTimeMillis() - start;
			if (time > 0) {
				// log.debug("Archives compared in " + time + " ms.");
			}
			return result;
		} catch (Exception e) {
			// log.debug("Could not compare '" + f1 + "' and '" + f2 + "':", e);
			return false;
		}
	}

	/**
	 * Archive equals internal.
	 *
	 * @param f1
	 *            the f1
	 * @param f2
	 *            the f2
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("resource")
	private static boolean archiveEqualsInternal(File f1, File f2)
			throws IOException {
		ZipFile zf1 = null;
		ZipFile zf2 = null;
		try {
			zf1 = new ZipFile(f1);
			zf2 = new ZipFile(f2);
			// Check the number of entries
			if (zf1.size() != zf2.size()) {
				// log.debug("Number of entries changed (" + zf1.size() + " vs "
				// + zf2.size() + ").");
				return false;
			}
			/*
			 * As there are same number of entries in both archives we can
			 * traverse all entries of one of the archives and get the
			 * corresponding entries from the other archive.
			 * 
			 * If a corresponding entry is missing from the second archive the
			 * archives are different and we finish the comparison.
			 * 
			 * We guarantee that no entry of the second archive is skipped as
			 * there are same number of unique entries in both archives.
			 */
			Enumeration<?> en = zf1.entries();
			while (en.hasMoreElements()) {
				ZipEntry e1 = (ZipEntry) en.nextElement();
				String path = e1.getName();
				ZipEntry e2 = zf2.getEntry(path);
				// Check meta data
				if (!metaDataEquals(path, e1, e2)) {
					return false;
				}
				// Check the content
				InputStream is1 = null;
				InputStream is2 = null;
				try {
					is1 = zf1.getInputStream(e1);
					is2 = zf2.getInputStream(e2);
					if (!IOUtils.contentEquals(is1, is2)) {
						// log.debug("Entry '{}' content changed.", path);
						return false;
					}
				} finally {
					IOUtils.closeQuietly(is1);
					IOUtils.closeQuietly(is2);
				}
			}
		} finally {
			closeQuietly(zf1);
			closeQuietly(zf2);
		}
		// log.debug("Archives are the same.");
		return true;
	}

	/**
	 * By path.
	 *
	 * @param entries
	 *            the entries
	 * @return the map
	 */
	static Map<String, ZipEntrySource> byPath(Collection<?> entries) {
		Map<String, ZipEntrySource> result = new HashMap<String, ZipEntrySource>();
		Iterator<?> iter = entries.iterator();
		while (iter.hasNext()) {
			ZipEntrySource source = (ZipEntrySource) iter.next();
			result.put(source.getPath(), source);
		}
		return result;
	}

	/**
	 * By path.
	 *
	 * @param entries
	 *            the entries
	 * @return the map
	 */
	static Map<String, ZipEntrySource> byPath(ZipEntrySource[] entries) {
		Map<String, ZipEntrySource> result = new HashMap<String, ZipEntrySource>();
		for (int i = 0; i < entries.length; i++) {
			ZipEntrySource source = entries[i];
			result.put(source.getPath(), source);
		}
		return result;
	}

	/**
	 * By path.
	 *
	 * @param entries
	 *            the entries
	 * @return the map
	 */
	static Map<String, ZipEntryTransformer> byPath(
			ZipEntryTransformerEntry[] entries) {
		Map<String, ZipEntryTransformer> result = new HashMap<String, ZipEntryTransformer>();
		for (int i = 0; i < entries.length; i++) {
			ZipEntryTransformerEntry entry = entries[i];
			result.put(entry.getPath(), entry.getTransformer());
		}
		return result;
	}

	/* Extracting whole ZIP files. */
	/**
	 * Close quietly.
	 *
	 * @param zf
	 *            the zf
	 */
	public static void closeQuietly(ZipFile zf) {
		try {
			if (zf != null) {
				zf.close();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Contains any entry.
	 *
	 * @param zip
	 *            the zip
	 * @param names
	 *            the names
	 * @return true, if successful
	 */
	@SuppressWarnings("resource")
	public static boolean containsAnyEntry(File zip, String[] names) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			for (int i = 0; i < names.length; i++) {
				if (zf.getEntry(names[i]) != null) {
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Contains entry.
	 *
	 * @param zip
	 *            the zip
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public static boolean containsEntry(File zip, String name) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			return zf.getEntry(name) != null;
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Copy entries.
	 *
	 * @param zip
	 *            the zip
	 * @param out
	 *            the out
	 */
	private static void copyEntries(File zip, final ZipOutputStream out) {
		// this one doesn't call copyEntries with ignoredEntries, because that
		// has poorer performance
		final Set<String> names = new HashSet<String>();
		iterate(zip, new ZipEntryCallback() {

			@Override
			public void process(InputStream in, ZipEntry zipEntry)
					throws IOException {
				String entryName = zipEntry.getName();
				if (names.add(entryName)) {
					ZipEntryUtil.copyEntry(zipEntry, in, out);
				}
				// else if (log.isDebugEnabled()) {
				// log.debug("Duplicate entry: {}", entryName);
				// }
			}
		});
	}

	/**
	 * Copy entries.
	 *
	 * @param zip
	 *            the zip
	 * @param out
	 *            the out
	 * @param ignoredEntries
	 *            the ignored entries
	 */
	private static void copyEntries(File zip, final ZipOutputStream out,
			final Set<String> ignoredEntries) {
		final Set<String> names = new HashSet<String>();
		final Set<String> dirNames = filterDirEntries(zip, ignoredEntries);
		iterate(zip, new ZipEntryCallback() {

			@Override
			public void process(InputStream in, ZipEntry zipEntry)
					throws IOException {
				String entryName = zipEntry.getName();
				if (ignoredEntries.contains(entryName)) {
					return;
				}
				Iterator<String> iter = dirNames.iterator();
				while (iter.hasNext()) {
					String dirName = iter.next();
					if (entryName.startsWith(dirName)) {
						return;
					}
				}
				if (names.add(entryName)) {
					ZipEntryUtil.copyEntry(zipEntry, in, out);
				}
				// else if (log.isDebugEnabled()) {
				// log.debug("Duplicate entry: {}", entryName);
				// }
			}
		});
	}

	/**
	 * Do entry equals.
	 *
	 * @param zf1
	 *            the zf1
	 * @param zf2
	 *            the zf2
	 * @param path1
	 *            the path1
	 * @param path2
	 *            the path2
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static boolean doEntryEquals(ZipFile zf1, ZipFile zf2,
			String path1, String path2) throws IOException {
		InputStream is1 = null;
		InputStream is2 = null;
		try {
			ZipEntry e1 = zf1.getEntry(path1);
			ZipEntry e2 = zf2.getEntry(path2);
			if (e1 == null && e2 == null) {
				return true;
			}
			if (e1 == null || e2 == null) {
				return false;
			}
			is1 = zf1.getInputStream(e1);
			is2 = zf2.getInputStream(e2);
			if (is1 == null && is2 == null) {
				return true;
			}
			if (is1 == null || is2 == null) {
				return false;
			}
			return IOUtils.contentEquals(is1, is2);
		} finally {
			IOUtils.closeQuietly(is1);
			IOUtils.closeQuietly(is2);
		}
	}

	/**
	 * Do unpack entry.
	 *
	 * @param zf
	 *            the zf
	 * @param name
	 *            the name
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static byte[] doUnpackEntry(ZipFile zf, String name)
			throws IOException {
		ZipEntry ze = zf.getEntry(name);
		if (ze == null) {
			return null; // entry not found
		}
		InputStream is = zf.getInputStream(ze);
		try {
			return IOUtils.toByteArray(is);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Do unpack entry.
	 *
	 * @param zf
	 *            the zf
	 * @param name
	 *            the name
	 * @param file
	 *            the file
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static boolean doUnpackEntry(ZipFile zf, String name, File file)
			throws IOException {
		// if (log.isTraceEnabled()) {
		// log.trace("Extracting '" + zf.getName() + "' entry '" + name +
		// "' into '" + file + "'.");
		// }
		ZipEntry ze = zf.getEntry(name);
		if (ze == null) {
			return false; // entry not found
		}
		InputStream in = new BufferedInputStream(zf.getInputStream(ze));
		try {
			FileUtils.copy(in, file);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return true;
	}

	/**
	 * Entry equals.
	 *
	 * @param f1
	 *            the f1
	 * @param f2
	 *            the f2
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	public static boolean entryEquals(File f1, File f2, String path) {
		return entryEquals(f1, f2, path, path);
	}

	/**
	 * Entry equals.
	 *
	 * @param f1
	 *            the f1
	 * @param f2
	 *            the f2
	 * @param path1
	 *            the path1
	 * @param path2
	 *            the path2
	 * @return true, if successful
	 */
	public static boolean entryEquals(File f1, File f2, String path1,
			String path2) {
		ZipFile zf1 = null;
		ZipFile zf2 = null;
		try {
			zf1 = new ZipFile(f1);
			zf2 = new ZipFile(f2);
			return doEntryEquals(zf1, zf2, path1, path2);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf1);
			closeQuietly(zf2);
		}
	}

	/**
	 * Entry equals.
	 *
	 * @param zf1
	 *            the zf1
	 * @param zf2
	 *            the zf2
	 * @param path1
	 *            the path1
	 * @param path2
	 *            the path2
	 * @return true, if successful
	 */
	public static boolean entryEquals(ZipFile zf1, ZipFile zf2, String path1,
			String path2) {
		try {
			return doEntryEquals(zf1, zf2, path1, path2);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/* Compressing single entries to ZIP files. */
	/**
	 * Explode.
	 *
	 * @param zip
	 *            the zip
	 */
	public static void explode(File zip) {
		try {
			// Find a new unique name is the same directory
			File tempFile = FileUtils.getTempFileFor(zip);
			// Rename the archive
			FileUtils.moveFile(zip, tempFile);
			// Unpack it
			unpack(tempFile, zip);
			// Delete the archive
			if (!tempFile.delete()) {
				throw new IOException("Unable to delete file: " + tempFile);
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/* Compressing ZIP files. */
	/**
	 * Filter dir entries.
	 *
	 * @param zip
	 *            the zip
	 * @param names
	 *            the names
	 * @return the sets the
	 */
	static Set<String> filterDirEntries(File zip, Collection<String> names) {
		Set<String> dirs = new HashSet<String>();
		if (zip == null) {
			return dirs;
		}
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			Iterator<String> iterator = names.iterator();
			while (iterator.hasNext()) {
				String entryName = iterator.next();
				ZipEntry entry = zf.getEntry(entryName);
				if (entry.isDirectory()) {
					dirs.add(entry.getName());
				} else if (zf.getInputStream(entry) == null) {
					// no input stream means that this is a dir.
					dirs.add(entry.getName() + PATH_SEPARATOR);
				}
			}
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
		return dirs;
	}

	/**
	 * Handle.
	 *
	 * @param zip
	 *            the zip
	 * @param name
	 *            the name
	 * @param action
	 *            the action
	 * @return true, if successful
	 */
	public static boolean handle(File zip, String name, ZipEntryCallback action) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			ZipEntry ze = zf.getEntry(name);
			if (ze == null) {
				return false; // entry not found
			}
			InputStream in = new BufferedInputStream(zf.getInputStream(ze));
			try {
				action.process(in, ze);
			} finally {
				IOUtils.closeQuietly(in);
			}
			return true;
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Handle.
	 *
	 * @param is
	 *            the is
	 * @param name
	 *            the name
	 * @param action
	 *            the action
	 * @return true, if successful
	 */
	public static boolean handle(InputStream is, String name,
			ZipEntryCallback action) {
		SingleZipEntryCallback helper = new SingleZipEntryCallback(name, action);
		iterate(is, helper);
		return helper.found();
	}

	/**
	 * Iterate.
	 *
	 * @param zip
	 *            the zip
	 * @param entryNames
	 *            the entry names
	 * @param action
	 *            the action
	 */
	public static void iterate(File zip, String[] entryNames,
			ZipEntryCallback action) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			for (int i = 0; i < entryNames.length; i++) {
				ZipEntry e = zf.getEntry(entryNames[i]);
				if (e == null) {
					continue;
				}
				InputStream is = zf.getInputStream(e);
				try {
					action.process(is, e);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ e.getName() + " with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Iterate.
	 *
	 * @param zip
	 *            the zip
	 * @param entryNames
	 *            the entry names
	 * @param action
	 *            the action
	 */
	@SuppressWarnings("resource")
	public static void iterate(File zip, String[] entryNames,
			ZipInfoCallback action) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			for (int i = 0; i < entryNames.length; i++) {
				ZipEntry e = zf.getEntry(entryNames[i]);
				if (e == null) {
					continue;
				}
				try {
					action.process(e);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ e.getName() + " with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Iterate.
	 *
	 * @param zip
	 *            the zip
	 * @param action
	 *            the action
	 */
	public static void iterate(File zip, ZipEntryCallback action) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			Enumeration<?> en = zf.entries();
			while (en.hasMoreElements()) {
				ZipEntry e = (ZipEntry) en.nextElement();
				InputStream is = zf.getInputStream(e);
				try {
					action.process(is, e);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ e.getName() + "' with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Iterate.
	 *
	 * @param zip
	 *            the zip
	 * @param action
	 *            the action
	 */
	@SuppressWarnings("resource")
	public static void iterate(File zip, ZipInfoCallback action) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			Enumeration<?> en = zf.entries();
			while (en.hasMoreElements()) {
				ZipEntry e = (ZipEntry) en.nextElement();
				try {
					action.process(e);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ e.getName() + " with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Iterate.
	 *
	 * @param is
	 *            the is
	 * @param entryNames
	 *            the entry names
	 * @param action
	 *            the action
	 */
	public static void iterate(InputStream is, String[] entryNames,
			ZipEntryCallback action) {
		iterate(is, entryNames, action, null);
	}

	/**
	 * Iterate.
	 *
	 * @param is
	 *            the is
	 * @param entryNames
	 *            the entry names
	 * @param action
	 *            the action
	 * @param charset
	 *            the charset
	 */
	public static void iterate(InputStream is, String[] entryNames,
			ZipEntryCallback action, Charset charset) {
		Set<String> namesSet = new HashSet<String>();
		for (int i = 0; i < entryNames.length; i++) {
			namesSet.add(entryNames[i]);
		}
		try {
			ZipInputStream in = null;
			if (charset == null) {
				in = new ZipInputStream(new BufferedInputStream(is));
			} else {
				in = ZipFileUtil.createZipInputStream(is, charset);
			}
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				if (!namesSet.contains(entry.getName())) {
					// skip the unnecessary entry
					continue;
				}
				try {
					action.process(in, entry);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ entry.getName() + " with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Iterate.
	 *
	 * @param is
	 *            the is
	 * @param action
	 *            the action
	 */
	public static void iterate(InputStream is, ZipEntryCallback action) {
		iterate(is, action, null);
	}

	/**
	 * Iterate.
	 *
	 * @param is
	 *            the is
	 * @param action
	 *            the action
	 * @param charset
	 *            the charset
	 */
	public static void iterate(InputStream is, ZipEntryCallback action,
			Charset charset) {
		try {
			ZipInputStream in = null;
			if (charset == null) {
				in = new ZipInputStream(new BufferedInputStream(is));
			} else {
				in = ZipFileUtil.createZipInputStream(is, charset);
			}
			ZipEntry entry;
			while ((entry = in.getNextEntry()) != null) {
				try {
					action.process(in, entry);
				} catch (IOException ze) {
					throw new ZipException("Failed to process zip entry '"
							+ entry.getName() + " with action " + action, ze);
				} catch (ZipBreakException ex) {
					break;
				}
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Meta data equals.
	 *
	 * @param path
	 *            the path
	 * @param e1
	 *            the e1
	 * @param e2
	 *            the e2
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static boolean metaDataEquals(String path, ZipEntry e1, ZipEntry e2)
			throws IOException {
		// Check if the same entry exists in the second archive
		if (e2 == null) {
			// log.debug("Entry '{}' removed.", path);
			return false;
		}
		// Check the directory flag
		if (e1.isDirectory()) {
			if (e2.isDirectory()) {
				return true; // Let's skip the directory as there is nothing to
								// compare
			} else {
				// log.debug("Entry '{}' not a directory any more.", path);
				return false;
			}
		} else if (e2.isDirectory()) {
			// log.debug("Entry '{}' now a directory.", path);
			return false;
		}
		// Check the size
		long size1 = e1.getSize();
		long size2 = e2.getSize();
		if (size1 != -1 && size2 != -1 && size1 != size2) {
			// log.debug("Entry '" + path + "' size changed (" + size1 + " vs "
			// + size2 + ").");
			return false;
		}
		// Check the CRC
		long crc1 = e1.getCrc();
		long crc2 = e2.getCrc();
		if (crc1 != -1 && crc2 != -1 && crc1 != crc2) {
			// log.debug("Entry '" + path + "' CRC changed (" + crc1 + " vs " +
			// crc2 + ").");
			return false;
		}
		// Check the time (ignored, logging only)
		// if (log.isTraceEnabled()) {
		// long time1 = e1.getTime();
		// long time2 = e2.getTime();
		// if (time1 != -1 && time2 != -1 && time1 != time2) {
		// log.trace("Entry '" + path + "' time changed (" + new Date(time1) +
		// " vs " + new Date(time2) + ").");
		// }
		// }
		return true;
	}

	/**
	 * Operate in place.
	 *
	 * @param src
	 *            the src
	 * @param action
	 *            the action
	 * @return true, if successful
	 */
	private static boolean operateInPlace(File src, InPlaceAction action) {
		File tmp = null;
		try {
			tmp = File.createTempFile("zt-zip-tmp", ".zip");
			boolean result = action.act(tmp);
			if (result) { // else nothing changes
				FileUtils.forceDelete(src);
				FileUtils.moveFile(tmp, src);
			}
			return result;
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			FileUtils.deleteQuietly(tmp);
		}
	}

	/**
	 * Pack.
	 *
	 * @param rootDir
	 *            the root dir
	 * @param zip
	 *            the zip
	 */
	public static void pack(File rootDir, File zip) {
		pack(rootDir, zip, DEFAULT_COMPRESSION_LEVEL);
	}

	/**
	 * Pack.
	 *
	 * @param sourceDir
	 *            the source dir
	 * @param targetZipFile
	 *            the target zip file
	 * @param preserveRoot
	 *            the preserve root
	 */
	public static void pack(final File sourceDir, final File targetZipFile,
			final boolean preserveRoot) {
		if (preserveRoot) {
			final String parentName = sourceDir.getName();
			pack(sourceDir, targetZipFile, new NameMapper() {

				@Override
				public String map(String name) {
					return parentName + PATH_SEPARATOR + name;
				}
			});
		} else {
			pack(sourceDir, targetZipFile);
		}
	}

	/**
	 * Pack.
	 *
	 * @param rootDir
	 *            the root dir
	 * @param zip
	 *            the zip
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void pack(File rootDir, File zip, int compressionLevel) {
		pack(rootDir, zip, IdentityNameMapper.INSTANCE, compressionLevel);
	}

	/**
	 * Pack.
	 *
	 * @param sourceDir
	 *            the source dir
	 * @param targetZip
	 *            the target zip
	 * @param mapper
	 *            the mapper
	 */
	public static void pack(File sourceDir, File targetZip, NameMapper mapper) {
		pack(sourceDir, targetZip, mapper, DEFAULT_COMPRESSION_LEVEL);
	}

	/**
	 * Pack.
	 *
	 * @param sourceDir
	 *            the source dir
	 * @param targetZip
	 *            the target zip
	 * @param mapper
	 *            the mapper
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void pack(File sourceDir, File targetZip, NameMapper mapper,
			int compressionLevel) {
		// log.debug("Compressing '{}' into '{}'.", sourceDir, targetZip);
		if (!sourceDir.exists()) {
			throw new ZipException("Given file '" + sourceDir
					+ "' doesn't exist!");
		}
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(targetZip)));
			out.setLevel(compressionLevel);
			pack(sourceDir, out, mapper, "", true);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Pack.
	 *
	 * @param dir
	 *            the dir
	 * @param out
	 *            the out
	 * @param mapper
	 *            the mapper
	 * @param pathPrefix
	 *            the path prefix
	 * @param mustHaveChildren
	 *            the must have children
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void pack(File dir, ZipOutputStream out, NameMapper mapper,
			String pathPrefix, boolean mustHaveChildren) throws IOException {
		String[] filenames = dir.list();
		if (filenames == null) {
			if (!dir.exists()) {
				throw new ZipException("Given file '" + dir
						+ "' doesn't exist!");
			}
			throw new IOException("Given file is not a directory '" + dir + "'");
		}
		if (mustHaveChildren && filenames.length == 0) {
			throw new ZipException("Given directory '" + dir
					+ "' doesn't contain any files!");
		}
		for (int i = 0; i < filenames.length; i++) {
			String filename = filenames[i];
			File file = new File(dir, filename);
			boolean isDir = file.isDirectory();
			String path = pathPrefix + file.getName(); // NOSONAR
			if (isDir) {
				path += PATH_SEPARATOR; // NOSONAR
			}
			// Create a ZIP entry
			String name = mapper.map(path);
			if (name != null) {
				ZipEntry zipEntry = new ZipEntry(name);
				if (!isDir) {
					zipEntry.setSize(file.length());
					zipEntry.setTime(file.lastModified());
				}
				out.putNextEntry(zipEntry);
				// Copy the file content
				if (!isDir) {
					FileUtils.copy(file, out);
				}
				out.closeEntry();
			}
			// Traverse the directory
			if (isDir) {
				pack(file, out, mapper, path, false);
			}
		}
	}

	/**
	 * Pack.
	 *
	 * @param entries
	 *            the entries
	 * @param zip
	 *            the zip
	 */
	public static void pack(ZipEntrySource[] entries, File zip) {
		// log.debug("Creating '{}' from {}.", zip, Arrays.asList(entries));
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zip)));
			for (int i = 0; i < entries.length; i++) {
				addEntry(entries[i], out);
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Pack entries.
	 *
	 * @param filesToPack
	 *            the files to pack
	 * @param destZipFile
	 *            the dest zip file
	 */
	public static void packEntries(File[] filesToPack, File destZipFile) {
		packEntries(filesToPack, destZipFile, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Pack entries.
	 *
	 * @param filesToPack
	 *            the files to pack
	 * @param destZipFile
	 *            the dest zip file
	 * @param mapper
	 *            the mapper
	 */
	public static void packEntries(File[] filesToPack, File destZipFile,
			NameMapper mapper) {
		// log.debug("Compressing '{}' into '{}'.", filesToPack, destZipFile);
		ZipOutputStream out = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destZipFile);
			out = new ZipOutputStream(new BufferedOutputStream(fos));
			for (int i = 0; i < filesToPack.length; i++) {
				File fileToPack = filesToPack[i];
				ZipEntry zipEntry = new ZipEntry(mapper.map(fileToPack
						.getName()));
				zipEntry.setSize(fileToPack.length());
				zipEntry.setTime(fileToPack.lastModified());
				out.putNextEntry(zipEntry);
				FileUtils.copy(fileToPack, out);
				out.closeEntry();
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * Pack entry.
	 *
	 * @param file
	 *            the file
	 * @return the byte[]
	 */
	public static byte[] packEntry(File file) {
		// log.trace("Compressing '{}' into a ZIP file with single entry.",
		// file);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			ZipOutputStream out = new ZipOutputStream(result);
			ZipEntry entry = new ZipEntry(file.getName());
			entry.setTime(file.lastModified());
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			try {
				ZipEntryUtil.addEntry(entry, in, out);
			} finally {
				IOUtils.closeQuietly(in);
			}
			out.close();
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
		return result.toByteArray();
	}

	/**
	 * Pack entry.
	 *
	 * @param fileToPack
	 *            the file to pack
	 * @param destZipFile
	 *            the dest zip file
	 */
	public static void packEntry(File fileToPack, File destZipFile) {
		packEntry(fileToPack, destZipFile, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Pack entry.
	 *
	 * @param fileToPack
	 *            the file to pack
	 * @param destZipFile
	 *            the dest zip file
	 * @param mapper
	 *            the mapper
	 */
	public static void packEntry(File fileToPack, File destZipFile,
			NameMapper mapper) {
		packEntries(new File[] { fileToPack }, destZipFile, mapper);
	}

	/**
	 * Pack entry.
	 *
	 * @param fileToPack
	 *            the file to pack
	 * @param destZipFile
	 *            the dest zip file
	 * @param fileName
	 *            the file name
	 */
	public static void packEntry(File fileToPack, File destZipFile,
			final String fileName) {
		packEntry(fileToPack, destZipFile, new NameMapper() {

			@Override
			public String map(String name) {
				return fileName;
			}
		});
	}

	/**
	 * Removes the entries.
	 *
	 * @param zip
	 *            the zip
	 * @param paths
	 *            the paths
	 */
	public static void removeEntries(final File zip, final String[] paths) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				removeEntries(zip, paths, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Removes the entries.
	 *
	 * @param zip
	 *            the zip
	 * @param paths
	 *            the paths
	 * @param destZip
	 *            the dest zip
	 */
	public static void removeEntries(File zip, String[] paths, File destZip) {
		// if (log.isDebugEnabled()) {
		// log.debug("Copying '" + zip + "' to '" + destZip +
		// "' and removing paths " + Arrays.asList(paths) + ".");
		// }
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(destZip)));
			copyEntries(zip, out, new HashSet<String>(Arrays.asList(paths)));
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Removes the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 */
	public static void removeEntry(final File zip, final String path) {
		operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				removeEntry(zip, path, tmpFile);
				return true;
			}
		});
	}

	/**
	 * Removes the entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param destZip
	 *            the dest zip
	 */
	public static void removeEntry(File zip, String path, File destZip) {
		removeEntries(zip, new String[] { path }, destZip);
	}

	/**
	 * Repack.
	 *
	 * @param srcZip
	 *            the src zip
	 * @param dstZip
	 *            the dst zip
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void repack(File srcZip, File dstZip, int compressionLevel) {
		// log.debug("Repacking '{}' into '{}'.", srcZip, dstZip);
		RepackZipEntryCallback callback = new RepackZipEntryCallback(dstZip,
				compressionLevel);
		try {
			iterate(srcZip, callback);
		} finally {
			callback.closeStream();
		}
	}

	/**
	 * Repack.
	 *
	 * @param zip
	 *            the zip
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void repack(File zip, int compressionLevel) {
		try {
			File tmpZip = FileUtils.getTempFileFor(zip);
			repack(zip, tmpZip, compressionLevel);
			// Delete original zip
			if (!zip.delete()) {
				throw new IOException("Unable to delete the file: " + zip);
			}
			// Rename the archive
			FileUtils.moveFile(tmpZip, zip);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Repack.
	 *
	 * @param is
	 *            the is
	 * @param dstZip
	 *            the dst zip
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void repack(InputStream is, File dstZip, int compressionLevel) {
		// log.debug("Repacking from input stream into '{}'.", dstZip);
		RepackZipEntryCallback callback = new RepackZipEntryCallback(dstZip,
				compressionLevel);
		try {
			iterate(is, callback);
		} finally {
			callback.closeStream();
		}
	}

	/**
	 * Replace entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @return true, if successful
	 */
	public static boolean replaceEntries(final File zip,
			final ZipEntrySource[] entries) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return replaceEntries(zip, entries, tmpFile);
			}
		});
	}

	/**
	 * Replace entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean replaceEntries(File zip, ZipEntrySource[] entries,
			File destZip) {
		// if (log.isDebugEnabled()) {
		// log.debug("Copying '" + zip + "' to '" + destZip +
		// "' and replacing entries " + Arrays.asList(entries) + ".");
		// }
		final Map<String, ZipEntrySource> entryByPath = byPath(entries);
		final int entryCount = entryByPath.size();
		try {
			final ZipOutputStream out = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(destZip)));
			try {
				final Set<String> names = new HashSet<String>();
				iterate(zip, new ZipEntryCallback() {

					@Override
					public void process(InputStream in, ZipEntry zipEntry)
							throws IOException {
						if (names.add(zipEntry.getName())) {
							ZipEntrySource entry = entryByPath.remove(zipEntry
									.getName());
							if (entry != null) {
								addEntry(entry, out);
							} else {
								ZipEntryUtil.copyEntry(zipEntry, in, out);
							}
						}
						// else if (log.isDebugEnabled()) {
						// log.debug("Duplicate entry: {}", zipEntry.getName());
						// }
					}
				});
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		}
		return entryByPath.size() < entryCount;
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 * @return true, if successful
	 */
	public static boolean replaceEntry(final File zip, final String path,
			final byte[] bytes) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return replaceEntry(zip, new ByteSource(path, bytes), tmpFile);
			}
		});
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean replaceEntry(File zip, String path, byte[] bytes,
			File destZip) {
		return replaceEntry(zip, new ByteSource(path, bytes), destZip);
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	public static boolean replaceEntry(final File zip, final String path,
			final File file) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return replaceEntry(zip, new FileSource(path, file), tmpFile);
			}
		});
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param file
	 *            the file
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean replaceEntry(File zip, String path, File file,
			File destZip) {
		return replaceEntry(zip, new FileSource(path, file), destZip);
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 * @return true, if successful
	 */
	public static boolean replaceEntry(final File zip,
			final ZipEntrySource entry) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return replaceEntry(zip, entry, tmpFile);
			}
		});
	}

	/**
	 * Replace entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean replaceEntry(File zip, ZipEntrySource entry,
			File destZip) {
		return replaceEntries(zip, new ZipEntrySource[] { entry }, destZip);
	}

	/**
	 * Transform entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @return true, if successful
	 */
	public static boolean transformEntries(final File zip,
			final ZipEntryTransformerEntry[] entries) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return transformEntries(zip, entries, tmpFile);
			}
		});
	}

	/**
	 * Transform entries.
	 *
	 * @param zip
	 *            the zip
	 * @param entries
	 *            the entries
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean transformEntries(File zip,
			ZipEntryTransformerEntry[] entries, File destZip) {
		// if (log.isDebugEnabled())
		// log.debug("Copying '" + zip + "' to '" + destZip +
		// "' and transforming entries " + Arrays.asList(entries) + ".");
		try {
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(destZip)));
			try {
				TransformerZipEntryCallback action = new TransformerZipEntryCallback(
						entries, out);
				iterate(zip, action);
				return action.found();
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Transform entries.
	 *
	 * @param is
	 *            the is
	 * @param entries
	 *            the entries
	 * @param os
	 *            the os
	 * @return true, if successful
	 */
	public static boolean transformEntries(InputStream is,
			ZipEntryTransformerEntry[] entries, OutputStream os) {
		// if (log.isDebugEnabled())
		// log.debug("Copying '" + is + "' to '" + os +
		// "' and transforming entries " + Arrays.asList(entries) + ".");
		try {
			ZipOutputStream out = new ZipOutputStream(os);
			TransformerZipEntryCallback action = new TransformerZipEntryCallback(
					entries, out);
			iterate(is, action);
			// Finishes writing the contents of the ZIP output stream without
			// closing
			// the underlying stream.
			out.finish();
			return action.found();
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Transform entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param transformer
	 *            the transformer
	 * @return true, if successful
	 */
	public static boolean transformEntry(final File zip, final String path,
			final ZipEntryTransformer transformer) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return transformEntry(zip, path, transformer, tmpFile);
			}
		});
	}

	/**
	 * Transform entry.
	 *
	 * @param zip
	 *            the zip
	 * @param path
	 *            the path
	 * @param transformer
	 *            the transformer
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean transformEntry(File zip, String path,
			ZipEntryTransformer transformer, File destZip) {
		return transformEntry(zip, new ZipEntryTransformerEntry(path,
				transformer), destZip);
	}

	/**
	 * Transform entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 * @return true, if successful
	 */
	public static boolean transformEntry(final File zip,
			final ZipEntryTransformerEntry entry) {
		return operateInPlace(zip, new InPlaceAction() {

			@Override
			public boolean act(File tmpFile) {
				return transformEntry(zip, entry, tmpFile);
			}
		});
	}

	/**
	 * Transform entry.
	 *
	 * @param zip
	 *            the zip
	 * @param entry
	 *            the entry
	 * @param destZip
	 *            the dest zip
	 * @return true, if successful
	 */
	public static boolean transformEntry(File zip,
			ZipEntryTransformerEntry entry, File destZip) {
		return transformEntries(zip, new ZipEntryTransformerEntry[] { entry },
				destZip);
	}

	/**
	 * Transform entry.
	 *
	 * @param is
	 *            the is
	 * @param path
	 *            the path
	 * @param transformer
	 *            the transformer
	 * @param os
	 *            the os
	 * @return true, if successful
	 */
	public static boolean transformEntry(InputStream is, String path,
			ZipEntryTransformer transformer, OutputStream os) {
		return transformEntry(is, new ZipEntryTransformerEntry(path,
				transformer), os);
	}

	/**
	 * Transform entry.
	 *
	 * @param is
	 *            the is
	 * @param entry
	 *            the entry
	 * @param os
	 *            the os
	 * @return true, if successful
	 */
	public static boolean transformEntry(InputStream is,
			ZipEntryTransformerEntry entry, OutputStream os) {
		return transformEntries(is, new ZipEntryTransformerEntry[] { entry },
				os);
	}

	/**
	 * Unexplode.
	 *
	 * @param dir
	 *            the dir
	 */
	public static void unexplode(File dir) {
		unexplode(dir, DEFAULT_COMPRESSION_LEVEL);
	}

	/**
	 * Unexplode.
	 *
	 * @param dir
	 *            the dir
	 * @param compressionLevel
	 *            the compression level
	 */
	public static void unexplode(File dir, int compressionLevel) {
		try {
			// Find a new unique name is the same directory
			File zip = FileUtils.getTempFileFor(dir);
			// Pack it
			pack(dir, zip, compressionLevel);
			// Delete the directory
			FileUtils.deleteDirectory(dir);
			// Rename the archive
			FileUtils.moveFile(zip, dir);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Unpack.
	 *
	 * @param zip
	 *            the zip
	 * @param outputDir
	 *            the output dir
	 */
	public static void unpack(File zip, final File outputDir) {
		unpack(zip, outputDir, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Unpack.
	 *
	 * @param zip
	 *            the zip
	 * @param outputDir
	 *            the output dir
	 * @param mapper
	 *            the mapper
	 */
	public static void unpack(File zip, File outputDir, NameMapper mapper) {
		// log.debug("Extracting '{}' into '{}'.", zip, outputDir);
		iterate(zip, new Unpacker(outputDir, mapper));
	}

	/**
	 * Unpack.
	 *
	 * @param is
	 *            the is
	 * @param outputDir
	 *            the output dir
	 */
	public static void unpack(InputStream is, File outputDir) {
		unpack(is, outputDir, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Unpack.
	 *
	 * @param is
	 *            the is
	 * @param outputDir
	 *            the output dir
	 * @param mapper
	 *            the mapper
	 */
	public static void unpack(InputStream is, File outputDir, NameMapper mapper) {
		// log.debug("Extracting {} into '{}'.", is, outputDir);
		iterate(is, new Unpacker(outputDir, mapper));
	}

	/**
	 * Unpack entry.
	 *
	 * @param zip
	 *            the zip
	 * @param name
	 *            the name
	 * @return the byte[]
	 */
	public static byte[] unpackEntry(File zip, String name) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			return doUnpackEntry(zf, name);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/* Comparing two ZIP files. */
	/**
	 * Unpack entry.
	 *
	 * @param zip
	 *            the zip
	 * @param name
	 *            the name
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	public static boolean unpackEntry(File zip, String name, File file) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(zip);
			return doUnpackEntry(zf, name, file);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		} finally {
			closeQuietly(zf);
		}
	}

	/**
	 * Unpack entry.
	 *
	 * @param is
	 *            the is
	 * @param name
	 *            the name
	 * @return the byte[]
	 */
	public static byte[] unpackEntry(InputStream is, String name) {
		ByteArrayUnpacker action = new ByteArrayUnpacker();
		if (!handle(is, name, action))
			return null; // entry not found
		return action.getBytes();
	}

	/**
	 * Unpack entry.
	 *
	 * @param is
	 *            the is
	 * @param name
	 *            the name
	 * @param file
	 *            the file
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean unpackEntry(InputStream is, String name, File file)
			throws IOException {
		return handle(is, name, new FileUnpacker(file));
	}

	/**
	 * Unpack entry.
	 *
	 * @param zf
	 *            the zf
	 * @param name
	 *            the name
	 * @return the byte[]
	 */
	public static byte[] unpackEntry(ZipFile zf, String name) {
		try {
			return doUnpackEntry(zf, name);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Unpack entry.
	 *
	 * @param zf
	 *            the zf
	 * @param name
	 *            the name
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	public static boolean unpackEntry(ZipFile zf, String name, File file) {
		try {
			return doUnpackEntry(zf, name, file);
		} catch (IOException e) {
			throw ZipExceptionUtil.rethrow(e);
		}
	}

	/**
	 * Unwrap.
	 *
	 * @param zip
	 *            the zip
	 * @param outputDir
	 *            the output dir
	 */
	public static void unwrap(File zip, final File outputDir) {
		unwrap(zip, outputDir, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Unwrap.
	 *
	 * @param zip
	 *            the zip
	 * @param outputDir
	 *            the output dir
	 * @param mapper
	 *            the mapper
	 */
	public static void unwrap(File zip, File outputDir, NameMapper mapper) {
		// log.debug("Unwraping '{}' into '{}'.", zip, outputDir);
		iterate(zip, new Unwraper(outputDir, mapper));
	}

	/**
	 * Unwrap.
	 *
	 * @param is
	 *            the is
	 * @param outputDir
	 *            the output dir
	 */
	public static void unwrap(InputStream is, File outputDir) {
		unwrap(is, outputDir, IdentityNameMapper.INSTANCE);
	}

	/**
	 * Unwrap.
	 *
	 * @param is
	 *            the is
	 * @param outputDir
	 *            the output dir
	 * @param mapper
	 *            the mapper
	 */
	public static void unwrap(InputStream is, File outputDir, NameMapper mapper) {
		// log.debug("Unwraping {} into '{}'.", is, outputDir);
		iterate(is, new Unwraper(outputDir, mapper));
	}

	/** The Constant PATH_SEPARATOR. */
	private static final String PATH_SEPARATOR = "/";

	/** The Constant DEFAULT_COMPRESSION_LEVEL. */
	public static final int DEFAULT_COMPRESSION_LEVEL = Deflater.DEFAULT_COMPRESSION;

	/**
	 * Instantiates a new zip util.
	 */
	private ZipUtil() {
	}
}
