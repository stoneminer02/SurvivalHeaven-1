/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Class ByteSource.
 */
public class ByteSource implements ZipEntrySource {

	/** The path. */
	private final String path;
	
	/** The bytes. */
	private final byte[] bytes;
	
	/** The time. */
	private final long time;

	/**
	 * Instantiates a new byte source.
	 *
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 */
	public ByteSource(final String path, final byte[] bytes) {
		this(path, bytes, System.currentTimeMillis());
	}

	/**
	 * Instantiates a new byte source.
	 *
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 * @param time
	 *            the time
	 */
	public ByteSource(final String path, final byte[] bytes, final long time) {
		this.path = path;
		this.bytes = bytes.clone();
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getEntry()
	 */
	@Override
	public ZipEntry getEntry() {
		final ZipEntry entry = new ZipEntry(path);
		if (bytes != null) {
			entry.setSize(bytes.length);
		}
		entry.setTime(time);
		return entry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		if (bytes == null)
			return null;
		else
			return new ByteArrayInputStream(bytes);
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
		return "ByteSource[" + path + "]";
	}
}
