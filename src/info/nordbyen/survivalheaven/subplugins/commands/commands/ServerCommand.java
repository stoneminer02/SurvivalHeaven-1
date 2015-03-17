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
import info.nordbyen.survivalheaven.api.command.AbstractCommand;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager;
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class ServerCommand.
 */
public class ServerCommand extends AbstractCommand {

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
			instance = new ServerCommand();
		}
	}

	/** The instance. */
	static ServerCommand instance = null;

	/** The feil syntax. */
	private final String FEIL_SYNTAX = ChatColor.RED
			+ "Feil syntax. Skriv /sh for en liste med kommandoer";

	/**
	 * Instantiates a new server command.
	 */
	private ServerCommand() {
		super("sh", "/<command>", "En gruppe med administrative kommandoer",
				Arrays.asList(new String[] { "s" }));
		register();
	}

	/**
	 * Ban.
	 *
	 * @param sender
	 *            the sender
	 * @param command
	 *            the command
	 * @param label
	 *            the label
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	private boolean ban(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (args.length < 3) {
			sender.sendMessage(FEIL_SYNTAX);
			return true;
		}
		final IPlayerData pd = findPlayer(sender, args[1]);
		if (pd == null)
			return true;
		int days = -1;
		try {
			days = Integer.parseInt(args[2]);
		} catch (final Exception e) {
			sender.sendMessage(ChatColor.RED + args[2] + " må være et tall");
			return true;
		}
		if (days < 1) {
			sender.sendMessage(ChatColor.RED + "Feil med tallet " + days);
			return true;
		}
		String reason = "The banhammer has spoken!";
		if (args.length >= 4) {
			final StringBuffer melding = new StringBuffer();
			for (int i = 3; i < args.length; i++) {
				melding.append(args[i] + " ");
			}
			reason = melding.toString();
		}
		// pd.setBanned( true );
		// pd.setBannedFrom( new Date() );
		// pd.setBannedTo( new Date( System.currentTimeMillis() + ( 86_400_000 *
		// days ) ) );
		// pd.setBanReason( reason ); // TODO
		Bukkit.broadcastMessage(ChatColor.GRAY + "Spiller " + ChatColor.YELLOW
				+ pd.getName() + ChatColor.GRAY + " ble bannet av en operator");
		Bukkit.broadcastMessage(ChatColor.GRAY + "    -> Grunn: "
				+ ChatColor.YELLOW + reason);
		Bukkit.broadcastMessage(ChatColor.GRAY + "    -> Tid "
				+ ChatColor.YELLOW + days);
		return true;
	}

	/**
	 * Find player.
	 *
	 * @param sender
	 *            the sender
	 * @param name
	 *            the name
	 * @return the i player data
	 */
	private IPlayerData findPlayer(final CommandSender sender, final String name) {
		final Player r = Bukkit.getPlayer(name);
		IPlayerData pd;
		if (r != null) {
			pd = SH.getManager().getPlayerDataManager()
					.getPlayerData(r.getUniqueId().toString());
		} else {
			sender.sendMessage(ChatColor.GRAY + "Spilleren " + name
					+ " er ikke online, søker internt...");
			pd = SH.getManager().getPlayerDataManager()
					.getPlayerDataFromName(name);
			if (pd == null) {
				sender.sendMessage(ChatColor.RED + "Fant ikke spiller " + name);
				return null;
			}
		}
		if (pd == null) {
			sender.sendMessage(ChatColor.RED
					+ "Fant spilleren online men ikke internt. Kontakt l0lkj om dette!");
			return null;
		}
		return pd;
	}

	/**
	 * Note.
	 *
	 * @param sender
	 *            the sender
	 * @param command
	 *            the command
	 * @param label
	 *            the label
	 * @param args
	 *            the args
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	private boolean note(final CommandSender sender, final Command command,
			final String label, final String[] args) throws Exception {
		if (args.length <= 3) {
			sender.sendMessage(FEIL_SYNTAX);
			return true;
		}
		if (args[1].equalsIgnoreCase("add")) {
			if (args.length <= 4) {
				sender.sendMessage(FEIL_SYNTAX);
				return true;
			}
			final IPlayerData pd = findPlayer(sender, args[2]);
			if (pd == null)
				return true;
			final StringBuilder sb = new StringBuilder();
			for (int i = 3; i < args.length; i++) {
				sb.append(args[i] + " ");
			}
			sb.substring(0, sb.length());
			final String s = sb.toString();
			IPlayerData spd;
			if (sender instanceof Player) {
				spd = SH.getManager()
						.getPlayerDataManager()
						.getPlayerData(
								((Player) sender).getUniqueId().toString());
			} else {
				spd = null;
			}
			final INoteManager nm = SH.getManager().getNoteManager();
			nm.addNote(new Date(), pd, spd, s);
			sender.sendMessage(ChatColor.GREEN
					+ "La til et nytt notat: spiller: " + args[2]
					+ " melding: " + s);
			return true;
		} else if (args[1].equalsIgnoreCase("see")) {
			if (args.length != 3) {
				sender.sendMessage(FEIL_SYNTAX + "1");
				return true;
			}
			final IPlayerData pd = findPlayer(sender, args[2]);
			if (pd == null)
				return true;
			final INoteManager nm = SH.getManager().getNoteManager();
			final List<INote> notes = nm.getNotesFromPlayer(pd);
			sender.sendMessage(ChatColor.BLUE
					+ "************************************************");
			sender.sendMessage(ChatColor.AQUA + "Notater på " + args[2]);
			sender.sendMessage(ChatColor.BLUE
					+ "------------------------------------------------");
			for (final INote note : notes) {
				final int id = note.getId();
				final IPlayerData setter = note.getSetter();
				String settername = "UKJENT";
				if (setter != null) {
					settername = setter.getName();
				}
				sender.sendMessage(ChatColor.YELLOW + "ID:" + id + " Av:"
						+ settername + " Melding:\"" + note.getMessage() + "\"");
				sender.sendMessage(ChatColor.GRAY + "-");
			}
			sender.sendMessage(ChatColor.BLUE
					+ "************************************************");
			return true;
		} else if (args[1].equalsIgnoreCase("del"))
			return true;
		return true;
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
		if (!isAuthorized(sender, "sh.server")) {
			sender.sendMessage(ChatColor.RED
					+ "Du har ikke tillatelse til dette");
			return true;
		}
		if (!isPlayer(sender)) {
			sender.sendMessage(ChatColor.RED
					+ "Du må være en spiller ( Sry Thomas :P )");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GOLD
					+ "**********************************");
			sender.sendMessage(ChatColor.BLUE + "Syntax for /sh");
			sender.sendMessage(ChatColor.GOLD
					+ "----------------------------------");
			sender.sendMessage(ChatColor.YELLOW
					+ "/sh ban <spiller> <dager> <grunn>" + " "
					+ ChatColor.GRAY + "Banner en spiller");
			sender.sendMessage(ChatColor.YELLOW
					+ "/sh permban <spiller> < grunn >" + " " + ChatColor.GRAY
					+ "Banner en spiller");
			sender.sendMessage(ChatColor.YELLOW
					+ "/sh warn add <spiller> <1-3> <grunn>" + " "
					+ ChatColor.GRAY + "Gir en advarsel");
			sender.sendMessage(ChatColor.YELLOW + "/sh warn see <spiller>"
					+ " " + ChatColor.GRAY + "Ser advarsler");
			sender.sendMessage(ChatColor.YELLOW + "/sh warn del <id>" + " "
					+ ChatColor.GRAY + "Fjerner en advarsel");
			sender.sendMessage(ChatColor.YELLOW
					+ "/sh note add <spiller> <grunn>" + ChatColor.GRAY + " "
					+ "Skriver et notat");
			sender.sendMessage(ChatColor.YELLOW + "/sh note see <spiller>"
					+ " " + ChatColor.GRAY + "Ser notater");
			sender.sendMessage(ChatColor.YELLOW + "/sh note del <id>" + " "
					+ ChatColor.GRAY + "Fjerner et notat");
			sender.sendMessage(ChatColor.GOLD
					+ "**********************************");
			return true;
		} else if (args[0].equalsIgnoreCase("ban")
				|| args[0].equalsIgnoreCase("tempban"))
			return ban(sender, command, label, args);
		else if (args[0].equalsIgnoreCase("permban"))
			return permban(sender, command, label, args);
		else if (args[0].equalsIgnoreCase("warn"))
			return warn(sender, command, label, args);
		else if (args[0].equalsIgnoreCase("note")) {
			try {
				return note(sender, command, label, args);
			} catch (final Exception e) {
				sender.sendMessage(ChatColor.RED
						+ "Noe gikk galt! Si ifra til l0lkj");
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(FEIL_SYNTAX + "0");
		}
		return true;
	}

	/**
	 * Permban.
	 *
	 * @param sender
	 *            the sender
	 * @param command
	 *            the command
	 * @param label
	 *            the label
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	private boolean permban(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		sender.sendMessage(ChatColor.RED + "NOT YET IMPLEMENTED");
		return true;
	}

	/**
	 * Warn.
	 *
	 * @param sender
	 *            the sender
	 * @param command
	 *            the command
	 * @param label
	 *            the label
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	@SuppressWarnings("unused")
	private boolean warn(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (args.length < 3) {
			sender.sendMessage(FEIL_SYNTAX);
			return true;
		}
		final IPlayerData pd = findPlayer(sender, args[3]);
		if (pd == null)
			return true;
		if (args[1].equalsIgnoreCase("add")) {
			if (args.length <= 5) {
				sender.sendMessage(FEIL_SYNTAX);
				return true;
			}
			int level = -1;
			try {
				level = Integer.parseInt(args[4]);
			} catch (final Exception e) {
				sender.sendMessage(ChatColor.RED + "Syntax feil: " + args[3]
						+ " er ikke et tall!");
			}
			return true;
		} else if (args[1].equalsIgnoreCase("see")) {
			if (args.length == 3) {
			}
			return true;
		} else if (args[1].equalsIgnoreCase("del"))
			return true;
		return true;
	}
}
