/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

/**
 * The Class LoggerAPI.
 */
public class LoggerAPI {

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static LoggerAPI getLogger() {
		return logger;
	}

	/** The logger. */
	private static LoggerAPI logger = new LoggerAPI();

	/**
	 * Info.
	 *
	 * @param plugin
	 *            the plugin
	 * @param message
	 *            the message
	 */
	public void info(final Plugin plugin, final String message) {
		log(plugin, Level.INFO, message);
	}

	/**
	 * Log.
	 *
	 * @param plugin
	 *            the plugin
	 * @param level
	 *            the level
	 * @param message
	 *            the message
	 */
	public void log(final Plugin plugin, final Level level, final String message) {
		plugin.getLogger().log(level, message);
	}

	/**
	 * Severe.
	 *
	 * @param plugin
	 *            the plugin
	 * @param message
	 *            the message
	 */
	public void severe(final Plugin plugin, final String message) {
		log(plugin, Level.SEVERE, message);
	}

	/**
	 * Warn.
	 *
	 * @param plugin
	 *            the plugin
	 * @param message
	 *            the message
	 */
	public void warn(final Plugin plugin, final String message) {
		log(plugin, Level.WARNING, message);
	}
}
