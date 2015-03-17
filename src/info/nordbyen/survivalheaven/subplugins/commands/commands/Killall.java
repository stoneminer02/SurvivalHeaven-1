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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * The Class Killall.
 */
public class Killall implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		if (!(sender instanceof Player))
			return false;
		if ((cmd.getName().equalsIgnoreCase("killall"))
				&& (sender.hasPermission("sh.killall"))) {
			for (final Entity element : ((Player) sender).getWorld()
					.getEntities()) {
				if ((element.getType() != EntityType.PLAYER)
						&& (element.getType() != EntityType.ITEM_FRAME)
						&& (element.getType() != EntityType.MINECART)) {
					element.remove();
				}
			}
			sender.sendMessage("Du fjernet " + ChatColor.YELLOW + " ALLE"
					+ ChatColor.RESET
					+ " monsterene og dyrene i denne verdenen.");
			return true;
		}
		return false;
	}
}
