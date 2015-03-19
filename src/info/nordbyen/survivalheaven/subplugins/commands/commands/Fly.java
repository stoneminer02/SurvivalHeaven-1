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
 * The Class Fly.
 */
public class Fly implements CommandExecutor {

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
		if (command.getName().equalsIgnoreCase("fly")) {
			/**if (Sender.getName().equalsIgnoreCase("TheDudeAdrian")) {
				final Player g = (Player) Sender;
				g.setFlying(true);
				g.setAllowFlight(true);
				return false;
			} */
			if (Sender.hasPermission("sh.fly")) {
				if (args.length == 1) {
					final Player p = Bukkit.getPlayer(args[0]);
					if (p.getAllowFlight() == true) {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.RED + "av");
						p.setAllowFlight(false);
						p.setFlying(false);
					} else {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.GREEN + "på");
						p.setAllowFlight(true);
						p.setFlying(true);
					}
				} else if (args.length == 0) {
					final Player p = (Player) Sender;
					if (p.getAllowFlight() == true) {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.RED + "av");
						p.setAllowFlight(false);
						p.setFlying(false);
					} else {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.GREEN + "på");
						p.setAllowFlight(true);
						p.setFlying(true);
					}
				}
			}
		}
		return false;
	}
}
