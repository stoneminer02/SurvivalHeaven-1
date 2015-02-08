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

package info.nordbyen.survivalheaven.subplugins.old_stuff.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class K.
 */
public class K implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (p.hasPermission("sh.k")) {
				if (command.getName().equalsIgnoreCase("k")) {
					if (args.length > 1) {
						p.sendMessage(ChatColor.RED
								+ "Prøve /k eller /k <spiller>??");
					}
					if (args.length == 0) {
						if (p.getGameMode().equals(GameMode.CREATIVE)) {
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(ChatColor.GOLD
									+ "[Alarm] Du skiftet spillermodus til survival");
						} else if (p.getGameMode().equals(GameMode.SURVIVAL)) {
							p.setGameMode(GameMode.CREATIVE);
							p.sendMessage(ChatColor.GOLD
									+ "[Alarm] Du skiftet spillermodus til kreativ");
							Bukkit.broadcast(
									ChatColor.GOLD + "[StabAlarm] LAV: "
											+ ChatColor.WHITE + p.getName()
											+ " skrudde på kreativ", "sh.kick");
						}
					}
					if (args.length == 1) {
						final Player targetPlayer = p.getServer().getPlayer(
								args[0]);
						if (targetPlayer.getGameMode()
								.equals(GameMode.CREATIVE)) {
							targetPlayer.setGameMode(GameMode.SURVIVAL);
							targetPlayer
									.sendMessage(ChatColor.GOLD
											+ "[Alarm] En i stabben skiftet ditt spillermodus til survival");
						} else if (targetPlayer.getGameMode().equals(
								GameMode.SURVIVAL)) {
							targetPlayer.setGameMode(GameMode.CREATIVE);
							targetPlayer
									.sendMessage(ChatColor.GOLD
											+ "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
							Bukkit.broadcast(ChatColor.GOLD
									+ "[StabAlarm] MIDDELS: " + ChatColor.WHITE
									+ p.getName() + " skrudde på kreativ til "
									+ targetPlayer.getName(), "sh.kick");
						}
					}
				}
			}
		} else {
			if (args.length == 1) {
				final Player targetPlayer = Bukkit.getServer().getPlayer(
						args[0]);
				if (targetPlayer.getGameMode().equals(GameMode.CREATIVE)) {
					targetPlayer.setGameMode(GameMode.SURVIVAL);
					targetPlayer
							.sendMessage(ChatColor.GOLD
									+ "[Alarm] En i stabben skiftet ditt spillermodus til survival");
				} else if (targetPlayer.getGameMode().equals(GameMode.SURVIVAL)) {
					targetPlayer.setGameMode(GameMode.CREATIVE);
					targetPlayer
							.sendMessage(ChatColor.GOLD
									+ "[Alarm] En i stabben skiftet ditt spillermodus til kreativ");
				} else {
					Sender.sendMessage(ChatColor.RED + "Du er ikke en spiller");
				}
			}
		}
		return true;
	}
}
