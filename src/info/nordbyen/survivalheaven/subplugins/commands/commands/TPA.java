/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class TPA.
 */
public class TPA implements CommandExecutor {

	/** The pl. */
	JavaPlugin pl = info.nordbyen.survivalheaven.SH.getPlugin();
	
	/** The tpa. */
	public static HashMap<String, String> tpa = new HashMap<String, String>();

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
		if (command.getName().equalsIgnoreCase("tpa")) {
			if (args.length == 1) {
				final Player p = Bukkit.getPlayer(args[0]);
				if (p != null) {
					tpa.put(p.getName(), Sender.getName());
					Sender.sendMessage(ChatColor.GREEN + "Forespørsel sendt!");
					p.sendMessage(ChatColor.DARK_GREEN + Sender.getName()
							+ " vil teleportere til deg.");
					p.sendMessage(ChatColor.GREEN
							+ "Sriv /tpaccept for å godta");
					pl.getServer().getScheduler()
							.scheduleSyncDelayedTask(pl, new Runnable() {

								@Override
								public void run() {
									tpa.remove(Sender.getName());
								}
							}, 20 * 30);
				}
			} else {
				Sender.sendMessage(ChatColor.RED + "Bruk /tpa <spiller>");
			}
		}
		return false;
	}
}
