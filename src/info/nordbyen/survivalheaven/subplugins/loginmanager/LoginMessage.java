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

package info.nordbyen.survivalheaven.subplugins.loginmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * The listener to the LoginMessage plugin
 * 
 * @author l0lkj
 */
class LoginListener implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Bukkit.broadcastMessage( ChatColor.GREEN + e.getPlayer().getName() + " logget inn" );
		FancyMessages.sendActionBar(e.getPlayer(), ChatColor.GREEN + ""
				+ ChatColor.BOLD + "VELKOMMEN TIL " + SH.NAME);
		FancyMessages.sendTitle(e.getPlayer(), 10, 70, 40, ChatColor.GREEN
				+ "Velkommen til " + SH.NAME, SH.MOTTO);
	}

	@EventHandler
	public void onPing(final ServerListPingEvent e) {
		e.setMotd(ChatColor.GOLD + "X--===[ " + ChatColor.RED + "Survival"
				+ ChatColor.GRAY + "Heaven " + ChatColor.DARK_GREEN + "1.8"
				+ ChatColor.GOLD + " ]===--X");
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Bukkit.broadcastMessage(ChatColor.RED + e.getPlayer().getName() + " logget av");
	}
}

/**
 * The Class LoginMessage.
 */
public class LoginMessage extends SubPlugin {

	/**
	 * Instantiates a new login message.
	 * 
	 * @param name
	 *            the name
	 */
	public LoginMessage(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		Bukkit.getPluginManager().registerEvents(new LoginListener(),
				getPlugin());
	}
}
