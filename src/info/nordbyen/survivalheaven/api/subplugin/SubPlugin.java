/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.subplugin;

import info.nordbyen.survivalheaven.ISH;
import info.nordbyen.survivalheaven.SH;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class SubPlugin.
 */
public abstract class SubPlugin {

	/** The name. */
	private final String name;
	
	/** The plugin. */
	private final JavaPlugin plugin;
	
	/** The manager. */
	@SuppressWarnings("unused")
	private final ISH manager;
	
	/** The enabled. */
	private boolean enabled;

	/**
	 * Instantiates a new sub plugin.
	 *
	 * @param name
	 *            the name
	 */
	public SubPlugin(final String name) {
		this.name = name;
		this.plugin = SH.getPlugin();
		this.manager = SH.getManager();
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.GOLD + "Enabling subplugin: " + ChatColor.YELLOW
						+ name);
	}

	/**
	 * Disable.
	 */
	protected abstract void disable();

	/**
	 * Disable plugin.
	 */
	public final void disablePlugin() {
		if (!enabled)
			return;
		enabled = false;
		disable();
	}

	/**
	 * Enable.
	 */
	protected abstract void enable();

	/**
	 * Enable plugin.
	 */
	public final void enablePlugin() {
		if (enabled)
			return;
		enabled = true;
		enable();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
	public final JavaPlugin getPlugin() {
		return plugin;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public final boolean isEnabled() {
		return enabled;
	}
}
