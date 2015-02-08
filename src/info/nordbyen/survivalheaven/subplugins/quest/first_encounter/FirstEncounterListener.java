/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
