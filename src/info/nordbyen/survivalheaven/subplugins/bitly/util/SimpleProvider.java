/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util;

import info.nordbyen.survivalheaven.subplugins.bitly.util.data.Pair;
import info.nordbyen.survivalheaven.subplugins.bitly.util.utils.Dom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class SimpleProvider.
 */
class SimpleProvider implements Bitly.Provider {

	/** The url. */
	private final String url;
	
	/** The user. */
	private final String user;
	
	/** The api key. */
	private final String apiKey;
	
	/** The end point. */
	private final String endPoint;

	/**
	 * Instantiates a new simple provider.
	 *
	 * @param url
	 *            the url
	 * @param user
	 *            the user
	 * @param apiKey
	 *            the api key
	 * @param endPoint
	 *            the end point
	 */
	SimpleProvider(final String url, final String user, final String apiKey,
			final String endPoint) {
		this.url = url;
		this.user = user;
		this.apiKey = apiKey;
		this.endPoint = endPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.bitly.util.Bitly.Provider#call(
	 * info.nordbyen.survivalheaven .subplugins.bitly.util.BitlyMethod)
	 */
	@Override
	public <A> A call(final BitlyMethod<A> m) {
		final String url = getUrlForCall(m);
		final Document response = filterErrorResponse(fetchUrl(url));
		return m.apply(this, response);
	}

	/**
	 * Fetch url.
	 *
	 * @param url
	 *            the url
	 * @return the document
	 */
	private Document fetchUrl(final String url) {
		try {
			final HttpURLConnection openConnection = (HttpURLConnection) new URL(
					url).openConnection();
			if (openConnection.getResponseCode() == 200)
				return DocumentBuilderFactory.newInstance()
						.newDocumentBuilder()
						.parse(openConnection.getInputStream());
			throw new BitlyException("Transport error! "
					+ openConnection.getResponseCode() + " "
					+ openConnection.getResponseMessage());
		} catch (final IOException e) {
			throw new BitlyException("Transport I/O error!", e);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Filter error response.
	 *
	 * @param doc
	 *            the doc
	 * @return the document
	 */
	private Document filterErrorResponse(final Document doc) {
		final Node statusCode = doc.getElementsByTagName("status_code").item(0);
		final Node statusText = doc.getElementsByTagName("status_txt").item(0);
		if ((statusCode == null) || (statusText == null))
			throw new BitlyException(
					"Unexpected response (no status and/or message)!");
		final int code = Integer.parseInt(Dom.getTextContent(statusCode));
		if (code == 200)
			return doc;
		throw new BitlyException(Dom.getTextContent(statusText));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.bitly.util.Bitly.Provider#getUrl
	 * ()
	 */
	@Override
	public String getUrl() {
		return this.url;
	}

	/**
	 * Gets the url for call.
	 *
	 * @param m
	 *            the m
	 * @return the url for call
	 */
	@SuppressWarnings("rawtypes")
	protected String getUrlForCall(final BitlyMethod<?> m) {
		final StringBuilder sb = new StringBuilder(this.endPoint)
				.append(m.getName() + "?").append("&login=").append(this.user)
				.append("&apiKey=").append(this.apiKey).append("&format=xml");
		try {
			for (final Pair p : m.getParameters()) {
				sb.append("&" + (String) p.getOne() + "="
						+ URLEncoder.encode((String) p.getTwo(), "UTF-8"));
			}
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleProvider [apiKey=" + this.apiKey + ", endPoint="
				+ this.endPoint + ", url=" + this.url + ", user=" + this.user
				+ "]";
	}
}
