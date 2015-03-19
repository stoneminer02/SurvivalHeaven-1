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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class TP.
 */
public class TP implements CommandExecutor
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args)
	{
		if (Sender instanceof Player)
		{
			final Player p = (Player) Sender;
			final Player targetPlayer = p.getServer().getPlayer(args[0]);
			if (p.hasPermission("sh.tp"))
			{
				if (command.getName().equalsIgnoreCase("tp"))
				{
					if (args.length == 0)
					{
						p.sendMessage(ChatColor.RED + "/tp <spiller>");
					} else if (args.length == 1)
					{
						final Location targetPlayerLocation = targetPlayer
								.getLocation();
						p.teleport(targetPlayerLocation);
						p.sendMessage(ChatColor.GOLD
								+ "[Alarm] Du ble teleportert til "
								+ targetPlayer.getName() + ".");
					} else if (args.length == 3)
					{
						final double x = Double.parseDouble(args[0]);
						final double z = Double.parseDouble(args[2]);
						final double y = Double.parseDouble(args[1]);
						p.teleport(new Location(p.getWorld(), x, y, z));
					}
				} else
				{
					p.sendMessage(ChatColor.RED + "For mange argumenter");
				}
			}
		} else
		{
			if (args.length == 4)
			{
				final Player tp = Bukkit.getServer().getPlayer(args[0]);
				final double x = Double.parseDouble(args[1]);
				final double z = Double.parseDouble(args[3]);
				final double y = Double.parseDouble(args[2]);
				tp.teleport(new Location(tp.getWorld(), x, y, z));
			} else
			{
				Sender.sendMessage(ChatColor.RED
						+ "Du er ikke en in game spiller");
				return true;
			}
		}
		return true;
	}
}
