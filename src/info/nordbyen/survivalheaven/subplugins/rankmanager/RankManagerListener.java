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
