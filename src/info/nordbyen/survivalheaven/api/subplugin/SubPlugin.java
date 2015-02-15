/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
