/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.subplugins.quest.Godta_Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * The listener interface for receiving firstEncounter events. The class that is
 * interested in processing a firstEncounter event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addFirstEncounterListener<code> method. When
 * the firstEncounter event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FirstEncounterEvent
 */
public class FirstEncounterListener implements Listener {

	/**
	 * On move.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (p.getName().equals("l0lkj")) {
			if (FirstEncounter.isInside(e.getTo(),
					FirstEncounterConfig.getDoor1_1(),
					FirstEncounterConfig.getDoor1_2())) {
				e.setCancelled(true);
				if (Godta_Command.players.containsKey(p.getUniqueId()
						.toString()))
					return;
				p.sendMessage(ChatColor.RED
						+ "Er du sikker på at du tør å gå inn her? skriv /godta for å gå inn.");
				Godta_Command.players.put(p.getUniqueId().toString(),
						new AcceptHandler());
			}
		}
	}
}
