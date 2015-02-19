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

package info.nordbyen.survivalheaven.subplugins.regions.teleports;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class ServerCommand.
 */
public class RegionTeleportCommand extends AbstractCommand {

	/**
	 * Instantiates a new server command.
	 */
	public RegionTeleportCommand() {
		super("nord", "/<command>",
				"Kommandoer for å teleportere til utpostene", 
				Arrays.asList(new String[] { "sør", "nord", "øst", "vest", "spawn" }));
		register();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.command.AbstractCommand#onCommand(org.
	 * bukkit.command .CommandSender, org.bukkit.command.Command,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if( !( sender instanceof Player ) ) {
			sender.sendMessage(ChatColor.RED + "Bare in-game spillere kan teleportere!");
			return true;
		}
		Player p = ( Player ) sender;
		if ( label.equalsIgnoreCase( "sør" ) ) {
			p.sendMessage( ChatColor.GREEN + "Teleporterer til sør" );
			p.teleport( new Location( Bukkit.getWorld( "NyVerden" ), 145, 77, 6234 ) );
		} else if ( label.equalsIgnoreCase( "nord" ) ) {
			p.sendMessage( ChatColor.GREEN + "Teleporterer til nord" );
			p.teleport( new Location( Bukkit.getWorld( "NyVerden" ), -232, 64, -6071 ) );
		} else if ( label.equalsIgnoreCase( "øst" ) ) {
			p.sendMessage( ChatColor.GREEN + "Teleporterer til øst" );
			p.teleport( new Location( Bukkit.getWorld( "NyVerden" ), 6269.5, 65.5, 780.5, 180, 0 ) );
		} else if ( label.equalsIgnoreCase( "vest" ) ) {
			p.sendMessage( ChatColor.GREEN + "Teleporterer til vest" );
			p.teleport( new Location( Bukkit.getWorld( "NyVerden" ), -5774, 73, 95 ) );
		} else if ( label.equalsIgnoreCase( "spawn" ) ) {
			p.sendMessage( ChatColor.GREEN + "Teleporterer til spawn" );
			p.teleport( new Location( Bukkit.getWorld( "NyVerden" ), 104.5, 65.5, 161.5, 270, 0 ) );
		}
		return true;
	}
}
