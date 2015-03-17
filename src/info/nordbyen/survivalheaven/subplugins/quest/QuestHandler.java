/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest;

import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;
import info.nordbyen.survivalheaven.subplugins.quest.first_encounter.FirstEncounterConfig;
import info.nordbyen.survivalheaven.subplugins.quest.first_encounter.FirstEncounterListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class QuestHandler.
 */
@SurvivalHeavenSubPlugin(name = "QuestHandler")
public class QuestHandler {

	/**
	 * Disble.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenDisable
	private static void disble(final JavaPlugin plugin) {
	}

	/**
	 * Enable.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenEnable
	private static void enable(final JavaPlugin plugin) {
		FirstEncounterConfig.getInstance();
		Bukkit.getPluginManager().registerEvents(new FirstEncounterListener(),
				plugin);
		Godta_Command.initCommand();
	}
}
