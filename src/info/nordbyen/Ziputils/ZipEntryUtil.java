/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipEntryUtil.
 */
class ZipEntryUtil {

	/**
	 * Adds the entry.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static void addEntry(final ZipEntry zipEntry, final InputStream in,
			final ZipOutputStream out) throws IOException {
		out.putNextEntry(zipEntry);
		if (in != null) {
			IOUtils.copy(in, out);
		}
		out.closeEntry();
	}

	/**
	 * Copy.
	 *
	 * @param original
	 *            the original
	 * @return the zip entry
	 */
	static ZipEntry copy(final ZipEntry original) {
		return copy(original, null);
	}

	/**
	 * Copy.
	 *
	 * @param original
	 *            the original
	 * @param newName
	 *            the new name
	 * @return the zip entry
	 */
	static ZipEntry copy(final ZipEntry original, final String newName) {
		final ZipEntry copy = new ZipEntry(newName == null ? original.getName()
				: newName);
		if (original.getCrc() != -1) {
			copy.setCrc(original.getCrc());
		}
		if (original.getMethod() != -1) {
			copy.setMethod(original.getMethod());
		}
		if (original.getSize() >= 0) {
			copy.setSize(original.getSize());
		}
		if (original.getExtra() != null) {
			copy.setExtra(original.getExtra());
		}
		copy.setComment(original.getComment());
		copy.setTime(original.getTime());
		return copy;
	}

	/**
	 * Copy entry.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static void copyEntry(final ZipEntry zipEntry, final InputStream in,
			final ZipOutputStream out) throws IOException {
		copyEntry(zipEntry, in, out, true);
	}

	/**
	 * Copy entry.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @param preserveTimestamps
	 *            the preserve timestamps
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static void copyEntry(final ZipEntry zipEntry, final InputStream in,
			final ZipOutputStream out, final boolean preserveTimestamps)
			throws IOException {
		final ZipEntry copy = copy(zipEntry);
		copy.setTime(preserveTimestamps ? zipEntry.getTime() : System
				.currentTimeMillis());
		addEntry(copy, new BufferedInputStream(in), out);
	}

	/**
	 * Instantiates a new zip entry util.
	 */
	private ZipEntryUtil() {
	}
}
