/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class SS.
 */
public class SS implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String CommandLabel, final String[] args) {
		RankType rank = SH.getManager().getRankManager()
				.getRank(((Player) sender).getUniqueId().toString());
		if (rank != RankType.ADMINISTRATOR && rank != RankType.MODERATOR) {
			sender.sendMessage(ChatColor.RED
					+ "Du har ikke tilgang til denne kommandoen");
		}
		if (command.getName().equalsIgnoreCase("ss")) {
			if (args.length > 0) {
				final StringBuffer msg = new StringBuffer();
				for (final String arg : args) {
					msg.append(arg + " ");
				}
				for (final Player b : Bukkit.getOnlinePlayers()) {
					if (b.hasPermission("sh.ss.motta")) {
						b.sendMessage(ChatColor.GOLD + "[Stabsamtale] "
								+ ChatColor.RED + ((Player) sender).getName()
								+ ": " + ChatColor.GRAY + msg.toString());
						if (!(sender instanceof Player)) {
							System.out.println(ChatColor.GOLD
									+ "[Stabsamtale] "
									+ ((Player) sender).getDisplayName() + ": "
									+ ChatColor.GRAY + msg.toString());
						}
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Feil: Bruk /ss <melding>");
			}
		}
		return false;
	}
}
