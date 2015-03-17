/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import info.nordbyen.Ziputils.commons.FileUtils;
import info.nordbyen.Ziputils.commons.IOUtils;
import info.nordbyen.Ziputils.transform.ZipEntryTransformer;
import info.nordbyen.Ziputils.transform.ZipEntryTransformerEntry;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The Class Zips.
 */
public class Zips {

	/**
	 * The Class CopyingCallback.
	 */
	private static class CopyingCallback implements ZipEntryCallback {

		/** The entry by path. */
		private final Map<?, ?> entryByPath;
		
		/** The out. */
		private final ZipOutputStream out;
		
		/** The visited names. */
		private final Set<String> visitedNames;
		
		/** The preserve timestapms. */
		private final boolean preserveTimestapms;

		/**
		 * Instantiates a new copying callback.
		 *
		 * @param entries
		 *            the entries
		 * @param out
		 *            the out
		 * @param preserveTimestapms
		 *            the preserve timestapms
		 */
		private CopyingCallback(ZipEntryTransformerEntry[] entries,
				ZipOutputStream out, boolean preserveTimestapms) {
			this.out = out;
			this.preserveTimestapms = preserveTimestapms;
			entryByPath = ZipUtil.byPath(entries);
			visitedNames = new HashSet<String>();
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
			String entryName = zipEntry.getName();
			if (visitedNames.contains(entryName)) {
				return;
			}
			visitedNames.add(entryName);
			ZipEntryTransformer transformer = (ZipEntryTransformer) entryByPath
					.remove(entryName);
			if (transformer == null) { // no transformer
				ZipEntryUtil.copyEntry(zipEntry, in, out, preserveTimestapms);
			} else { // still transfom entry
				transformer.transform(in, zipEntry, out);
			}
		}
	}

	/**
	 * The Class UnpackingCallback.
	 */
	private static class UnpackingCallback implements ZipEntryCallback {

		/** The entry by path. */
		private final Map<?, ?> entryByPath;
		
		/** The visited names. */
		private final Set<String> visitedNames;
		
		/** The destination. */
		private final File destination;

		/**
		 * Instantiates a new unpacking callback.
		 *
		 * @param entries
		 *            the entries
		 * @param destination
		 *            the destination
		 */
		private UnpackingCallback(ZipEntryTransformerEntry[] entries,
				File destination) {
			this.destination = destination;
			this.entryByPath = ZipUtil.byPath(entries);
			visitedNames = new HashSet<String>();
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
			String entryName = zipEntry.getName();
			if (visitedNames.contains(entryName)) {
				return;
			}
			visitedNames.add(entryName);
			File file = new File(destination, entryName);
			if (zipEntry.isDirectory()) {
				FileUtils.forceMkdir(file);
				return;
			} else {
				FileUtils.forceMkdir(file.getParentFile());
				file.createNewFile();
			}
			ZipEntryTransformer transformer = (ZipEntryTransformer) entryByPath
					.remove(entryName);
			if (transformer == null) { // no transformer
				FileUtils.copy(in, file);
			} else { // still transform entry
				transformIntoFile(transformer, in, zipEntry, file);
			}
		}

		/**
		 * Transform into file.
		 *
		 * @param transformer
		 *            the transformer
		 * @param entryIn
		 *            the entry in
		 * @param zipEntry
		 *            the zip entry
		 * @param destination
		 *            the destination
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void transformIntoFile(final ZipEntryTransformer transformer,
				final InputStream entryIn, final ZipEntry zipEntry,
				final File destination) throws IOException {
			final PipedInputStream pipedIn = new PipedInputStream();
			final PipedOutputStream pipedOut = new PipedOutputStream(pipedIn);
			final ZipOutputStream zipOut = new ZipOutputStream(pipedOut);
			final ZipInputStream zipIn = new ZipInputStream(pipedIn);
			ExecutorService newFixedThreadPool = Executors
					.newFixedThreadPool(1);
			try {
				newFixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						try {
							transformer.transform(entryIn, zipEntry, zipOut);
						} catch (IOException e) {
							ZipExceptionUtil.rethrow(e);
						}
					}
				});
				zipIn.getNextEntry();
				FileUtils.copy(zipIn, destination);
			} finally {
				try {
					zipIn.closeEntry();
				} catch (IOException e) {
					// closing quietly
				}
				newFixedThreadPool.shutdown();
				IOUtils.closeQuietly(pipedIn);
				IOUtils.closeQuietly(zipIn);
				IOUtils.closeQuietly(pipedOut);
				IOUtils.closeQuietly(zipOut);
			}
		}
	}

	/**
	 * Creates the.
	 *
	 * @return the zips
	 */
	public static Zips create() {
		return new Zips(null);
	}

	/**
	 * Gets the.
	 *
	 * @param src
	 *            the src
	 * @return the zips
	 */
	public static Zips get(File src) {
		return new Zips(src);
	}

	/** The src. */
	private final File src;
	
	/** The dest. */
	private File dest;
	
	/** The charset. */
	private Charset charset;
	
	/** The preserve timestamps. */
	private boolean preserveTimestamps;
	
	/** The changed entries. */
	private List<ZipEntrySource> changedEntries = new ArrayList<ZipEntrySource>();
	
	/** The removed entries. */
	private Set<String> removedEntries = new HashSet<String>();
	
	/** The transformers. */
	private List<ZipEntryTransformerEntry> transformers = new ArrayList<ZipEntryTransformerEntry>();
	/*
	 * If you want many name mappers here, you can create some compound instance
	 * that knows if it wants to stop after first successfull mapping or go
	 * through all transformations, if null means stop and ignore entry or that
	 * name mapper didn't know how to transform, etc.
	 */
	/** The name mapper. */
	private NameMapper nameMapper;
	
	/** The unpacked result. */
	private boolean unpackedResult;

	/**
	 * Instantiates a new zips.
	 *
	 * @param src
	 *            the src
	 */
	private Zips(File src) {
		this.src = src;
	}

	/**
	 * Adds the entries.
	 *
	 * @param entries
	 *            the entries
	 * @return the zips
	 */
	public Zips addEntries(ZipEntrySource[] entries) {
		this.changedEntries.addAll(Arrays.asList(entries));
		return this;
	}

	/**
	 * Adds the entry.
	 *
	 * @param entry
	 *            the entry
	 * @return the zips
	 */
	public Zips addEntry(ZipEntrySource entry) {
		this.changedEntries.add(entry);
		return this;
	}

	/**
	 * Adds the file.
	 *
	 * @param file
	 *            the file
	 * @return the zips
	 */
	public Zips addFile(File file) {
		return addFile(file, false, null);
	}

	/**
	 * Adds the file.
	 *
	 * @param file
	 *            the file
	 * @param preserveRoot
	 *            the preserve root
	 * @return the zips
	 */
	public Zips addFile(File file, boolean preserveRoot) {
		return addFile(file, preserveRoot, null);
	}

	/**
	 * Adds the file.
	 *
	 * @param file
	 *            the file
	 * @param preserveRoot
	 *            the preserve root
	 * @param filter
	 *            the filter
	 * @return the zips
	 */
	public Zips addFile(File file, boolean preserveRoot, FileFilter filter) {
		if (!file.isDirectory()) {
			this.changedEntries.add(new FileSource(file.getName(), file));
			return this;
		}
		Collection<?> files = ZTFileUtil.listFiles(file);
		for (Iterator<?> iter = files.iterator(); iter.hasNext();) {
			File entryFile = (File) iter.next();
			if (filter != null && !filter.accept(entryFile)) {
				continue;
			}
			String entryPath = getRelativePath(file, entryFile);
			if (File.separator.equals("\\")) {
				// replace directory separators on windows as at least 7zip
				// packs zip with entries having "/" like on linux
				entryPath = entryPath.replace('\\', '/');
			}
			if (preserveRoot) {
				entryPath = file.getName() + entryPath;
			}
			if (entryPath.startsWith("/")) {
				entryPath = entryPath.substring(1);
			}
			this.changedEntries.add(new FileSource(entryPath, entryFile));
		}
		return this;
	}

	/**
	 * Adds the file.
	 *
	 * @param file
	 *            the file
	 * @param filter
	 *            the filter
	 * @return the zips
	 */
	public Zips addFile(File file, FileFilter filter) {
		return this.addFile(file, false, filter);
	}

	/**
	 * Adds the transformer.
	 *
	 * @param path
	 *            the path
	 * @param transformer
	 *            the transformer
	 * @return the zips
	 */
	public Zips addTransformer(String path, ZipEntryTransformer transformer) {
		this.transformers.add(new ZipEntryTransformerEntry(path, transformer));
		return this;
	}

	/**
	 * Charset.
	 *
	 * @param charset
	 *            the charset
	 * @return the zips
	 */
	public Zips charset(Charset charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * Contains entry.
	 *
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public boolean containsEntry(String name) {
		if (src == null) {
			throw new IllegalStateException("Source is not given");
		}
		return ZipUtil.containsEntry(src, name);
	}

	/**
	 * Destination.
	 *
	 * @param destination
	 *            the destination
	 * @return the zips
	 */
	public Zips destination(File destination) {
		this.dest = destination;
		return this;
	}

	/**
	 * Gets the destination file.
	 *
	 * @return the destination file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private File getDestinationFile() throws IOException {
		if (isUnpack()) {
			if (isInPlace()) {
				File tempFile = File.createTempFile("zips", null);
				FileUtils.deleteQuietly(tempFile);
				tempFile.mkdirs(); // temp dir created
				return tempFile;
			} else {
				if (!dest.isDirectory()) {
					// destination is a directory, actually we shouldn't be
					// here, because this should mean we want an unpacked
					// result.
					FileUtils.deleteQuietly(dest);
					File result = new File(dest.getAbsolutePath());
					result.mkdirs(); // create a directory instead of dest file
					return result;
				}
				return dest;
			}
		} else {
			// we need a file
			if (isInPlace()) { // no destination specified, temp file
				return File.createTempFile("zips", ".zip");
			} else {
				if (dest.isDirectory()) {
					// destination is a directory, actually we shouldn't be
					// here, because this should mean we want an unpacked
					// result.
					FileUtils.deleteQuietly(dest);
					return new File(dest.getAbsolutePath());
				}
				return dest;
			}
		}
	}

	/**
	 * Gets the entry.
	 *
	 * @param name
	 *            the name
	 * @return the entry
	 */
	public byte[] getEntry(String name) {
		if (src == null) {
			throw new IllegalStateException("Source is not given");
		}
		return ZipUtil.unpackEntry(src, name);
	}

	/**
	 * Gets the relative path.
	 *
	 * @param parent
	 *            the parent
	 * @param file
	 *            the file
	 * @return the relative path
	 */
	private String getRelativePath(File parent, File file) {
		String parentPath = parent.getPath();
		String filePath = file.getPath();
		if (!filePath.startsWith(parentPath)) {
			throw new IllegalArgumentException("File " + file
					+ " is not a child of " + parent);
		}
		return filePath.substring(parentPath.length());
	}

	/**
	 * Gets the transformers array.
	 *
	 * @return the transformers array
	 */
	private ZipEntryTransformerEntry[] getTransformersArray() {
		ZipEntryTransformerEntry[] result = new ZipEntryTransformerEntry[transformers
				.size()];
		int idx = 0;
		Iterator<ZipEntryTransformerEntry> iter = transformers.iterator();
		while (iter.hasNext()) {
			result[idx++] = iter.next();
		}
		return result;
	}

	/**
	 * Gets the zip file.
	 *
	 * @return the zip file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ZipFile getZipFile() throws IOException {
		return ZipFileUtil.getZipFile(src, charset);
	}

	/**
	 * Handle in place actions.
	 *
	 * @param result
	 *            the result
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void handleInPlaceActions(File result) throws IOException {
		if (isInPlace()) {
			// we operate in-place
			FileUtils.forceDelete(src);
			if (result.isFile()) {
				FileUtils.moveFile(result, src);
			} else {
				FileUtils.moveDirectory(result, src);
			}
		}
	}

	/**
	 * Checks if is entry in dir.
	 *
	 * @param dirNames
	 *            the dir names
	 * @param entryName
	 *            the entry name
	 * @return true, if is entry in dir
	 */
	private boolean isEntryInDir(Set<?> dirNames, String entryName) {
		// this should be done with a trie, put dirNames in a trie and check if
		// entryName leads to
		// some node or not.
		Iterator<?> iter = dirNames.iterator();
		while (iter.hasNext()) {
			String dirName = (String) iter.next();
			if (entryName.startsWith(dirName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is in place.
	 *
	 * @return true, if is in place
	 */
	private boolean isInPlace() {
		return dest == null;
	}

	/**
	 * Checks if is unpack.
	 *
	 * @return true, if is unpack
	 */
	private boolean isUnpack() {
		return unpackedResult || (dest != null && dest.isDirectory());
	}

	/**
	 * Iterate.
	 *
	 * @param zipEntryCallback
	 *            the zip entry callback
	 */
	public void iterate(ZipEntryCallback zipEntryCallback) {
		ZipEntryOrInfoAdapter zipEntryAdapter = new ZipEntryOrInfoAdapter(
				zipEntryCallback, null);
		processAllEntries(zipEntryAdapter);
	}

	/**
	 * Iterate.
	 *
	 * @param callback
	 *            the callback
	 */
	public void iterate(ZipInfoCallback callback) {
		ZipEntryOrInfoAdapter zipEntryAdapter = new ZipEntryOrInfoAdapter(null,
				callback);
		processAllEntries(zipEntryAdapter);
	}

	/**
	 * Iterate changed and added.
	 *
	 * @param zipEntryCallback
	 *            the zip entry callback
	 */
	private void iterateChangedAndAdded(ZipEntryOrInfoAdapter zipEntryCallback) {
		for (Iterator<ZipEntrySource> it = changedEntries.iterator(); it
				.hasNext();) {
			ZipEntrySource entrySource = it.next();
			try {
				ZipEntry entry = entrySource.getEntry();
				if (nameMapper != null) {
					String mappedName = nameMapper.map(entry.getName());
					if (mappedName == null) {
						continue; // we should ignore this entry
					} else if (!mappedName.equals(entry.getName())) {
						// if name is different, do nothing
						entry = ZipEntryUtil.copy(entry, mappedName);
					}
				}
				zipEntryCallback.process(entrySource.getInputStream(), entry);
			} catch (ZipBreakException ex) {
				break;
			} catch (IOException e) {
				ZipExceptionUtil.rethrow(e);
			}
		}
	}

	/**
	 * Iterate existing except removed.
	 *
	 * @param zipEntryCallback
	 *            the zip entry callback
	 */
	private void iterateExistingExceptRemoved(
			ZipEntryOrInfoAdapter zipEntryCallback) {
		if (src == null) {
			// if we don't have source specified, then we have nothing to
			// iterate.
			return;
		}
		final Set<?> removedDirs = ZipUtil
				.filterDirEntries(src, removedEntries);
		ZipFile zf = null;
		try {
			zf = getZipFile();
			// manage existing entries
			Enumeration<?> en = zf.entries();
			while (en.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) en.nextElement();
				String entryName = entry.getName();
				if (removedEntries.contains(entryName)
						|| isEntryInDir(removedDirs, entryName)) {
					// removed entries are
					continue;
				}
				if (nameMapper != null) {
					String mappedName = nameMapper.map(entry.getName());
					if (mappedName == null) {
						continue; // we should ignore this entry
					} else if (!mappedName.equals(entry.getName())) {
						// if name is different, do nothing
						entry = ZipEntryUtil.copy(entry, mappedName);
					}
				}
				InputStream is = zf.getInputStream(entry);
				try {
					zipEntryCallback.process(is, entry);
				} catch (ZipBreakException ex) {
					break;
				} finally {
					IOUtils.closeQuietly(is);
				}
			}
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		} finally {
			ZipUtil.closeQuietly(zf);
		}
	}

	// ///////////// private api ///////////////
	/**
	 * Name mapper.
	 *
	 * @param nameMapper
	 *            the name mapper
	 * @return the zips
	 */
	public Zips nameMapper(NameMapper nameMapper) {
		this.nameMapper = nameMapper;
		return this;
	}

	/**
	 * Preserve timestamps.
	 *
	 * @return the zips
	 */
	public Zips preserveTimestamps() {
		this.preserveTimestamps = true;
		return this;
	}

	/**
	 * Process.
	 */
	public void process() {
		if (src == null && dest == null) {
			throw new IllegalArgumentException(
					"Source and destination shouldn't be null together");
		}
		ZipEntryTransformerEntry[] transformersArray = getTransformersArray();
		File destinationFile = null;
		try {
			destinationFile = getDestinationFile();
			ZipOutputStream out = null;
			ZipEntryOrInfoAdapter zipEntryAdapter = null;
			if (destinationFile.isFile()) {
				out = ZipFileUtil.createZipOutputStream(
						new BufferedOutputStream(new FileOutputStream(
								destinationFile)), charset);
				zipEntryAdapter = new ZipEntryOrInfoAdapter(
						new CopyingCallback(transformersArray, out,
								preserveTimestamps), null);
			} else { // directory
				zipEntryAdapter = new ZipEntryOrInfoAdapter(
						new UnpackingCallback(transformersArray,
								destinationFile), null);
			}
			try {
				processAllEntries(zipEntryAdapter);
			} finally {
				IOUtils.closeQuietly(out);
			}
			handleInPlaceActions(destinationFile);
		} catch (IOException e) {
			ZipExceptionUtil.rethrow(e);
		} finally {
			if (isInPlace()) {
				// destinationZip is a temporary file
				FileUtils.deleteQuietly(destinationFile);
			}
		}
	}

	/**
	 * Process all entries.
	 *
	 * @param zipEntryAdapter
	 *            the zip entry adapter
	 */
	private void processAllEntries(ZipEntryOrInfoAdapter zipEntryAdapter) {
		iterateChangedAndAdded(zipEntryAdapter);
		iterateExistingExceptRemoved(zipEntryAdapter);
	}

	/**
	 * Removes the entries.
	 *
	 * @param entries
	 *            the entries
	 * @return the zips
	 */
	public Zips removeEntries(String[] entries) {
		this.removedEntries.addAll(Arrays.asList(entries));
		return this;
	}

	/**
	 * Removes the entry.
	 *
	 * @param entry
	 *            the entry
	 * @return the zips
	 */
	public Zips removeEntry(String entry) {
		this.removedEntries.add(entry);
		return this;
	}

	/**
	 * Sets the preserve timestamps.
	 *
	 * @param preserve
	 *            the preserve
	 * @return the zips
	 */
	public Zips setPreserveTimestamps(boolean preserve) {
		this.preserveTimestamps = preserve;
		return this;
	}

	/**
	 * Unpack.
	 *
	 * @return the zips
	 */
	public Zips unpack() {
		this.unpackedResult = true;
		return this;
	}
}
