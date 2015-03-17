/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

/**
 * The Class ZipException.
 */
public class ZipException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5571111112619349468L;

	/**
	 * Instantiates a new zip exception.
	 *
	 * @param e
	 *            the e
	 */
	public ZipException(Exception e) {
		super(e);
	}

	/**
	 * Instantiates a new zip exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public ZipException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new zip exception.
	 *
	 * @param msg
	 *            the msg
	 * @param cause
	 *            the cause
	 */
	public ZipException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
