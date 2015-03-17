/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Sudo.
 */
public class Sudo implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String args[]) {
		if (args.length > 1) {
			if (command.getName().equalsIgnoreCase("sudo")) {
				if (Sender.hasPermission("sh.kick")) {
					final Player p = Bukkit.getPlayer(args[0]);
					if (p == null) {
						Sender.sendMessage(ChatColor.RED
								+ "Spilleren er ikke på");
					} else {
						final StringBuffer me = new StringBuffer();
						for (int i = 1; i < args.length; i++) {
							me.append(args[i] + " ");
						}
						p.performCommand(me.toString());
					}
				}
			}
		}
		return false;
	}
}
