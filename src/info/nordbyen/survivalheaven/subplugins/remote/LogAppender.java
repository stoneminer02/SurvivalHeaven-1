/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.remote;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

/**
 * The Class LogAppender.
 */
public class LogAppender extends AbstractAppender {

	/** The plugin. */
	private final RemoteBukkitPlugin plugin;

	/**
	 * Instantiates a new log appender.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public LogAppender(final RemoteBukkitPlugin plugin) {
		super("RemoteController", null, null);
		this.plugin = plugin;
		start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.logging.log4j.core.Appender#append(org.apache.logging.log4j
	 * .core.LogEvent)
	 */
	@Override
	public void append(final LogEvent event) {
		this.plugin.broadcast(new SimpleDateFormat("hh:mm a").format(new Date(
				event.getMillis()))
				+ " ["
				+ event.getLevel().toString()
				+ "] "
				+ event.getMessage().getFormattedMessage());
	}
}
