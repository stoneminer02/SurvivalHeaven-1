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
 * The Class UrlInfo.
 */
public class UrlInfo {

	/** The url. */
	private final Url url;
	
	/** The created by. */
	private final String createdBy;
	
	/** The title. */
	private final String title;

	/**
	 * Instantiates a new url info.
	 *
	 * @param url
	 *            the url
	 * @param createdBy
	 *            the created by
	 * @param title
	 *            the title
	 */
	UrlInfo(final Url url, final String createdBy, final String title) {
		this.url = url;
		this.createdBy = createdBy;
		this.title = title;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public Url getUrl() {
		return this.url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Info [createdBy=" + this.createdBy + ", title=" + this.title
				+ ", url=" + this.url + "]";
	}
}
