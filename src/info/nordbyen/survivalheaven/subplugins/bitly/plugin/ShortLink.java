/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.plugin;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * The Class ShortLink.
 */
public class ShortLink extends SubPlugin implements Listener {

	/** The log. */
	static Logger log = Bukkit.getLogger();
	
	/** The instance. */
	static ShortLink instance;
	
	/** The key. */
	private String key;
	
	/** The username. */
	private String username;

	/**
	 * Instantiates a new short link.
	 *
	 * @param name
	 *            the name
	 */
	public ShortLink(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
		Bukkit.getScheduler().cancelTasks(getPlugin());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		instance = this;
		key = "R_402ff904a22447148961043124fab70c";
		username = "l0lkj";
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	/**
	 * On chat.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(final AsyncPlayerChatEvent event) {
		if (event.isCancelled())
			return;
		if (event.getMessage() == null)
			return;
		final ShortenUrlTask sut = new ShortenUrlTask(username, key,
				event.getMessage());
		event.setMessage(sut.parseStringForUrl());
		return;
	}
}
