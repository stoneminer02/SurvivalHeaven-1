/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

import info.nordbyen.Ziputils.ByteSource;
import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class ByteArrayZipEntryTransformer.
 */
public abstract class ByteArrayZipEntryTransformer implements
		ZipEntryTransformer {

	/**
	 * Preserve timestamps.
	 *
	 * @return true, if successful
	 */
	protected boolean preserveTimestamps() {
		return false;
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
		byte[] bytes = IOUtils.toByteArray(in);
		bytes = transform(zipEntry, bytes);
		ByteSource source;
		if (preserveTimestamps()) {
			source = new ByteSource(zipEntry.getName(), bytes,
					zipEntry.getTime());
		} else {
			source = new ByteSource(zipEntry.getName(), bytes);
		}
		ZipEntrySourceZipEntryTransformer.addEntry(source, out);
	}

	/**
	 * Transform.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param input
	 *            the input
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected abstract byte[] transform(ZipEntry zipEntry, byte[] input)
			throws IOException;
}
