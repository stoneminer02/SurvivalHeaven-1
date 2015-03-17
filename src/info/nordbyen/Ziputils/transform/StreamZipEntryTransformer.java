/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class StreamZipEntryTransformer.
 */
public abstract class StreamZipEntryTransformer implements ZipEntryTransformer {

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
		ZipEntry entry = new ZipEntry(zipEntry.getName());
		entry.setTime(System.currentTimeMillis());
		out.putNextEntry(entry);
		transform(zipEntry, in, out);
		out.closeEntry();
	}

	/**
	 * Transform.
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
	protected abstract void transform(ZipEntry zipEntry, InputStream in,
			OutputStream out) throws IOException;
}
