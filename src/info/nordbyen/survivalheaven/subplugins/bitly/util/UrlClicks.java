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
 * The Class UrlClicks.
 */
public class UrlClicks {

	/** The user clicks. */
	private final long userClicks;
	
	/** The global clicks. */
	private final long globalClicks;
	
	/** The url. */
	private final Url url;

	/**
	 * Instantiates a new url clicks.
	 *
	 * @param url
	 *            the url
	 * @param userClicks
	 *            the user clicks
	 * @param globalClicks
	 *            the global clicks
	 */
	UrlClicks(final Url url, final long userClicks, final long globalClicks) {
		this.url = url;
		this.userClicks = userClicks;
		this.globalClicks = globalClicks;
	}

	/**
	 * Gets the global clicks.
	 *
	 * @return the global clicks
	 */
	public long getGlobalClicks() {
		return this.globalClicks;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public Url getUrl() {
		return this.url;
	}

	/**
	 * Gets the user clicks.
	 *
	 * @return the user clicks
	 */
	public long getUserClicks() {
		return this.userClicks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UrlClicks [globalClicks=" + this.globalClicks + ", userClicks="
				+ this.userClicks + ", url=" + this.url + "]";
	}
}
