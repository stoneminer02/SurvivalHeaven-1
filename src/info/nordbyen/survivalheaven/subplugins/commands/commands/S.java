/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class S.
 */
public class S implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		final Player p = (Player) Sender;
		if (Sender.hasPermission("sh.s")) {
			if (command.getName().equalsIgnoreCase("s")) {
				if (Sender instanceof Player) {
					final Location pos = p.getWorld()
							.getHighestBlockAt(p.getLocation()).getLocation();
					p.teleport(pos);
					p.sendMessage(ChatColor.GREEN + "Poff!");
				} else {
					Sender.sendMessage(ChatColor.RED
							+ "Du er ikke en in-game spiller");
				}
			}
		}
		return true;
	}
}
