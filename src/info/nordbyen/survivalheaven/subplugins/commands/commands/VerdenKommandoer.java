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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class VerdenKommandoer.
 */
public class VerdenKommandoer implements CommandExecutor {

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
			final Player p = (Player) Sender;
			if (command.getName().equalsIgnoreCase("normal")) {
				p.teleport((Bukkit.getWorld("world").getSpawnLocation()));
			}
			if (command.getName().equalsIgnoreCase("pvp")) {
				p.teleport(Bukkit.getWorld("pvp").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("theend")) {
				if (p.getWorld().getName().equalsIgnoreCase("PvP")) {
					p.teleport(Bukkit.getWorld("theend_pvp").getSpawnLocation());
				} else {
					p.teleport(Bukkit.getWorld("world_the_end")
							.getSpawnLocation());
				}
			}
			if (command.getName().equalsIgnoreCase("nether")) {
				if (p.getWorld().getName() != "PvP") {
					p.teleport(Bukkit.getWorld("world_nether")
							.getSpawnLocation());
				} else {
					p.teleport(Bukkit.getWorld("pvp_nether").getSpawnLocation());
				}
			}
			if (command.getName().equalsIgnoreCase("kreativ")) {
				p.teleport(Bukkit.getWorld("Kreativ").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("pvpt")) {
				p.teleport(Bukkit.getWorld("PvPTeams").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("old")) {
				p.teleport(Bukkit.getWorld("GodeGamleNormal")
						.getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("pve")) {
				p.teleport(Bukkit.getWorld("PvE").getSpawnLocation());
			}
		}
		return true;
	}
}
