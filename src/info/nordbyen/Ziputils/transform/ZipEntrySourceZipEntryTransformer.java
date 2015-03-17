/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

import info.nordbyen.Ziputils.ZipEntrySource;
import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipEntrySourceZipEntryTransformer.
 */
public class ZipEntrySourceZipEntryTransformer implements ZipEntryTransformer {

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
	static void addEntry(ZipEntrySource entry, ZipOutputStream out)
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

	/** The source. */
	private final ZipEntrySource source;

	/**
	 * Instantiates a new zip entry source zip entry transformer.
	 *
	 * @param source
	 *            the source
	 */
	public ZipEntrySourceZipEntryTransformer(ZipEntrySource source) {
		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.Ziputils.transform.ZipEntryTransformer#transform(java.io
	 * .InputStream, java.util.zip.ZipEntry, java.util.zip.ZipOutputStream)
	 */
	@Override
	public void transform(InputStream in, ZipEntry zipEntry, ZipOutputStream out)
			throws IOException {
		addEntry(source, out);
	}
}
