/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.groupmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class FriendManagerCommand.
 */
public class FriendManagerCommand implements CommandExecutor {

	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String arg, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "En konsoll kan ikke ha venner .-.");
			return true;
		}

		Player p = (Player) sender;
		IPlayerData pd = SH.getManager().getPlayerDataManager()
				.getPlayerData(p.getUniqueId().toString());
		FriendManager fm = SH.getManager().getFriendManager();

		if (args.length == 0) {
			p.sendMessage(ChatColor.GREEN
					+ "************************************************");
			p.sendMessage(ChatColor.BLUE + "Kommandoer til vennesystemet");
			p.sendMessage(ChatColor.GRAY
					+ "En spiller som er venn med deg kan åpne dine kister og knuse dine blokker. Pass på hvem du legger til som venn!");
			p.sendMessage(ChatColor.GREEN
					+ "------------------------------------------------");
			p.sendMessage(ChatColor.YELLOW + "/venn " + ChatColor.GRAY
					+ "Lister kommandoer");
			p.sendMessage(ChatColor.YELLOW + "/venn list " + ChatColor.GRAY
					+ "Lister alle dine venner");
			p.sendMessage(ChatColor.YELLOW + "/venn spør <spiller> "
					+ ChatColor.GRAY + "Sender en venneforespørsel");
			p.sendMessage(ChatColor.YELLOW + "/venn godta <spiller> "
					+ ChatColor.GRAY + "Godtar en venneforespørsel");
			p.sendMessage(ChatColor.YELLOW + "/venn avslå <spiller> "
					+ ChatColor.GRAY + "Avslår en venneforespørsel");
			p.sendMessage(ChatColor.YELLOW + "/venn forespørsler "
					+ ChatColor.GRAY + "Lister alle dine venneforespørsler");
			p.sendMessage(ChatColor.RED + "/venn fjern <spiller "
					+ ChatColor.GRAY + "Sletter en spiller fra vennelisten din");
			p.sendMessage(ChatColor.GREEN
					+ "************************************************");
			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {
			if (args.length != 1) {
				p.sendMessage(ChatColor.RED + "Feil syntax: /venn list");
				return true;
			}
			ArrayList<IPlayerData> list = fm.getFriendsWith(pd);
			if (list == null || list.size() == 0) {
				p.sendMessage(ChatColor.GREEN
						+ "Du har for øyeblikket ingen spiller på vennelisten. Legg til venner med /venn spør <spiller>");
				return true;
			}
			StringBuilder venner = new StringBuilder(ChatColor.GREEN
					+ "Følgende personer er venn med deg:\n" + ChatColor.AQUA);
			for (IPlayerData venn : list) {
				venner.append(venn.getName() + ", ");
			}
			String l = venner.substring(0, venner.length() - 2);
			p.sendMessage(l);
			return true;
		} else if (args[0].equalsIgnoreCase("spør")) {
			if (args.length != 2) {
				p.sendMessage(ChatColor.RED
						+ "Feil syntax: /venn spør <spiller>");
				return true;
			}
			Player r = Bukkit.getPlayer(args[1]);
			if (r == null) {
				p.sendMessage(ChatColor.RED + "Fant ikke spilleren " + args[1]);
				return true;
			}
			if (r == p) {
				p.sendMessage(ChatColor.RED
						+ "Du kan ikke legg til deg selv som venn!");
				return true;
			}
			ArrayList<IPlayerData> list = fm.getFriendsWith(pd);
			for (IPlayerData fpd : list) {
				if (fpd.getUUID().equals(r.getUniqueId().toString())) {
					p.sendMessage(ChatColor.RED + "Du er allerede venn med "
							+ r.getName());
					return true;
				}
			}
			ArrayList<String> reql = fm.getFriendrequestsTo(args[1]);

			if (reql != null && reql.contains(p.getName())) {
				p.sendMessage(ChatColor.RED
						+ "Du har allerede sendt en forespørsel til denne spilleren");
				return true;
			}
			p.sendMessage(ChatColor.GREEN
					+ "Du sendte en venneforespørsel til " + r.getName());

			r.sendMessage(ChatColor.GREEN
					+ "************************************************");
			r.sendMessage(ChatColor.BLUE
					+ "Du har mottat en venneforespørsel fra " + p.getName());
			r.sendMessage(ChatColor.GRAY
					+ "En spiller som er venn med deg kan åpne dine kister og knuse dine blokker. Pass på hvem du legger til som venn!");
			r.sendMessage(ChatColor.GREEN
					+ "------------------------------------------------");
			r.sendMessage(ChatColor.YELLOW + "/venn godta " + p.getName()
					+ ChatColor.GRAY + " For å godta forespørselen");
			r.sendMessage(ChatColor.YELLOW + "/venn avslå " + p.getName()
					+ ChatColor.GRAY + " For å avslå forespørselen");
			r.sendMessage(ChatColor.GREEN
					+ "************************************************");

			ArrayList<String> req = fm.getFriendrequestsTo(args[1]);
			if (req == null) {
				fm.setFriendrequestsTo(args[1], new ArrayList<String>());
				req = fm.getFriendrequestsTo(args[1]);
			}
			req.add(p.getName());
			return true;
		} else if (args[0].equalsIgnoreCase("godta")) {
			if (args.length != 2) {
				p.sendMessage(ChatColor.RED
						+ "Feil syntax: /venn godta <spiller>");
				return true;
			}
			if (!fm.getFriendrequestsTo(p.getName()).contains(args[1])) {
				p.sendMessage(ChatColor.RED + "Du har ingen forespørsler fra "
						+ args[1]);
				return true;
			}
			Player r = Bukkit.getPlayer(args[1]);
			if (r == null) {
				p.sendMessage(ChatColor.RED + args[0]
						+ " må være online for å gjøre dette");
				return true;
			}
			try {
				fm.setFriends(p.getUniqueId().toString(), r.getUniqueId()
						.toString());
			} catch (SQLException e) {
				p.sendMessage(ChatColor.RED
						+ "Noe gikk galt... Si ifra til staben eller send en melding til alex.l0lkj på skype");
				return true;
			}
			fm.getFriendrequestsTo(p.getName()).remove(r.getName());

			p.sendMessage(ChatColor.GREEN + "Du godtok venneforespørselen fra "
					+ args[0]);
			p.sendMessage(ChatColor.GRAY
					+ "Dere er nå venner og kan ødelegge hverandres blokker");

			r.sendMessage(ChatColor.GREEN + p.getName()
					+ " godtok venneforespørselen din");
			r.sendMessage(ChatColor.GRAY
					+ "Dere er nå venner og kan ødelegge hverandres blokker");
			return true;
		} else if (args[0].equalsIgnoreCase("fjern")) {
			IPlayerData fpd = SH.getManager().getPlayerDataManager()
					.getPlayerDataFromName(args[1]);
			if (!fm.isFriends(pd, fpd)) {
				p.sendMessage(ChatColor.RED + "Dere er ikke venner");
				return true;
			}
			try {
				fm.removeFriendship(pd.getUUID(), fpd.getUUID());
			} catch (SQLException e) {
				p.sendMessage(ChatColor.RED
						+ "Noe gikk galt... Si ifra til staben eller send en melding til alex.l0lkj på skype");
				return true;
			}
			p.sendMessage(ChatColor.GREEN + "Du fjernet " + args[1]
					+ " fra dine venner");
			Player r = Bukkit.getPlayer(args[1]);
			if (r != null)
				r.sendMessage(ChatColor.RED + p.getName()
						+ " fjernet deg fra sine venner!");
			return true;
		} else if (args[0].equalsIgnoreCase("avslå")) {
			if (args.length != 2) {
				p.sendMessage(ChatColor.RED
						+ "Feil syntax: /venn avslå <spiller>");
				return true;
			}
			ArrayList<String> requests = fm.getFriendrequestsTo(p.getName());
			if (requests == null) {
				fm.setFriendrequestsTo(p.getName(), new ArrayList<String>());
				requests = fm.getFriendrequestsTo(p.getName());
			}
			if (!requests.contains(args[1])) {
				p.sendMessage(ChatColor.RED + "Du har ingen forespørsler fra "
						+ args[1]);
				return true;
			}
			requests.remove(args[1]);
			p.sendMessage(ChatColor.GREEN + "Avslå forespørselen fra "
					+ args[1]);
			Player r = Bukkit.getPlayer(args[1]);
			if (r != null)
				r.sendMessage(ChatColor.RED
						+ "Din venneforespørsel ble avslått av " + p.getName());
			return true;
		} else if (args[0].equalsIgnoreCase("forespørsler")) {
			ArrayList<String> requests = fm.getFriendrequestsTo(p.getName());
			if (requests == null) {
				fm.setFriendrequestsTo(p.getName(), new ArrayList<String>());
				requests = fm.getFriendrequestsTo(p.getName());
			}
			StringBuilder sb = new StringBuilder(ChatColor.GREEN
					+ "Alle spillere som ønsker å legge deg til som venn:\n"
					+ ChatColor.AQUA);
			for (String re : requests) {
				sb.append(re + ", ");
			}
			p.sendMessage(sb.substring(0, sb.length() - 2));
			return true;
		}
		return true;
	}
}
