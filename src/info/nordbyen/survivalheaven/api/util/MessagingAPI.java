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

package info.nordbyen.survivalheaven.api.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class MessagingAPI.
 */
public class MessagingAPI {

	/**
	 * Gets the messenger.
	 * 
	 * @return the messenger
	 */
	public static MessagingAPI getMessenger() {
		return messageAPI;
	}

	/** The message api. */
	private static MessagingAPI messageAPI = new MessagingAPI();

	/**
	 * Broadcast.
	 * 
	 * @param start
	 *            the start
	 * @param args
	 *            the args
	 */
	public void broadcast(final int start, final String[] args) {
		final String message = buildString(start, args);
		Bukkit.broadcastMessage(message);
	}

	/**
	 * Broadcast.
	 * 
	 * @param message
	 *            the message
	 */
	public void broadcast(final String message) {
		Bukkit.broadcastMessage(message);
	}

	/**
	 * Broadcast.
	 * 
	 * @param args
	 *            the args
	 */
	public void broadcast(final String[] args) {
		broadcast(0, args);
	}

	/**
	 * Builds the string.
	 * 
	 * @param start
	 *            the start
	 * @param args
	 *            the args
	 * @return the string
	 */
	public String buildString(final int start, final String[] args) {
		final StringBuilder str = new StringBuilder();
		for (int i = start; i < args.length; i++) {
			str.append(args[i] + " ");
		}
		final String message = str.toString();
		return message;
	}

	/**
	 * Builds the string.
	 * 
	 * @param args
	 *            the args
	 * @return the string
	 */
	public String buildString(final String[] args) {
		return buildString(0, args);
	}

	/**
	 * Deny.
	 * 
	 * @param sender
	 *            the sender
	 */
	public void deny(final CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You are not allowed to do this!");
	}

	/**
	 * Deny.
	 * 
	 * @param sender
	 *            the sender
	 * @param message
	 *            the message
	 */
	public void deny(final CommandSender sender, final String message) {
		sender.sendMessage(ChatColor.RED + message);
	}

	/**
	 * Deny.
	 * 
	 * @param player
	 *            the player
	 */
	public void deny(final Player player) {
		player.sendMessage(ChatColor.RED + "You are not allowed to do this!");
	}

	/**
	 * Deny.
	 * 
	 * @param player
	 *            the player
	 * @param message
	 *            the message
	 */
	public void deny(final Player player, final String message) {
		player.sendMessage(ChatColor.RED + message);
	}

	/**
	 * Info.
	 * 
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String info(final String message) {
		return ChatColor.GRAY + "" + ChatColor.ITALIC + message;
	}

	/**
	 * Mass send.
	 * 
	 * @param players
	 *            the players
	 * @param message
	 *            the message
	 */
	public void massSend(final Player[] players, final String message) {
		for (final Player player : players)
			if (player.isOnline()) {
				player.sendMessage(message);
			}
	}

	/**
	 * Mass send.
	 * 
	 * @param message
	 *            the message
	 */
	public void massSend(final String message) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	/**
	 * Mass send.
	 * 
	 * @param players
	 *            the players
	 * @param message
	 *            the message
	 */
	public void massSend(final String[] players, final String message) {
		for (final String target : players) {
			final Player player = Bukkit.getPlayer(target);
			if (player.isOnline()) {
				player.sendMessage(message);
			}
		}
	}

	/**
	 * No target.
	 * 
	 * @param sender
	 *            the sender
	 * @param name
	 *            the name
	 */
	public void noTarget(final CommandSender sender, final String name) {
		sender.sendMessage(ChatColor.GOLD + name + ChatColor.RED
				+ " was not found!");
	}

	/**
	 * No target.
	 * 
	 * @param player
	 *            the player
	 * @param name
	 *            the name
	 */
	public void noTarget(final Player player, final String name) {
		player.sendMessage(ChatColor.GOLD + name + ChatColor.RED
				+ " was not found!");
	}

	/**
	 * Send.
	 * 
	 * @param sender
	 *            the sender
	 * @param message
	 *            the message
	 */
	public void send(final CommandSender sender, final String message) {
		sender.sendMessage(message);
	}

	/**
	 * Send.
	 * 
	 * @param player
	 *            the player
	 * @param message
	 *            the message
	 */
	public void send(final Player player, final String message) {
		player.sendMessage(message);
	}

	/**
	 * Send.
	 * 
	 * @param name
	 *            the name
	 * @param message
	 *            the message
	 */
	public void send(final String name, final String message) {
		final Player player = Bukkit.getPlayer(name);
		if (player != null) {
			player.sendMessage(message);
		}
	}

	/**
	 * Severe.
	 * 
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String severe(final String message) {
		return ChatColor.RED + message;
	}

	/**
	 * Warn.
	 * 
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String warn(final String message) {
		return ChatColor.YELLOW + message;
	}
}
