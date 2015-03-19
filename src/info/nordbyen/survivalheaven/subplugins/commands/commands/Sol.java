/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Sol.
 */
public class Sol implements CommandExecutor
{

	/** The i. */
	int i = 0;

	/** The sol. */
	ArrayList<String> sol = new ArrayList<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String args[])
	{
		if (Sender instanceof Player)
		{
			if (!(sol.contains(Sender.getName())))
			{
				if (command.getName().equalsIgnoreCase("sol"))
				{
					if (args.length == 0)
					{
						if (((Player) Sender).getWorld().hasStorm() == true)
						{
							i += 1;
							sol.add(Sender.getName());
							if (i < (Bukkit.getOnlinePlayers().size() / 2))
							{
								Bukkit.broadcastMessage(ChatColor.BLUE
										+ Sender.getName()
										+ ChatColor.AQUA
										+ " stemte på å få sol! "
										+ i
										+ "/"
										+ (Bukkit.getOnlinePlayers().size() / 2)
										+ "har stemt. Skriv /sol for å stemme");
							} else
							{
								for (final World w : Bukkit.getWorlds())
								{
									w.setStorm(false);
									w.setThundering(false);
								}
								sol.clear();
								Bukkit.broadcastMessage(ChatColor.BLUE
										+ Sender.getName()
										+ ChatColor.AQUA
										+ " stemte på å få sol! "
										+ i
										+ "/"
										+ (Bukkit.getOnlinePlayers().size() / 2));
								Bukkit.broadcastMessage(ChatColor.AQUA
										+ "Været ble satt til sol!");
								i = 0;
							}
						} else
						{
							Sender.sendMessage(ChatColor.RED
									+ "Det er allerede sol i din verden");
						}
					}
				}
			} else
			{
				Sender.sendMessage(ChatColor.AQUA + "Du har allerede stemt!");
			}
		}
		return false;
	}
}
