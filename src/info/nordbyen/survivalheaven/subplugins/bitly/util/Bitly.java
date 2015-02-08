/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
