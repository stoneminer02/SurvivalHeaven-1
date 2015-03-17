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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class Tid.
 */
public class Tid extends JavaPlugin implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String commandLabel, final String[] args) {
		final Player p = (Player) sender;
		final World w = p.getWorld();
		if (p.hasPermission("cd.tid")) {
			if (command.getName().equalsIgnoreCase("tid")) {
				if (args.length == 0) {
					p.sendMessage(ChatColor.GOLD + "Tidssystemet:");
					p.sendMessage(ChatColor.BLUE + "/tid dag - "
							+ ChatColor.GOLD + "stiller din tid til dag.");
					p.sendMessage(ChatColor.BLUE + "/tid natt - "
							+ ChatColor.GOLD + "stiller din tid til natt.");
					p.sendMessage(ChatColor.BLUE + "/tid morgen - "
							+ ChatColor.GOLD + "stiller din tid til morgen.");
					p.sendMessage(ChatColor.BLUE + "/tid kveld - "
							+ ChatColor.GOLD + "stiller din tid til kveld.");
					p.sendMessage(ChatColor.BLUE + "/tid reset - "
							+ ChatColor.GOLD + "stiller din tid til servertid.");
					p.sendMessage(ChatColor.BLUE + "/tid info - "
							+ ChatColor.GOLD
							+ "gir deg litt info om tiden din.");
					return true;
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("dag")) {
						p.sendMessage(ChatColor.GOLD + "[Alarm] "
								+ ChatColor.RED
								+ "Din tid har blitt endret til dag.");
						p.getWorld().setTime(6000);
						w.setWeatherDuration(0);
						return true;
					} else if (args[0].equalsIgnoreCase("natt")) {
						p.sendMessage(ChatColor.GOLD + "[Alarm] "
								+ ChatColor.RED
								+ "Din tid har blitt endret til natt.");
						p.getWorld().setTime(18000);
						return true;
					} else if (args[0].equalsIgnoreCase("morgen")) {
						p.sendMessage(ChatColor.GOLD + "[Alarm] "
								+ ChatColor.RED
								+ "Din tid har blitt endret til morgen.");
						p.getWorld().setTime(0);
						return true;
					} else if (args[0].equalsIgnoreCase("kveld")) {
						p.sendMessage(ChatColor.GOLD + "[Alarm] "
								+ ChatColor.RED
								+ "Din tid har blitt endret til kveld.");
						p.getWorld().setTime(12000);
						return true;
					} else if (args[0].equalsIgnoreCase("info")) {
						p.sendMessage(ChatColor.GOLD + "Din tid er satt på "
								+ ChatColor.BLUE + p.getPlayerTime()
								+ ChatColor.GOLD + ".");
						p.sendMessage(ChatColor.GOLD
								+ "Forskjellen mellom serverertiden og spillertiden er "
								+ ChatColor.BLUE + p.getPlayerTimeOffset()
								+ ChatColor.GOLD + ".");
						p.sendMessage(ChatColor.GOLD + "Du er i verden:"
								+ p.getWorld().getName() + ChatColor.GOLD + ".");
						return true;
					} else if (args[0].equalsIgnoreCase("reset")) {
						p.sendMessage(ChatColor.GOLD + "[Alarm] "
								+ ChatColor.RED
								+ "Funker bare på egen spillertid.");
						return true;
					} else {
						p.sendMessage(ChatColor.GOLD
								+ "[Alarm] "
								+ ChatColor.RED
								+ "Du har skrevet feil, skriv /tid for mere info.");
						return true;
					}
				} else {
					p.sendMessage(ChatColor.GOLD + "[Alarm] " + ChatColor.RED
							+ "Denne kommandoen er deaktivert i denne worlden.");
				}
			}
		}
		return true;
	}
}
