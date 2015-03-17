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
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * The Class LogHandler.
 */
public class LogHandler extends Handler {

	/** The plugin. */
	private final RemoteBukkitPlugin plugin;

	/**
	 * Instantiates a new log handler.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public LogHandler(final RemoteBukkitPlugin plugin) {
		this.plugin = plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() throws SecurityException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public synchronized void publish(final LogRecord record) {
		this.plugin.broadcast(new SimpleDateFormat("hh:mm a").format(new Date(
				record.getMillis()))
				+ " ["
				+ record.getLevel().toString()
				+ "] " + record.getMessage());
	}
}
