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
 * The Class TPH.
 */
public class TPH implements CommandExecutor {

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
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			final Player targetPlayer = p.getServer().getPlayer(args[0]);
			if (p.hasPermission("sh.tph")) {
				if (command.getName().equalsIgnoreCase("tph")) {
					if ((args.length == 0) || (args.length > 1)) {
						p.sendMessage(ChatColor.RED + "/tph <spiller>");
					} else if (args.length == 1) {
						targetPlayer.teleport(p.getLocation());
						targetPlayer.sendMessage(ChatColor.GOLD
								+ "[Alarm] Du ble teleportert til "
								+ ChatColor.WHITE + p.getName());
						p.sendMessage(ChatColor.GOLD
								+ "[Alarm] Du teleporterte " + ChatColor.WHITE
								+ targetPlayer.getName() + ChatColor.GOLD
								+ " til deg.");
					}
				}
			}
		} else {
			Sender.sendMessage(ChatColor.RED + "Du er ikke en in game spiller");
		}
		return true;
	}
}
