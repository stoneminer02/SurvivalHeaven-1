/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util;

import java.util.Set;

/**
 * The Class Bitly.
 */
public final class Bitly {

	/**
	 * The Interface Provider.
	 */
	public static abstract interface Provider {

		/**
		 * Call.
		 *
		 * @param <A>
		 *            the generic type
		 * @param paramBitlyMethod
		 *            the param bitly method
		 * @return the a
		 */
		public abstract <A> A call(BitlyMethod<A> paramBitlyMethod);

		/**
		 * Gets the url.
		 *
		 * @return the url
		 */
		public abstract String getUrl();
	}

	/**
	 * As.
	 *
	 * @param user
	 *            the user
	 * @param apiKey
	 *            the api key
	 * @return the provider
	 */
	public static Provider as(final String user, final String apiKey) {
		return new SimpleProvider("http://bit.ly/", user, apiKey,
				"http://api.bit.ly/v3/");
	}

	/**
	 * Clicks.
	 *
	 * @param string
	 *            the string
	 * @return the bitly method
	 */
	public static BitlyMethod<UrlClicks> clicks(final String string) {
		return Methods.clicks(string);
	}

	/**
	 * Clicks.
	 *
	 * @param string
	 *            the string
	 * @return the bitly method
	 */
	public static BitlyMethod<Set<UrlClicks>> clicks(final String[] string) {
		return Methods.clicks(string);
	}

	/**
	 * Expand.
	 *
	 * @param value
	 *            the value
	 * @return the bitly method
	 */
	public static BitlyMethod<Url> expand(final String value) {
		return Methods.expand(value);
	}

	/**
	 * Expand.
	 *
	 * @param value
	 *            the value
	 * @return the bitly method
	 */
	public static BitlyMethod<Set<Url>> expand(final String[] value) {
		return Methods.expand(value);
	}

	/**
	 * Info.
	 *
	 * @param value
	 *            the value
	 * @return the bitly method
	 */
	public static BitlyMethod<UrlInfo> info(final String value) {
		return Methods.info(value);
	}

	/**
	 * Info.
	 *
	 * @param value
	 *            the value
	 * @return the bitly method
	 */
	public static BitlyMethod<Set<UrlInfo>> info(final String[] value) {
		return Methods.info(value);
	}

	/**
	 * Shorten.
	 *
	 * @param longUrl
	 *            the long url
	 * @return the bitly method
	 */
	public static BitlyMethod<ShortenedUrl> shorten(final String longUrl) {
		return Methods.shorten(longUrl);
	}
}
