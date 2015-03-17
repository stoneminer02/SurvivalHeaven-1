/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Class Fix.
 */
public class Fix implements CommandExecutor {

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
			if (Sender.hasPermission("sh.fix")) {
				if (args.length == 0) {
					((Player) Sender).getItemInHand().setDurability((short) 0);
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("alt")) {
						for (final ItemStack i : ((Player) Sender)
								.getInventory().getContents()) {
							i.setDurability((short) 0);
						}
					}
				}
			}
		}
		return false;
	}
}
