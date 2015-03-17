/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * The Class EventAPI.
 */
public class EventAPI {

	/** The events. */
	private static HashMap<Listener, Plugin> events;

	/**
	 * Instantiates a new event api.
	 */
	public EventAPI() {
		events = new HashMap<Listener, Plugin>();
	}

	/**
	 * Adds the event.
	 *
	 * @param listener
	 *            the listener
	 * @param plugin
	 *            the plugin
	 */
	public void addEvent(final Listener listener, final Plugin plugin) {
		events.put(listener, plugin);
	}

	/**
	 * Register event.
	 *
	 * @param listener
	 *            the listener
	 * @param plugin
	 *            the plugin
	 */
	public void registerEvent(final Listener listener, final Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}

	/**
	 * Register events.
	 */
	public void registerEvents() {
		for (final Listener listener : events.keySet()) {
			Bukkit.getPluginManager().registerEvents(listener,
					events.get(listener));
		}
	}
}
