/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import java.io.IOException;
import java.util.zip.ZipEntry;

/**
 * The Interface ZipInfoCallback.
 */
public interface ZipInfoCallback {

	/**
	 * Process.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	void process(ZipEntry zipEntry) throws IOException;
}
