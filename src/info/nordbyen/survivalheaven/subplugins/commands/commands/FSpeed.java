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

package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class FSpeed.
 */
public class FSpeed implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String CommandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (p.hasPermission("sh.fspeef")) {
				if (command.getName().equalsIgnoreCase("fspeed")) {
					if (args.length == 0) {
						p.sendMessage(ChatColor.RED + "/fspeed <1-11>");
					} else if (args.length == 1) {
						switch (args[0].toLowerCase()) {
						case "1":
							p.setFlySpeed(0.1f);
							break;
						case "2":
							p.setFlySpeed(0.15f);
							break;
						case "3":
							p.setFlySpeed(0.2f);
							break;
						case "4":
							p.setFlySpeed(0.3f);
							break;
						case "5":
							p.setFlySpeed(0.4f);
							break;
						case "6":
							p.setFlySpeed(0.5f);
							break;
						case "7":
							p.setFlySpeed(0.6f);
							break;
						case "8":
							p.setFlySpeed(0.7f);
							break;
						case "9":
							p.setFlySpeed(0.8f);
							break;
						case "10":
							p.setFlySpeed(0.9f);
							break;
						case "11":
							p.setFlySpeed(1f);
							break;
						}
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "Du har ikke permission!");
			}
		} else {
			Sender.sendMessage(ChatColor.RED + "Du er ikke en spiller!");
		}
		return true;
	}
}
