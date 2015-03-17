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

/**
 * The Class ZipExceptionUtil.
 */
class ZipExceptionUtil {

	/**
	 * Rethrow.
	 *
	 * @param e
	 *            the e
	 * @return the zip exception
	 */
	static ZipException rethrow(IOException e) {
		throw new ZipException(e);
	}
}
