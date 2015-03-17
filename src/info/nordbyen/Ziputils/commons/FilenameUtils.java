/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.commons;

import java.io.File;

/**
 * The Class FilenameUtils.
 */
public class FilenameUtils {

	// -----------------------------------------------------------------------
	/**
	 * Gets the prefix length.
	 *
	 * @param filename
	 *            the filename
	 * @return the prefix length
	 */
	public static int getPrefixLength(final String filename) {
		if (filename == null)
			return -1;
		final int len = filename.length();
		if (len == 0)
			return 0;
		char ch0 = filename.charAt(0);
		if (ch0 == ':')
			return -1;
		if (len == 1) {
			if (ch0 == '~')
				return 2; // return a length greater than the input
			return (isSeparator(ch0) ? 1 : 0);
		} else {
			if (ch0 == '~') {
				int posUnix = filename.indexOf(UNIX_SEPARATOR, 1);
				int posWin = filename.indexOf(WINDOWS_SEPARATOR, 1);
				if ((posUnix == -1) && (posWin == -1))
					return len + 1; // return a length greater than the input
				posUnix = (posUnix == -1 ? posWin : posUnix);
				posWin = (posWin == -1 ? posUnix : posWin);
				return Math.min(posUnix, posWin) + 1;
			}
			final char ch1 = filename.charAt(1);
			if (ch1 == ':') {
				ch0 = Character.toUpperCase(ch0);
				if ((ch0 >= 'A') && (ch0 <= 'Z')) {
					if ((len == 2)
							|| (isSeparator(filename.charAt(2)) == false))
						return 2;
					return 3;
				}
				return -1;
			} else if (isSeparator(ch0) && isSeparator(ch1)) {
				int posUnix = filename.indexOf(UNIX_SEPARATOR, 2);
				int posWin = filename.indexOf(WINDOWS_SEPARATOR, 2);
				if (((posUnix == -1) && (posWin == -1)) || (posUnix == 2)
						|| (posWin == 2))
					return -1;
				posUnix = (posUnix == -1 ? posWin : posUnix);
				posWin = (posWin == -1 ? posUnix : posWin);
				return Math.min(posUnix, posWin) + 1;
			} else
				return (isSeparator(ch0) ? 1 : 0);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Checks if is separator.
	 *
	 * @param ch
	 *            the ch
	 * @return true, if is separator
	 */
	private static boolean isSeparator(final char ch) {
		return (ch == UNIX_SEPARATOR) || (ch == WINDOWS_SEPARATOR);
	}

	// -----------------------------------------------------------------------
	/**
	 * Checks if is system windows.
	 *
	 * @return true, if is system windows
	 */
	static boolean isSystemWindows() {
		return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
	}

	/** The Constant EXTENSION_SEPARATOR. */
	public static final char EXTENSION_SEPARATOR = '.';
	
	/** The Constant EXTENSION_SEPARATOR_STR. */
	public static final String EXTENSION_SEPARATOR_STR = (new Character(
			EXTENSION_SEPARATOR)).toString();

	/** The Constant UNIX_SEPARATOR. */
	private static final char UNIX_SEPARATOR = '/';

	/** The Constant WINDOWS_SEPARATOR. */
	private static final char WINDOWS_SEPARATOR = '\\';

	/** The Constant SYSTEM_SEPARATOR. */
	private static final char SYSTEM_SEPARATOR = File.separatorChar;

	/**
	 * Instantiates a new filename utils.
	 */
	public FilenameUtils() {
		super();
	}
}
