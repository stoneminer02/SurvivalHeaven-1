/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

/**
 * The Class CommandUtils.
 */
public class CommandUtils {

	/**
	 * Gets the help topics.
	 *
	 * @return the help topics
	 */
	public static List<HelpTopic> getHelpTopics() {
		final List<HelpTopic> helpTopics = new ArrayList<HelpTopic>();
		for (final HelpTopic cmdLabel : Bukkit.getServer().getHelpMap()
				.getHelpTopics()) {
			helpTopics.add(cmdLabel);
		}
		return helpTopics;
	}

	/**
	 * Gets the known commands.
	 *
	 * @return the known commands
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static Map<String, Command> getKnownCommands()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		final PluginManager manager = Bukkit.getServer().getPluginManager();
		final SimplePluginManager spm = (SimplePluginManager) manager;
		List<Plugin> plugins = null;
		Map<String, Plugin> lookupNames = null;
		SimpleCommandMap commandMap = null;
		Map<String, Command> knownCommands = null;
		if (spm != null) {
			final Field pluginsField = spm.getClass().getDeclaredField(
					"plugins");
			final Field lookupNamesField = spm.getClass().getDeclaredField(
					"lookupNames");
			final Field commandMapField = spm.getClass().getDeclaredField(
					"commandMap");
			pluginsField.setAccessible(true);
			lookupNamesField.setAccessible(true);
			commandMapField.setAccessible(true);
			plugins = (List<Plugin>) pluginsField.get(spm);
			lookupNames = (Map<String, Plugin>) lookupNamesField.get(spm);
			commandMap = (SimpleCommandMap) commandMapField.get(spm);
			final Field knownCommandsField = commandMap.getClass()
					.getDeclaredField("knownCommands");
			knownCommandsField.setAccessible(true);
			knownCommands = (Map<String, Command>) knownCommandsField
					.get(commandMap);
		}
		return knownCommands;
		/*
		 * if (commandMap != null) { for (Iterator<Map.Entry<String, Command>>
		 * it = knownCommands.entrySet().iterator(); it.hasNext()) {
		 * Map.Entry<String, Command> entry = it.next(); if (entry.getValue()
		 * instanceof PluginCommand) { PluginCommand c = (PluginCommand)
		 * entry.getValue(); } } }
		 */
	}
}
