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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class Methods.
 */
final class Methods {

	/**
	 * Clicks.
	 *
	 * @param string
	 *            the string
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<UrlClicks> clicks(final String string) {
		return new MethodBase("clicks", new Pair[] { Pair.p(hashOrUrl(string),
				string) }) {

			@Override
			public UrlClicks apply(final Bitly.Provider provider,
					final Document document) {
				return Methods.parseClicks(provider, document
						.getElementsByTagName("clicks").item(0));
			}
		};
	}

	/**
	 * Clicks.
	 *
	 * @param string
	 *            the string
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<Set<UrlClicks>> clicks(final String[] string) {
		return new MethodBase("clicks", getUrlMethodArgs(string)) {

			@Override
			public Set<UrlClicks> apply(final Bitly.Provider provider,
					final Document document) {
				final HashSet clicks = new HashSet();
				final NodeList nl = document.getElementsByTagName("clicks");
				for (int i = 0; i < nl.getLength(); i++) {
					clicks.add(Methods.parseClicks(provider, nl.item(i)));
				}
				return clicks;
			}
		};
	}

	/**
	 * Expand.
	 *
	 * @param values
	 *            the values
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<Url> expand(final String values) {
		return new MethodBase("expand",
				getUrlMethodArgs(new String[] { values })) {

			@Override
			public Url apply(final Bitly.Provider provider,
					final Document document) {
				return Methods.parseUrl(provider, document
						.getElementsByTagName("entry").item(0));
			}
		};
	}

	/**
	 * Expand.
	 *
	 * @param values
	 *            the values
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<Set<Url>> expand(final String[] values) {
		return new MethodBase("expand", getUrlMethodArgs(values)) {

			@Override
			public Set<Url> apply(final Bitly.Provider provider,
					final Document document) {
				final HashSet inf = new HashSet();
				final NodeList infos = document.getElementsByTagName("entry");
				for (int i = 0; i < infos.getLength(); i++) {
					inf.add(Methods.parseUrl(provider, infos.item(i)));
				}
				return inf;
			}
		};
	}

	/**
	 * Gets the url method args.
	 *
	 * @param value
	 *            the value
	 * @return the url method args
	 */
	static Collection<Pair<String, String>> getUrlMethodArgs(
			final String[] value) {
		final List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
		for (final String p : value) {
			pairs.add(Pair.p(hashOrUrl(p), p));
		}
		return pairs;
	}

	/**
	 * Hash or url.
	 *
	 * @param p
	 *            the p
	 * @return the string
	 */
	static String hashOrUrl(final String p) {
		return p.startsWith("http://") ? "shortUrl" : "hash";
	}

	/**
	 * Info.
	 *
	 * @param value
	 *            the value
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<UrlInfo> info(final String value) {
		return new MethodBase("info", getUrlMethodArgs(new String[] { value })) {

			@Override
			public UrlInfo apply(final Bitly.Provider provider,
					final Document document) {
				return Methods.parseInfo(provider, document
						.getElementsByTagName("info").item(0));
			}
		};
	}

	/**
	 * Info.
	 *
	 * @param values
	 *            the values
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<Set<UrlInfo>> info(final String[] values) {
		return new MethodBase("info", getUrlMethodArgs(values)) {

			@Override
			public Set<UrlInfo> apply(final Bitly.Provider provider,
					final Document document) {
				final HashSet inf = new HashSet();
				final NodeList infos = document.getElementsByTagName("info");
				for (int i = 0; i < infos.getLength(); i++) {
					inf.add(Methods.parseInfo(provider, infos.item(i)));
				}
				return inf;
			}
		};
	}

	/**
	 * Parses the clicks.
	 *
	 * @param provider
	 *            the provider
	 * @param item
	 *            the item
	 * @return the url clicks
	 */
	static UrlClicks parseClicks(final Bitly.Provider provider, final Node item) {
		final NodeList nl = item.getChildNodes();
		long user = 0L;
		long global = 0L;
		for (int i = 0; i < nl.getLength(); i++) {
			final String name = nl.item(i).getNodeName();
			final String value = Dom.getTextContent(nl.item(i));
			if ("user_clicks".equals(name)) {
				user = Long.parseLong(value);
			} else if ("global_clicks".equals(name)) {
				global = Long.parseLong(value);
			}
		}
		return new UrlClicks(parseUrl(provider, item), user, global);
	}

	/**
	 * Parses the info.
	 *
	 * @param provider
	 *            the provider
	 * @param nl
	 *            the nl
	 * @return the url info
	 */
	static UrlInfo parseInfo(final Bitly.Provider provider, final Node nl) {
		final NodeList il = nl.getChildNodes();
		String title = "";
		String createdBy = "";
		for (int i = 0; i < il.getLength(); i++) {
			final Node n = il.item(i);
			final String name = n.getNodeName();
			final String value = Dom.getTextContent(n);
			if ("created_by".equals(name)) {
				createdBy = value;
			} else if ("title".equals(name)) {
				title = value;
			}
		}
		return new UrlInfo(parseUrl(provider, nl), createdBy, title);
	}

	/**
	 * Parses the shortened url.
	 *
	 * @param provider
	 *            the provider
	 * @param nl
	 *            the nl
	 * @return the shortened url
	 */
	static ShortenedUrl parseShortenedUrl(final Bitly.Provider provider,
			final Node nl) {
		String gHash = "";
		String uHash = "";
		String sUrl = "";
		String lUrl = "";
		String isNew = "";
		final NodeList il = nl.getChildNodes();
		for (int i = 0; i < il.getLength(); i++) {
			final Node n = il.item(i);
			final String name = n.getNodeName();
			final String value = Dom.getTextContent(n).trim();
			if ("new_hash".equals(name)) {
				isNew = value;
			} else if ("url".equals(name)) {
				sUrl = value;
			} else if ("long_url".equals(name)) {
				lUrl = value;
			} else if ("global_hash".equals(name)) {
				gHash = value;
			} else if ("hash".equals(name)) {
				uHash = value;
			}
		}
		return new ShortenedUrl(provider.getUrl(), gHash, uHash, sUrl, lUrl,
				isNew.equals("1"));
	}

	/**
	 * Parses the url.
	 *
	 * @param provider
	 *            the provider
	 * @param nl
	 *            the nl
	 * @return the url
	 */
	static Url parseUrl(final Bitly.Provider provider, final Node nl) {
		String gHash = "";
		String uHash = "";
		String sUrl = "";
		String lUrl = "";
		final NodeList il = nl.getChildNodes();
		for (int i = 0; i < il.getLength(); i++) {
			final Node n = il.item(i);
			final String name = n.getNodeName();
			final String value = Dom.getTextContent(n);
			if ("short_url".equals(name)) {
				sUrl = value;
			} else if ("long_url".equals(name)) {
				lUrl = value;
			} else if ("global_hash".equals(name)) {
				gHash = value;
			} else if ("user_hash".equals(name)) {
				uHash = value;
			} else if ("hash".equals(name)) {
				uHash = value;
			}
		}
		return new Url(provider.getUrl(), gHash, uHash, sUrl, lUrl);
	}

	/**
	 * Shorten.
	 *
	 * @param longUrl
	 *            the long url
	 * @return the bitly method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BitlyMethod<ShortenedUrl> shorten(final String longUrl) {
		return new MethodBase("shorten",
				new Pair[] { Pair.p("longUrl", longUrl) }) {

			@Override
			public ShortenedUrl apply(final Bitly.Provider provider,
					final Document document) {
				final NodeList infos = document.getElementsByTagName("data");
				return Methods.parseShortenedUrl(provider, infos.item(0));
			}
		};
	}
}
