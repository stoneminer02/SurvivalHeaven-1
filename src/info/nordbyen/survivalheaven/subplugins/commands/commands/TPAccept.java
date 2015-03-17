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
 * The Class TPAccept.
 */
public class TPAccept implements CommandExecutor {

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
		if (Sender instanceof Player) {
			if (args.length == 0) {
				if (command.getName().equalsIgnoreCase("tpaccept")) {
					if (TPA.tpa.containsKey(Sender.getName())) {
						final Player p = Bukkit.getPlayer(TPA.tpa.get(Sender
								.getName()));
						p.teleport(((Player) Sender).getLocation());
						p.sendMessage(ChatColor.GREEN + "poff");
						TPA.tpa.remove(Sender.getName());
						Sender.sendMessage(ChatColor.GREEN + p.getName()
								+ " ble teleportert til deg");
					} else {
						Sender.sendMessage(ChatColor.RED
								+ "Du har ingen teleport forespørsel");
					}
				}
			}
		}
		return false;
	}
}
