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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Interface ZipEntryTransformer.
 */
public interface ZipEntryTransformer {

	/**
	 * Transform.
	 *
	 * @param in
	 *            the in
	 * @param zipEntry
	 *            the zip entry
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void transform(InputStream in, ZipEntry zipEntry, ZipOutputStream out)
			throws IOException;
}
