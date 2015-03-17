/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.rankmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class RankManagerCommand.
 */
public class RankManagerCommand implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String arg, final String[] args) {
		SH.getManager().getRankManager().updateNames();
		IRankManager rm = SH.getManager().getRankManager();
		if (sender instanceof Player) {
			Player p = (Player) sender;
			RankType rank = rm.getRank(p.getUniqueId().toString());
			if (rank != RankType.ADMINISTRATOR) {
				p.sendMessage(ChatColor.RED
						+ "Du har ikke høy nok rank til dette");
				return true;
			}
		}

		if (args.length == 2) {
			IPlayerData pd = SH.getManager().getPlayerDataManager()
					.getPlayerDataFromName(args[0]);
			if (pd == null) {
				sender.sendMessage(ChatColor.RED + "Finner ikke spilleren "
						+ args[0]);
				return true;
			}
			RankType rt = RankType.getRankFromName(args[1]);
			if (rt == null) {
				sender.sendMessage(ChatColor.RED + "Finner ikke ranken "
						+ args[1]);
				return true;
			}
			pd.setRank(rt.getId());
			sender.sendMessage(ChatColor.GREEN + args[0] + " ble satt til "
					+ rt.getName());
			Player r = Bukkit.getPlayer(args[0]);
			if (r != null)
				r.sendMessage(ChatColor.GREEN + "Du ble satt til "
						+ rt.getName() + " av " + sender.getName());
			SH.getManager().getRankManager().updateNames();
		} else {
			sender.sendMessage(ChatColor.RED
					+ "Feil syntax! /rank <spiller> <BANNED|BRUKER|MOD|ADMIN>");
			return true;
		}

		return true;
	}
}
