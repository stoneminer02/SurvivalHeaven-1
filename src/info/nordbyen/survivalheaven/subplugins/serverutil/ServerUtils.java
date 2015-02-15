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

package info.nordbyen.survivalheaven.subplugins.serverutil;

import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;
import info.nordbyen.survivalheaven.subplugins.commands.commands.ServerCommand;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class ServerUtils.
 */
@SurvivalHeavenSubPlugin(name = "ServerUtils")
public final class ServerUtils {

	/**
	 * Disable.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenDisable
	private static void disable(final JavaPlugin plugin) {
		unregisterCommands();
		unregisterListeners();
	}

	/**
	 * Enable.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenEnable
	private static void enable(final JavaPlugin plugin) {
		registerCommands();
		registerListeners();
	}

	/**
	 * Register commands.
	 */
	private static void registerCommands() {
		ServerCommand.initCommand();
	}

	/**
	 * Register listeners.
	 */
	private static void registerListeners() {
	}

	/**
	 * Unregister commands.
	 */
	private static void unregisterCommands() {
		ServerCommand.clearCommand();
	}

	/**
	 * Unregister listeners.
	 */
	private static void unregisterListeners() {
	}
}
