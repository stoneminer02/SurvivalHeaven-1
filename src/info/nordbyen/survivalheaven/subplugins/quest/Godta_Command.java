/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Godta_Command.
 */
public class Godta_Command extends AbstractCommand {

	/**
	 * Clear command.
	 */
	public static void clearCommand() {
		instance = null;
	}

	/**
	 * Inits the command.
	 */
	public static void initCommand() {
		if (instance == null) {
			instance = new Godta_Command();
		}
	}

	/** The Constant players. */
	public static final HashMap<String, Acceptable> players = new HashMap<String, Acceptable>();

	/** The instance. */
	private static Godta_Command instance = null;

	/**
	 * Instantiates a new godta_ command.
	 */
	private Godta_Command() {
		super("godta", "/<command>", "En kommando for bruk i quester");
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
		sender.sendMessage(ChatColor.RED + "HAHA");
		if (!isAuthorized(sender, "sh.godta")) {
			sender.sendMessage(ChatColor.RED
					+ "Du har ikke tillatelse til dette");
		}
		if (!isPlayer(sender)) {
			sender.sendMessage(ChatColor.RED
					+ "Du må være en spiller ( Sry Thomas :P )");
		}
		final Player p = (Player) sender;
		if (players.containsKey(p.getUniqueId().toString())) {
			players.get(p.getUniqueId().toString()).executedAccept(
					p.getUniqueId().toString());
			players.remove(p.getUniqueId().toString());
		} else {
			p.sendMessage(ChatColor.RED + "Du har ingenting å godta");
		}
		return true;
	}
}
