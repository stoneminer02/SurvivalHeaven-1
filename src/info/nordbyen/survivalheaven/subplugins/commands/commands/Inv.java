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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Inv.
 */
public class Inv implements CommandExecutor {

	/**
	 * Instantiates a new inv.
	 */
	public Inv() {
		// TODO Auto-generated constructor stub
	}

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
		final Player targetPlayer = p.getServer().getPlayer(args[0]);
		if (p.hasPermission("cd.inv")) {
			if (command.getName().equalsIgnoreCase("inv")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.RED + "/inv <spiller>");
				} else if (args.length == 1) {
					p.openInventory(targetPlayer.getInventory());
				}
			}
		}
		return true;
	}
}
