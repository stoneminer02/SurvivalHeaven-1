/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util;

/**
 * The Class BitlyException.
 */
public class BitlyException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8300631062123036696L;

	/**
	 * Instantiates a new bitly exception.
	 *
	 * @param message
	 *            the message
	 */
	BitlyException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new bitly exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	BitlyException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
