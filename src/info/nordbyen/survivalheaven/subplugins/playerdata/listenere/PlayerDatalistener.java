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

package info.nordbyen.survivalheaven.subplugins.playerdata.listenere;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
	@EventHandler
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
}
