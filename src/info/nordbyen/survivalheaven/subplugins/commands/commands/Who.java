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
 * The Class Who.
 */
public class Who implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (sender.hasPermission("sh.who")) {
			if (command.getName().equalsIgnoreCase("who")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.GREEN + "Antall pålogget: "
							+ ChatColor.WHITE
							+ Bukkit.getOnlinePlayers().size());
					return true;
				} else if (args.length == 1) {
					final Player v = Bukkit.getServer().getPlayer(args[0]);
					sender.sendMessage((v == null ? ChatColor.GOLD + "[Alarm] "
							: ChatColor.GREEN)
							+ args[0]
							+ " er "
							+ (v == null ? "ikke " : "") + "pålogget");
					return true;
				}
			}
		}
		return false;
	}
}
