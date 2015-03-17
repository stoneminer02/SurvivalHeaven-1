/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
