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
 * The Class Jmp.
 */
public class Jmp {

	/**
	 * As.
	 *
	 * @param user
	 *            the user
	 * @param apiKey
	 *            the api key
	 * @return the bitly. provider
	 */
	public static Bitly.Provider as(final String user, final String apiKey) {
		return new SimpleProvider("http://j.mp/", user, apiKey,
				"http://api.j.mp/v3/");
	}
}
