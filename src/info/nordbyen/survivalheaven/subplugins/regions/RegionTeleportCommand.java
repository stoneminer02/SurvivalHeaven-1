/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class RegionTeleportCommand.
 */
public class RegionTeleportCommand extends AbstractCommand {

	/**
	 * Instantiates a new region teleport command.
	 */
	public RegionTeleportCommand() {
		super("nord", "/<command>",
				"Kommandoer for å teleportere til utpostene", Arrays
						.asList(new String[] { "sør", "nord", "øst", "vest",
								"spawn" }));
		register();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.command.AbstractCommand#onCommand(org.
	 * bukkit.command .CommandSender, org.bukkit.command.Command,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "Bare in-game spillere kan teleportere!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("sør")) {
			p.sendMessage(ChatColor.GREEN + "Teleporterer til sør");
			p.teleport(new Location(Bukkit.getWorld("NyVerden"), 145, 77, 6234));
		} else if (label.equalsIgnoreCase("nord")) {
			p.sendMessage(ChatColor.GREEN + "Teleporterer til nord");
			p.teleport(new Location(Bukkit.getWorld("NyVerden"), -232, 64,
					-6071));
		} else if (label.equalsIgnoreCase("øst")) {
			p.sendMessage(ChatColor.GREEN + "Teleporterer til øst");
			p.teleport(new Location(Bukkit.getWorld("NyVerden"), 6269.5, 65.5,
					780.5, 180, 0));
		} else if (label.equalsIgnoreCase("vest")) {
			p.sendMessage(ChatColor.GREEN + "Teleporterer til vest");
			p.teleport(new Location(Bukkit.getWorld("NyVerden"), -5774, 73, 95));
		} else if (label.equalsIgnoreCase("spawn")) {
			p.sendMessage(ChatColor.GREEN + "Teleporterer til spawn");
			p.teleport(new Location(Bukkit.getWorld("NyVerden"), 104.5, 65.5,
					161.5, 270, 0));
		}
		return true;
	}
}
