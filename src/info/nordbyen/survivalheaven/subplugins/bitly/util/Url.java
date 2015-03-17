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
 * The Class Url.
 */
public class Url {

	/** The short base. */
	private String shortBase;
	
	/** The global hash. */
	private String globalHash;
	
	/** The user hash. */
	private String userHash;
	
	/** The short url. */
	private String shortUrl;
	
	/** The long url. */
	private String longUrl;

	/**
	 * Instantiates a new url.
	 */
	Url() {
	}

	/**
	 * Instantiates a new url.
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
	 */
	Url(final String shortBase, final String globalHash, final String userHash,
			final String shortUrl, final String longUrl) {
		this.shortBase = shortBase;
		this.globalHash = globalHash;
		this.userHash = userHash;
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
		if (this.shortUrl.length() == 0) {
			this.shortUrl = (shortBase + userHash);
		}
	}

	/**
	 * Gets the global hash.
	 *
	 * @return the global hash
	 */
	public String getGlobalHash() {
		return this.globalHash;
	}

	/**
	 * Gets the long url.
	 *
	 * @return the long url
	 */
	public String getLongUrl() {
		return this.longUrl;
	}

	/**
	 * Gets the short url.
	 *
	 * @return the short url
	 */
	public String getShortUrl() {
		return this.shortUrl;
	}

	/**
	 * Gets the user hash.
	 *
	 * @return the user hash
	 */
	public String getUserHash() {
		return this.userHash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Url [shortBase=" + this.shortBase + ", globalHash="
				+ this.globalHash + ", longUrl=" + this.longUrl + ", shortUrl="
				+ this.shortUrl + ", userHash=" + this.userHash + "]";
	}
}
