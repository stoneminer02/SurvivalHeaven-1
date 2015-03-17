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
 * The Class FSpeed.
 */
public class FSpeed implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String CommandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (p.hasPermission("sh.fspeef")) {
				if (command.getName().equalsIgnoreCase("fspeed")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "/fspeed <1-11>");
					} else if (args.length == 1) {
						switch (args[0].toLowerCase()) {
						case "1":
							p.setFlySpeed(0.1f);
							break;
						case "2":
							p.setFlySpeed(0.15f);
							break;
						case "3":
							p.setFlySpeed(0.2f);
							break;
						case "4":
							p.setFlySpeed(0.3f);
							break;
						case "5":
							p.setFlySpeed(0.4f);
							break;
						case "6":
							p.setFlySpeed(0.5f);
							break;
						case "7":
							p.setFlySpeed(0.6f);
							break;
						case "8":
							p.setFlySpeed(0.7f);
							break;
						case "9":
							p.setFlySpeed(0.8f);
							break;
						case "10":
							p.setFlySpeed(0.9f);
							break;
						case "11":
							p.setFlySpeed(1f);
							break;
						}
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "Du har ikke permission!");
			}
		} else {
			Sender.sendMessage(ChatColor.RED + "Du er ikke en spiller!");
		}
		return true;
	}
}
