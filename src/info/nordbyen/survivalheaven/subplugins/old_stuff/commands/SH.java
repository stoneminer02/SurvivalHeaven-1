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

// TODO: Auto-generated Javadoc
/**
 * The Class SH.
 */
public class SH implements CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
     * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(final CommandSender Sender, final Command command, final String CommandLabel, final String[] args) {
        if (Sender instanceof Player) {
            final Player p = (Player) Sender;
            if (command.getName().equalsIgnoreCase("sh")) {
                if (args.length > 0) {
                    final StringBuffer msg = new StringBuffer();
                    for (final String arg : args) {
                        msg.append(arg + " ");
                    }
                    p.sendMessage(ChatColor.DARK_RED + "Meldingen din er sendt: " + ChatColor.RED + msg.toString());
                    for (final Player r : Bukkit.getOnlinePlayers()) {
                        if (r.hasPermission("sh.kick")) {
                            if (r != null) {
                                r.sendMessage(ChatColor.DARK_RED + "[StabHjelp] " + p.getName() + ": " + ChatColor.RED + msg.toString());
                            } else {
                                p.sendMessage(ChatColor.RED + "Finner ingen online stabmedlemmer");
                            }
                        }
                    }
                } else {
                    Sender.sendMessage(ChatColor.RED + "Feil: Bruk /sh <melding>");
                }
            }
        } else {
            Sender.sendMessage(ChatColor.RED + "Du er ikke en in game spiller");
        }
        return true;
    }
}
