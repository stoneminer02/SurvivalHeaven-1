/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.plugin;

import info.nordbyen.survivalheaven.subplugins.bitly.util.Bitly;
import info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyException;
import info.nordbyen.survivalheaven.subplugins.bitly.util.Url;

import java.io.IOException;
import java.net.URL;
import java.util.TimerTask;

import org.bukkit.Bukkit;

/**
 * The Class ShortenUrlTask.
 */
public class ShortenUrlTask extends TimerTask {

	/** The username. */
	private final String username;
	
	/** The key. */
	private final String key;
	
	/** The message. */
	private final String message;

	/**
	 * Instantiates a new shorten url task.
	 *
	 * @param username
	 *            the username
	 * @param key
	 *            the key
	 * @param message
	 *            the message
	 */
	public ShortenUrlTask(final String username, final String key,
			final String message) {
		this.username = username;
		this.key = key;
		this.message = message;
	}

	/**
	 * Parses the string for url.
	 *
	 * @return the string
	 */
	@SuppressWarnings("unused")
	public String parseStringForUrl() {
		String finalMessage = "";
		final String[] words = this.message.split(" ");
		for (int i = 0; i < words.length; i++) {
			URL url_;
			try {
				url_ = new URL(words[i]);
			} catch (final Exception e) {
				continue;
			}
			try {
				url_.openConnection();
			} catch (final IOException e1) {
				continue;
			}
			if (true || words[i]
					.matches("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,3})(/\\S*)?$")) {
				try {
					final Url url = Bitly.as(this.username, this.key).call(
							Bitly.shorten(words[i]));
					words[i] = url.getShortUrl();
				} catch (final BitlyException e) {
					final String http = "http://" + words[i];
					try {
						final Url url = Bitly.as(this.username, this.key).call(
								Bitly.shorten(http));
						words[i] = url.getShortUrl();
					} catch (final BitlyException e2) {
						Bukkit.getLogger()
								.warning("Malformed url: " + words[i]);
					}
				}
			}
		}
		for (final String string : words) {
			finalMessage += string + " ";
		}
		return finalMessage.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		parseStringForUrl();
	}
}
