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
 * The Class ShortenedUrl.
 */
public class ShortenedUrl extends Url {

	/** The new hash. */
	private final boolean newHash;

	/**
	 * Instantiates a new shortened url.
	 *
	 * @param shortBase
	 *            the short base
	 * @param globalHash
	 *            the global hash
	 * @param userHash
	 *            the user hash
	 * @param shortUrl
	 *            the short url
	 * @param longUrl
	 *            the long url
	 * @param newHash
	 *            the new hash
	 */
	ShortenedUrl(final String shortBase, final String globalHash,
			final String userHash, final String shortUrl, final String longUrl,
			final boolean newHash) {
		super(shortBase, globalHash, userHash, shortUrl, longUrl);
		this.newHash = newHash;
	}

	/**
	 * Checks if is new hash.
	 *
	 * @return true, if is new hash
	 */
	public boolean isNewHash() {
		return this.newHash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.subplugins.bitly.util.Url#toString()
	 */
	@Override
	public String toString() {
		return "ShortenedUrl [newHash=" + this.newHash + ", getGlobalHash()="
				+ getGlobalHash() + ", getLongUrl()=" + getLongUrl()
				+ ", getShortUrl()=" + getShortUrl() + ", getUserHash()="
				+ getUserHash() + "]";
	}
}
