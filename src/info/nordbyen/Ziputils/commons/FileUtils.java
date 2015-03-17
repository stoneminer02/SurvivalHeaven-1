/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class FileUtils.
 */
public class FileUtils {

	/**
	 * Clean directory.
	 *
	 * @param directory
	 *            the directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void cleanDirectory(final File directory) throws IOException {
		if (!directory.exists()) {
			final String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}
		if (!directory.isDirectory()) {
			final String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}
		final File[] files = directory.listFiles();
		if (files == null)
			throw new IOException("Failed to list contents of " + directory);
		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
			try {
				forceDelete(file);
			} catch (final IOException ioe) {
				exception = ioe;
			}
		}
		if (null != exception)
			throw exception;
	}

	/**
	 * Clean directory on exit.
	 *
	 * @param directory
	 *            the directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void cleanDirectoryOnExit(final File directory)
			throws IOException {
		if (!directory.exists()) {
			final String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}
		if (!directory.isDirectory()) {
			final String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}
		final File[] files = directory.listFiles();
		if (files == null)
			throw new IOException("Failed to list contents of " + directory);
		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
			try {
				forceDeleteOnExit(file);
			} catch (final IOException ioe) {
				exception = ioe;
			}
		}
		if (null != exception)
			throw exception;
	}

	// -----------------------------------------------------------------------
	/**
	 * Content equals.
	 *
	 * @param file1
	 *            the file1
	 * @param file2
	 *            the file2
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean contentEquals(final File file1, final File file2)
			throws IOException {
		final boolean file1Exists = file1.exists();
		if (file1Exists != file2.exists())
			return false;
		if (!file1Exists)
			// two not existing files are equal
			return true;
		if (file1.isDirectory() || file2.isDirectory())
			// don't want to compare directory contents
			throw new IOException("Can't compare directories, only files");
		if (file1.length() != file2.length())
			// lengths differ, cannot be equal
			return false;
		if (file1.getCanonicalFile().equals(file2.getCanonicalFile()))
			// same file
			return true;
		InputStream input1 = null;
		InputStream input2 = null;
		try {
			input1 = new FileInputStream(file1);
			input2 = new FileInputStream(file2);
			return IOUtils.contentEquals(input1, input2);
		} finally {
			IOUtils.closeQuietly(input1);
			IOUtils.closeQuietly(input2);
		}
	}

	/**
	 * Copy.
	 *
	 * @param file
	 *            the file
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copy(final File file, final OutputStream out)
			throws IOException {
		final FileInputStream in = new FileInputStream(file);
		try {
			IOUtils.copy(new BufferedInputStream(in), out);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Copy.
	 *
	 * @param in
	 *            the in
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copy(final InputStream in, final File file)
			throws IOException {
		final OutputStream out = new BufferedOutputStream(new FileOutputStream(
				file));
		try {
			IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * Copy directory.
	 *
	 * @param srcDir
	 *            the src dir
	 * @param destDir
	 *            the dest dir
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(final File srcDir, final File destDir)
			throws IOException {
		copyDirectory(srcDir, destDir, true);
	}

	/**
	 * Copy directory.
	 *
	 * @param srcDir
	 *            the src dir
	 * @param destDir
	 *            the dest dir
	 * @param preserveFileDate
	 *            the preserve file date
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(final File srcDir, final File destDir,
			final boolean preserveFileDate) throws IOException {
		copyDirectory(srcDir, destDir, null, preserveFileDate);
	}

	/**
	 * Copy directory.
	 *
	 * @param srcDir
	 *            the src dir
	 * @param destDir
	 *            the dest dir
	 * @param filter
	 *            the filter
	 * @param preserveFileDate
	 *            the preserve file date
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(final File srcDir, final File destDir,
			final FileFilter filter, final boolean preserveFileDate)
			throws IOException {
		if (srcDir == null)
			throw new NullPointerException("Source must not be null");
		if (destDir == null)
			throw new NullPointerException("Destination must not be null");
		if (srcDir.exists() == false)
			throw new FileNotFoundException("Source '" + srcDir
					+ "' does not exist");
		if (srcDir.isDirectory() == false)
			throw new IOException("Source '" + srcDir
					+ "' exists but is not a directory");
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath()))
			throw new IOException("Source '" + srcDir + "' and destination '"
					+ destDir + "' are the same");
		// Cater for destination being directory within the source directory
		// (see IO-141)
		List<String> exclusionList = null;
		if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
			final File[] srcFiles = filter == null ? srcDir.listFiles()
					: srcDir.listFiles(filter);
			if ((srcFiles != null) && (srcFiles.length > 0)) {
				exclusionList = new ArrayList<String>(srcFiles.length);
				for (int i = 0; i < srcFiles.length; i++) {
					final File copiedFile = new File(destDir,
							srcFiles[i].getName());
					exclusionList.add(copiedFile.getCanonicalPath());
				}
			}
		}
		doCopyDirectory(srcDir, destDir, filter, preserveFileDate,
				exclusionList);
	}

	/**
	 * Copy file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(final File srcFile, final File destFile)
			throws IOException {
		copyFile(srcFile, destFile, true);
	}

	/**
	 * Copy file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @param preserveFileDate
	 *            the preserve file date
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFile(final File srcFile, final File destFile,
			final boolean preserveFileDate) throws IOException {
		if (srcFile == null)
			throw new NullPointerException("Source must not be null");
		if (destFile == null)
			throw new NullPointerException("Destination must not be null");
		if (srcFile.exists() == false)
			throw new FileNotFoundException("Source '" + srcFile
					+ "' does not exist");
		if (srcFile.isDirectory())
			throw new IOException("Source '" + srcFile
					+ "' exists but is a directory");
		if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath()))
			throw new IOException("Source '" + srcFile + "' and destination '"
					+ destFile + "' are the same");
		if ((destFile.getParentFile() != null)
				&& (destFile.getParentFile().exists() == false)) {
			if (destFile.getParentFile().mkdirs() == false)
				throw new IOException("Destination '" + destFile
						+ "' directory cannot be created");
		}
		if (destFile.exists() && (destFile.canWrite() == false))
			throw new IOException("Destination '" + destFile
					+ "' exists but is read-only");
		doCopyFile(srcFile, destFile, preserveFileDate);
	}

	// -----------------------------------------------------------------------
	/**
	 * Copy file to directory.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destDir
	 *            the dest dir
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFileToDirectory(final File srcFile,
			final File destDir) throws IOException {
		copyFileToDirectory(srcFile, destDir, true);
	}

	/**
	 * Copy file to directory.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destDir
	 *            the dest dir
	 * @param preserveFileDate
	 *            the preserve file date
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyFileToDirectory(final File srcFile,
			final File destDir, final boolean preserveFileDate)
			throws IOException {
		if (destDir == null)
			throw new NullPointerException("Destination must not be null");
		if (destDir.exists() && (destDir.isDirectory() == false))
			throw new IllegalArgumentException("Destination '" + destDir
					+ "' is not a directory");
		copyFile(srcFile, new File(destDir, srcFile.getName()),
				preserveFileDate);
	}

	// -----------------------------------------------------------------------
	/**
	 * Delete directory.
	 *
	 * @param directory
	 *            the directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void deleteDirectory(final File directory) throws IOException {
		if (!directory.exists())
			return;
		cleanDirectory(directory);
		if (!directory.delete()) {
			final String message = "Unable to delete directory " + directory
					+ ".";
			throw new IOException(message);
		}
	}

	/**
	 * Delete directory on exit.
	 *
	 * @param directory
	 *            the directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void deleteDirectoryOnExit(final File directory)
			throws IOException {
		if (!directory.exists())
			return;
		cleanDirectoryOnExit(directory);
		directory.deleteOnExit();
	}

	/**
	 * Delete quietly.
	 *
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	public static boolean deleteQuietly(final File file) {
		if (file == null)
			return false;
		try {
			if (file.isDirectory()) {
				cleanDirectory(file);
			}
		} catch (final Exception e) {
		}
		try {
			return file.delete();
		} catch (final Exception e) {
			return false;
		}
	}

	/**
	 * Do copy directory.
	 *
	 * @param srcDir
	 *            the src dir
	 * @param destDir
	 *            the dest dir
	 * @param filter
	 *            the filter
	 * @param preserveFileDate
	 *            the preserve file date
	 * @param exclusionList
	 *            the exclusion list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void doCopyDirectory(final File srcDir, final File destDir,
			final FileFilter filter, final boolean preserveFileDate,
			final List<String> exclusionList) throws IOException {
		if (destDir.exists()) {
			if (destDir.isDirectory() == false)
				throw new IOException("Destination '" + destDir
						+ "' exists but is not a directory");
		} else {
			if (destDir.mkdirs() == false)
				throw new IOException("Destination '" + destDir
						+ "' directory cannot be created");
			if (preserveFileDate) {
				destDir.setLastModified(srcDir.lastModified());
			}
		}
		if (destDir.canWrite() == false)
			throw new IOException("Destination '" + destDir
					+ "' cannot be written to");
		// recurse
		final File[] files = filter == null ? srcDir.listFiles() : srcDir
				.listFiles(filter);
		if (files == null)
			throw new IOException("Failed to list contents of " + srcDir);
		for (int i = 0; i < files.length; i++) {
			final File copiedFile = new File(destDir, files[i].getName());
			if ((exclusionList == null)
					|| !exclusionList.contains(files[i].getCanonicalPath())) {
				if (files[i].isDirectory()) {
					doCopyDirectory(files[i], copiedFile, filter,
							preserveFileDate, exclusionList);
				} else {
					doCopyFile(files[i], copiedFile, preserveFileDate);
				}
			}
		}
	}

	/**
	 * Do copy file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @param preserveFileDate
	 *            the preserve file date
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void doCopyFile(final File srcFile, final File destFile,
			final boolean preserveFileDate) throws IOException {
		if (destFile.exists() && destFile.isDirectory())
			throw new IOException("Destination '" + destFile
					+ "' exists but is a directory");
		final FileInputStream input = new FileInputStream(srcFile);
		try {
			final FileOutputStream output = new FileOutputStream(destFile);
			try {
				IOUtils.copy(input, output);
			} finally {
				IOUtils.closeQuietly(output);
			}
		} finally {
			IOUtils.closeQuietly(input);
		}
		if (srcFile.length() != destFile.length())
			throw new IOException("Failed to copy full contents from '"
					+ srcFile + "' to '" + destFile + "'");
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Force delete.
	 *
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void forceDelete(final File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectory(file);
		} else {
			final boolean filePresent = file.exists();
			if (!file.delete()) {
				if (!filePresent)
					throw new FileNotFoundException("File does not exist: "
							+ file);
				final String message = "Unable to delete file: " + file;
				throw new IOException(message);
			}
		}
	}

	/**
	 * Force delete on exit.
	 *
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void forceDeleteOnExit(final File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectoryOnExit(file);
		} else {
			file.deleteOnExit();
		}
	}

	/**
	 * Force mkdir.
	 *
	 * @param directory
	 *            the directory
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void forceMkdir(final File directory) throws IOException {
		if (directory.exists()) {
			if (directory.isFile()) {
				final String message = "File " + directory + " exists and is "
						+ "not a directory. Unable to create directory.";
				throw new IOException(message);
			}
		} else {
			if (!directory.mkdirs()) {
				final String message = "Unable to create directory "
						+ directory;
				throw new IOException(message);
			}
		}
	}

	/**
	 * Gets the temp file for.
	 *
	 * @param file
	 *            the file
	 * @return the temp file for
	 */
	public static File getTempFileFor(final File file) {
		final File parent = file.getParentFile();
		final String name = file.getName();
		File result;
		int index = 0;
		do {
			result = new File(parent, name + "_" + index++);
		} while (result.exists());
		return result;
	}

	/**
	 * Move directory.
	 *
	 * @param srcDir
	 *            the src dir
	 * @param destDir
	 *            the dest dir
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void moveDirectory(final File srcDir, final File destDir)
			throws IOException {
		if (srcDir == null)
			throw new NullPointerException("Source must not be null");
		if (destDir == null)
			throw new NullPointerException("Destination must not be null");
		if (!srcDir.exists())
			throw new FileNotFoundException("Source '" + srcDir
					+ "' does not exist");
		if (!srcDir.isDirectory())
			throw new IOException("Source '" + srcDir + "' is not a directory");
		if (destDir.exists())
			throw new IOException("Destination '" + destDir
					+ "' already exists");
		final boolean rename = srcDir.renameTo(destDir);
		if (!rename) {
			copyDirectory(srcDir, destDir);
			deleteDirectory(srcDir);
			if (srcDir.exists())
				throw new IOException("Failed to delete original directory '"
						+ srcDir + "' after copy to '" + destDir + "'");
		}
	}

	/**
	 * Move file.
	 *
	 * @param srcFile
	 *            the src file
	 * @param destFile
	 *            the dest file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void moveFile(final File srcFile, final File destFile)
			throws IOException {
		if (srcFile == null)
			throw new NullPointerException("Source must not be null");
		if (destFile == null)
			throw new NullPointerException("Destination must not be null");
		if (!srcFile.exists())
			throw new FileNotFoundException("Source '" + srcFile
					+ "' does not exist");
		if (srcFile.isDirectory())
			throw new IOException("Source '" + srcFile + "' is a directory");
		if (destFile.exists())
			throw new IOException("Destination '" + destFile
					+ "' already exists");
		if (destFile.isDirectory())
			throw new IOException("Destination '" + destFile
					+ "' is a directory");
		final boolean rename = srcFile.renameTo(destFile);
		if (!rename) {
			copyFile(srcFile, destFile);
			if (!srcFile.delete()) {
				FileUtils.deleteQuietly(destFile);
				throw new IOException("Failed to delete original file '"
						+ srcFile + "' after copy to '" + destFile + "'");
			}
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Open input stream.
	 *
	 * @param file
	 *            the file
	 * @return the file input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static FileInputStream openInputStream(final File file)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory())
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			if (file.canRead() == false)
				throw new IOException("File '" + file + "' cannot be read");
		} else
			throw new FileNotFoundException("File '" + file
					+ "' does not exist");
		return new FileInputStream(file);
	}

	// -----------------------------------------------------------------------
	/**
	 * Open output stream.
	 *
	 * @param file
	 *            the file
	 * @return the file output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static FileOutputStream openOutputStream(final File file)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory())
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			if (file.canWrite() == false)
				throw new IOException("File '" + file
						+ "' cannot be written to");
		} else {
			final File parent = file.getParentFile();
			if ((parent != null) && (parent.exists() == false)) {
				if (parent.mkdirs() == false)
					throw new IOException("File '" + file
							+ "' could not be created");
			}
		}
		return new FileOutputStream(file);
	}

	/**
	 * Read file to string.
	 *
	 * @param file
	 *            the file
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readFileToString(final File file) throws IOException {
		return readFileToString(file, null);
	}

	// -----------------------------------------------------------------------
	/**
	 * Read file to string.
	 *
	 * @param file
	 *            the file
	 * @param encoding
	 *            the encoding
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readFileToString(final File file, final String encoding)
			throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return IOUtils.toString(in, encoding);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Size of directory.
	 *
	 * @param directory
	 *            the directory
	 * @return the long
	 */
	public static long sizeOfDirectory(final File directory) {
		if (!directory.exists()) {
			final String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}
		if (!directory.isDirectory()) {
			final String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}
		long size = 0;
		final File[] files = directory.listFiles();
		if (files == null)
			return 0L;
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
			if (file.isDirectory()) {
				size += sizeOfDirectory(file);
			} else {
				size += file.length();
			}
		}
		return size;
	}

	/** The Constant ONE_KB. */
	public static final long ONE_KB = 1024;

	/** The Constant ONE_MB. */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/** The Constant ONE_GB. */
	public static final long ONE_GB = ONE_KB * ONE_MB;

	/** The Constant EMPTY_FILE_ARRAY. */
	public static final File[] EMPTY_FILE_ARRAY = new File[0];

	/**
	 * Instantiates a new file utils.
	 */
	public FileUtils() {
		super();
	}
}
