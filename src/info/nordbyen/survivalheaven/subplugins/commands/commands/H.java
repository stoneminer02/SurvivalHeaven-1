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
 * The Class H.
 */
public class H implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String commandLabel, final String[] args) {
		final StringBuffer me = new StringBuffer();
		for (final String arg : args) {
			me.append(arg + " ");
		}
		if (sender.hasPermission("sh.h")) {
			if (command.getName().equalsIgnoreCase("h")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "/h <tekst>");
				} else if (args.length >= 1) {
					final Player p = (Player) sender;
					Bukkit.broadcastMessage(ChatColor.DARK_GREEN
							+ "["
							+ ChatColor.GREEN
							+ "Kjøp/Salg"
							+ ChatColor.DARK_GREEN
							+ "] "
							+ ChatColor.DARK_GRAY
							+ "("
							+ ChatColor.GRAY
							+ p.getWorld().getName()
									.replaceAll("world", "Normal")
							+ ChatColor.DARK_GRAY + ") " + ChatColor.YELLOW
							+ sender.getName() + ChatColor.WHITE + ": " + me);
				}
			}
		}
		return true;
	}
}
