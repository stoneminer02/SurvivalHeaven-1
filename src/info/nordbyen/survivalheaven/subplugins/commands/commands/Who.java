/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.api.rankmanager.RankType;
import info.nordbyen.survivalheaven.subplugins.rankmanager.RankManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Who.
 */
public class Who implements CommandExecutor
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args)
	{
		if (sender.hasPermission("sh.who"))
		{
			if (command.getName().equalsIgnoreCase("who"))
			{
				if (args.length == 0)
				{
					Player[] players = new Player[Bukkit.getOnlinePlayers()
							.size() - 1];
					int at = 0;
					for (Player player : Bukkit.getOnlinePlayers())
					{
						players[at] = player;
						at++;
					}

					StringBuilder brukere = new StringBuilder();
					StringBuilder stab = new StringBuilder();
					for (int i = 0; i <= Bukkit.getOnlinePlayers().size(); i++)
					{
						RankType rank = RankManager.getInstance().getRank(
								String.valueOf(players[i].getUniqueId()));

						if (i == Bukkit.getOnlinePlayers().size())
						{
							if (rank == RankType.BRUKER)
								brukere.append(ChatColor.RESET
										+ players[i].getName());
							else
								stab.append(RankManager.getInstance()
										.getChatRankPrefix(
												String.valueOf(players[i]
														.getUniqueId())
														+ players[i].getName()));
						} else
						{
							if (rank == RankType.BRUKER)
								brukere.append(ChatColor.RESET
										+ players[i].getName()
										+ ChatColor.YELLOW + ", ");
							else
								stab.append(ChatColor.RESET
										+ ""
										+ RankManager
												.getInstance()
												.getChatRankPrefix(
														String.valueOf(players[i]
																.getUniqueId())
																+ players[i]
																		.getName()
																+ ChatColor.YELLOW
																+ ", "));
						}
					}

					sender.sendMessage(ChatColor.GREEN + "Brukere pålogget: ");
					sender.sendMessage(brukere.toString());
					sender.sendMessage(ChatColor.GREEN
							+ "Alle i staben pålogget:");
					sender.sendMessage(stab.toString());
					sender.sendMessage(ChatColor.GREEN + "Antall pålogget: "
							+ ChatColor.WHITE
							+ Bukkit.getOnlinePlayers().size());
					return true;
				} else if (args.length == 1)
				{
					final Player v = Bukkit.getServer().getPlayer(args[0]);
					sender.sendMessage((v == null ? ChatColor.GOLD + "[Alarm] "
							: ChatColor.GREEN)
							+ args[0]
							+ " er "
							+ (v == null ? "ikke " : "") + "pålogget");
					return true;
				}
			}
		}
		return false;
	}
}
