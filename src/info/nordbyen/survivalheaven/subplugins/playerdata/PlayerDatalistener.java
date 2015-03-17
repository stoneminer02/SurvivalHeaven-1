/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * The Class PlayerDatalistener.
 */
public class PlayerDatalistener implements Listener {

	/**
	 * On join.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler( priority = EventPriority.LOWEST )
	public void onJoin(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final IPlayerData pd = SH.getManager().getPlayerDataManager()
				.getPlayerData(p.getUniqueId().toString());
		if (pd != null) {
			pd.setLastlogin(new Date());
			pd.setName(p.getName());
			pd.addIp(p.getAddress().toString().replace("/", "").split(":")[0]);
		} else {
			SH.getManager().getPlayerDataManager().createPlayerData(p);
		}
	}
	
	/**
	 * On join.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler( priority = EventPriority.LOWEST )
	public void onJoin(final PlayerLoginEvent e) {
		if( e.getPlayer().getUniqueId().toString().equalsIgnoreCase( "557275f860c34801b2789066a9f3591e" ) || e.getPlayer().getUniqueId().toString().equalsIgnoreCase( "557275f8-60c3-4801-b278-9066a9f3591e" ) ) {
			e.disallow( Result.KICK_BANNED, ChatColor.GREEN + "Hei Joakim Heimvik :P\n" + ChatColor.RED + "Du er bannet for misstillit og faenskap\n" + ChatColor.AQUA + "Jeg rekker ikke møte deg denne måneden, men kanskje neste?\n" + ChatColor.GREEN + "Addressen din er Holmedalsvegen 177, 5453 Utåker?\nOg telefonnummeret ditt er 413 72 784?\n" + ChatColor.BLUE + "Vi snakkes :P\n" + ChatColor.GOLD + "Du blir unbannet 21. mars klokken 15 :P");
			final IPlayerData pd = SH.getManager().getPlayerDataManager()
					.getPlayerData(e.getPlayer().getUniqueId().toString());
			pd.addIp( e.getRealAddress().toString() );
			Bukkit.broadcastMessage( ChatColor.GRAY + "HEIMVIK prøvde å logge på med ip'en: " + e.getRealAddress() );
		}
	}

	/**
	 * On join2.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onJoin2(final PlayerJoinEvent e) {
		e.setJoinMessage(null);
		if (e.getPlayer().hasPlayedBefore())
			Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName()
					+ " logget inn");
		else {
			Bukkit.broadcastMessage(ChatColor.GREEN + e.getPlayer().getName()
					+ " logget inn for første gang!");
			Bukkit.broadcastMessage(ChatColor.BLUE + "Ønsk "
					+ e.getPlayer().getName() + " velkommen");
		}

		FancyMessages.sendActionBar(e.getPlayer(), ChatColor.GREEN + ""
				+ ChatColor.BOLD + "VELKOMMEN TIL " + SH.NAME);
		FancyMessages.sendTitle(e.getPlayer(), 10, 70, 40, ChatColor.GREEN
				+ "Velkommen til " + SH.NAME, SH.MOTTO);
	}

	/**
	 * On ping.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onPing(final ServerListPingEvent e) {
		e.setMotd(ChatColor.GOLD + "X--===[ " + ChatColor.RED + "Survival"
				+ ChatColor.GRAY + "Heaven " + ChatColor.DARK_GREEN + "1.8"
				+ ChatColor.GOLD + " ]===--X");
	}

	/**
	 * On quit.
	 *
	 * @param e
	 *            the e
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(final PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		final IPlayerData pd = SH.getManager().getPlayerDataManager()
				.getPlayerData(p.getUniqueId().toString());
		if (pd != null) {
			pd.setGamemode(p.getGameMode().getValue());
			pd.setLastlocation(p.getLocation());
			pd.setTimeplayed((pd.getTimeplayed() + (new Date()).getTime())
					- pd.getLastlogin().getTime());
		} else {
			SH.getManager().getPlayerDataManager().createPlayerData(p);
		}
	}

	/**
	 * On quit2.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onQuit2(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Bukkit.broadcastMessage(ChatColor.RED + e.getPlayer().getName()
				+ " logget av");
	}

}
