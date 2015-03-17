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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * The listener interface for receiving rankManager events. The class that is
 * interested in processing a rankManager event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addRankManagerListener<code> method. When
 * the rankManager event occurs, that object's appropriate
 * method is invoked.
 *
 * @see RankManagerEvent
 */
public class RankManagerListener implements Listener {

	/**
	 * On chat.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onChat(final AsyncPlayerChatEvent e) {
		SH.getManager().getRankManager().updateNames();
		if (e.isCancelled())
			return;
		if (e.getMessage() == null)
			return;
		final Player p = e.getPlayer();
		e.setCancelled(true);
		for (final Player o : Bukkit.getOnlinePlayers()) {
			o.sendMessage(p.getDisplayName() + ChatColor.RESET + ": "
					+ ChatColor.GRAY + e.getMessage());
		}
		Bukkit.getConsoleSender().sendMessage(
				p.getDisplayName() + ChatColor.RESET + ": " + ChatColor.GRAY
						+ e.getMessage());
	}

	/**
	 * On join.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		SH.getManager().getRankManager().updateNames();
	}
}
