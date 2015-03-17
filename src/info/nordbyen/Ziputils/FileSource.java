/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Class FileSource.
 */
public class FileSource implements ZipEntrySource {

	/**
	 * Pair.
	 *
	 * @param files
	 *            the files
	 * @param names
	 *            the names
	 * @return the file source[]
	 */
	public static FileSource[] pair(File[] files, String[] names) {
		if (files.length > names.length) {
			throw new IllegalArgumentException(
					"names array must contain "
							+ "at least the same amount of items as files array or more");
		}
		FileSource[] result = new FileSource[files.length];
		for (int i = 0; i < files.length; i++) {
			result[i] = new FileSource(names[i], files[i]);
		}
		return result;
	}

	/** The path. */
	private final String path;
	
	/** The file. */
	private final File file;

	/**
	 * Instantiates a new file source.
	 *
	 * @param path
	 *            the path
	 * @param file
	 *            the file
	 */
	public FileSource(String path, File file) {
		this.path = path;
		this.file = file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getEntry()
	 */
	@Override
	public ZipEntry getEntry() {
		ZipEntry entry = new ZipEntry(path);
		if (!file.isDirectory()) {
			entry.setSize(file.length());
		}
		entry.setTime(file.lastModified());
		return entry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		if (file.isDirectory()) {
			return null;
		} else {
			return new BufferedInputStream(new FileInputStream(file));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileSource[" + path + ", " + file + "]";
	}
}
