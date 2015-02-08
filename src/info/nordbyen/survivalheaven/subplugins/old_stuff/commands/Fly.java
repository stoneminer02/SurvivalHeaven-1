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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class Fly.
 */
public class Fly implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String commandLabel, final String args[]) {
		if (command.getName().equalsIgnoreCase("fly")) {
			if (Sender.getName().equalsIgnoreCase("TheDudeAdrian")) {
				final Player g = (Player) Sender;
				g.setFlying(true);
				g.setAllowFlight(true);
				return false;
			}
			if (Sender.hasPermission("sh.fly")) {
				if (args.length == 1) {
					final Player p = Bukkit.getPlayer(args[0]);
					if (p.getAllowFlight() == true) {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.RED + "av");
						p.setAllowFlight(false);
						p.setFlying(false);
					} else {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.GREEN + "på");
						p.setAllowFlight(true);
						p.setFlying(true);
					}
				} else if (args.length == 0) {
					final Player p = (Player) Sender;
					if (p.getAllowFlight() == true) {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.RED + "av");
						p.setAllowFlight(false);
						p.setFlying(false);
					} else {
						p.sendMessage(ChatColor.GRAY + "Flying er nå "
								+ ChatColor.GREEN + "på");
						p.setAllowFlight(true);
						p.setFlying(true);
					}
				}
			}
		}
		return false;
	}
}
