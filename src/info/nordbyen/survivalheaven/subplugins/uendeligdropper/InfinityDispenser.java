/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.Dispensers;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class InfinityDispenser.
 */
@SurvivalHeavenSubPlugin(name = "InfDisp")
public class InfinityDispenser {

	/**
	 * Disable.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenDisable
	private static void disable(final JavaPlugin plugin) {
	}

	/**
	 * Enable.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenEnable
	private static void enable(final JavaPlugin plugin) {
		dispensers = new Dispensers();
		final PluginManager pluginmanager = plugin.getServer()
				.getPluginManager();
		pluginmanager.registerEvents(new InfinityDispenserListener(), plugin);
		pluginmanager.registerEvents(new BlockListener(), plugin);
		plugin.getCommand("infdisp").setExecutor(new Commands());
	}

	/**
	 * Gets the dispensers.
	 *
	 * @return the dispensers
	 */
	public static Dispensers getDispensers() {
		return dispensers;
	}

	/** The plugin. */
	public static Plugin plugin;
	
	/** The instance. */
	public static InfinityDispenser instance;

	/** The config. */
	public static FileConfiguration config;

	/** The Constant log. */
	public static final Logger log = Logger.getLogger("Minecraft");

	/** The dispensers. */
	private static Dispensers dispensers;
}
