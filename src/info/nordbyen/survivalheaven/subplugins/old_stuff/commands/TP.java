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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class TP.
 */
public class TP implements CommandExecutor {

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
			final Player targetPlayer = p.getServer().getPlayer(args[0]);
			if (p.hasPermission("sh.tp")) {
				if (command.getName().equalsIgnoreCase("tp")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "/tp <spiller>");
					} else if (args.length == 1) {
						final Location targetPlayerLocation = targetPlayer
								.getLocation();
						p.teleport(targetPlayerLocation);
						p.sendMessage(ChatColor.GOLD
								+ "[Alarm] Du ble teleportert til "
								+ targetPlayer.getName() + ".");
					} else if (args.length == 3) {
						final double x = Double.parseDouble(args[0]);
						final double z = Double.parseDouble(args[2]);
						final double y = Double.parseDouble(args[1]);
						p.teleport(new Location(p.getWorld(), x, y, z));
					}
				} else {
					p.sendMessage(ChatColor.RED + "For mange argumenter");
				}
			}
		} else {
			if (args.length == 4) {
				final Player tp = Bukkit.getServer().getPlayer(args[0]);
				final double x = Double.parseDouble(args[1]);
				final double z = Double.parseDouble(args[3]);
				final double y = Double.parseDouble(args[2]);
				tp.teleport(new Location(tp.getWorld(), x, y, z));
			} else {
				Sender.sendMessage(ChatColor.RED
						+ "Du er ikke en in game spiller");
				return true;
			}
		}
		return true;
	}
}
