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
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Interface ZipEntrySource.
 */
public interface ZipEntrySource {

	/**
	 * Gets the entry.
	 *
	 * @return the entry
	 */
	ZipEntry getEntry();

	/**
	 * Gets the input stream.
	 *
	 * @return the input stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	InputStream getInputStream() throws IOException;

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	String getPath();
}
