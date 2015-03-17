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
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The Class Blokker.
 */
public class Blokker implements CommandExecutor {

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
			final Player p = (Player) Sender;
			if (p.hasPermission("sh.blokker")) {
				if (command.getName().equalsIgnoreCase("blokker")) {
					if (args.length == 1) {
						final Inventory b = (Bukkit.createInventory(p, 90,
								"Blokker"));
						for (int i = 0; i < 91; i++) {
							final Material m = Material.valueOf(args[0]
									.toUpperCase());
							final ItemStack r = new ItemStack(m, 64);
							b.setItem(i, r);
						}
						p.openInventory(b);
					}
				}
			}
		}
		return false;
	}
}
