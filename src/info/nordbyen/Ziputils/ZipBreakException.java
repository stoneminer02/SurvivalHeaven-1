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
 * The Class ZipBreakException.
 */
public class ZipBreakException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new zip break exception.
	 */
	public ZipBreakException() {
		super();
	}

	/**
	 * Instantiates a new zip break exception.
	 *
	 * @param e
	 *            the e
	 */
	public ZipBreakException(Exception e) {
		super(e);
	}

	/**
	 * Instantiates a new zip break exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public ZipBreakException(String msg) {
		super(msg);
	}
}
