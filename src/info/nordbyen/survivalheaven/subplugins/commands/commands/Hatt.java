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
import org.bukkit.inventory.ItemStack;

/**
 * The Class Hatt.
 */
public class Hatt implements CommandExecutor {

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
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "Kan bare utføres av In game spillere");
			return true;
		}
		final Player p = (Player) sender;
		final ItemStack helmet = p.getInventory().getItemInHand();
		if (p.hasPermission("sh.hatt")) {
			if (command.getName().equalsIgnoreCase("hatt")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.GREEN + "Hatt satt!");
					p.getInventory()
							.setItemInHand(p.getInventory().getHelmet());
					p.getInventory().setHelmet(helmet);
				}
			}
		}
		return true;
	}
}
