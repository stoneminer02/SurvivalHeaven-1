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
import org.bukkit.command.CommandExecutor;

/**
 * The Class CommandAPI.
 */
public class CommandAPI {

	/** The commands. */
	private static HashMap<String, CommandExecutor> commands;

	/**
	 * Instantiates a new command api.
	 */
	public CommandAPI() {
		commands = new HashMap<String, CommandExecutor>();
	}

	/**
	 * Adds the command.
	 *
	 * @param command
	 *            the command
	 * @param executor
	 *            the executor
	 */
	public void addCommand(final String command, final CommandExecutor executor) {
		commands.put(command, executor);
	}

	/**
	 * Inits the command.
	 *
	 * @param command
	 *            the command
	 * @param executor
	 *            the executor
	 */
	public void initCommand(final String command, final CommandExecutor executor) {
		Bukkit.getPluginCommand(command).setExecutor(executor);
	}

	/**
	 * Inits the commands.
	 */
	public void initCommands() {
		for (final String command : commands.keySet()) {
			Bukkit.getPluginCommand(command).setExecutor(commands.get(command));
		}
	}
}
