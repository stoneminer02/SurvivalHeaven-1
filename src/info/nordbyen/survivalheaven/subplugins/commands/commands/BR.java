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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class BR.
 */
public class BR implements CommandExecutor
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
			final String commandLabel, final String args[])
	{
		if (Sender instanceof Player)
		{
			if (args.length > 0)
			{
				final StringBuffer me = new StringBuffer();
				for (int i = 0; i < args.length; i++)
				{
					me.append(args[i] + " ");
				}
				if (command.getName().equalsIgnoreCase("bug"))
				{
					ArrayList<Player> stab = new ArrayList<Player>();
					for (final Player p : Bukkit.getOnlinePlayers())
					{
						if (p.hasPermission("sh.kick"))
						{
							if (p != null)
							{
								stab.add(p);
							}
						}
					}
					if (stab.size() == 0)
					{
						Sender.sendMessage(ChatColor.RED
								+ "Det er ingen i staben på serveren for øyeblikket.");
					} else
					{
						for (Player p : stab)
						{
							p.sendMessage(ChatColor.RED + "[Bug] "
									+ Sender.getName() + ": " + me);
						}
						Sender.sendMessage(ChatColor.GREEN
								+ "[Bug] Meldingen har blitt sendt til alle stab som er på for øyeblikket.");
					}
				}
			}
		}
		return false;
	}
}
