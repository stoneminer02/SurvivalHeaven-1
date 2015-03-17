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
 * The Class Jobb.
 */
public class Jobb implements CommandExecutor {

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
			if (command.getName().equalsIgnoreCase("jobb")) {
				if (Sender.hasPermission("sh.kick")) {
					final Player p = (Player) Sender;
					final Inventory i = Bukkit.createInventory(p, 9);
					i.setItem(0, new ItemStack(Material.STICK));
					i.setItem(1, new ItemStack(Material.COMPASS));
					i.setItem(2, new ItemStack(Material.WOOD_AXE));
					i.setItem(4, new ItemStack(Material.WATER));
					i.setItem(6, new ItemStack(Material.LAVA));
					i.setItem(7, new ItemStack(Material.FIRE));
					i.setItem(8, new ItemStack(Material.BEDROCK));
					p.openInventory(i);
				}
			}
		}
		return false;
	}
}
