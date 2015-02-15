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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class VerdenKommandoer.
 */
public class VerdenKommandoer implements CommandExecutor {

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
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (command.getName().equalsIgnoreCase("normal")) {
				p.teleport((Bukkit.getWorld("world").getSpawnLocation()));
			}
			if (command.getName().equalsIgnoreCase("pvp")) {
				p.teleport(Bukkit.getWorld("pvp").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("theend")) {
				if (p.getWorld().getName().equalsIgnoreCase("PvP")) {
					p.teleport(Bukkit.getWorld("theend_pvp").getSpawnLocation());
				} else {
					p.teleport(Bukkit.getWorld("world_the_end")
							.getSpawnLocation());
				}
			}
			if (command.getName().equalsIgnoreCase("nether")) {
				if (p.getWorld().getName() != "PvP") {
					p.teleport(Bukkit.getWorld("world_nether")
							.getSpawnLocation());
				} else {
					p.teleport(Bukkit.getWorld("pvp_nether").getSpawnLocation());
				}
			}
			if (command.getName().equalsIgnoreCase("kreativ")) {
				p.teleport(Bukkit.getWorld("Kreativ").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("pvpt")) {
				p.teleport(Bukkit.getWorld("PvPTeams").getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("old")) {
				p.teleport(Bukkit.getWorld("GodeGamleNormal")
						.getSpawnLocation());
			}
			if (command.getName().equalsIgnoreCase("pve")) {
				p.teleport(Bukkit.getWorld("PvE").getSpawnLocation());
			}
		}
		return true;
	}
}
