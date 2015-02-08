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

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class TPA.
 */
public class TPA implements CommandExecutor {

    /** The pl. */
    JavaPlugin pl = info.nordbyen.survivalheaven.SH.getPlugin();
    /** The tpa. */
    public static HashMap<String, String> tpa = new HashMap<String, String>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
     * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String args[]) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                final Player p = Bukkit.getPlayer(args[0]);
                if (p != null) {
                    tpa.put(p.getName(), Sender.getName());
                    Sender.sendMessage(ChatColor.GREEN + "Forespørsel sendt!");
                    p.sendMessage(ChatColor.DARK_GREEN + Sender.getName() + " vil teleportere til deg.");
                    p.sendMessage(ChatColor.GREEN + "Sriv /tpaccept for å godta");
                    pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {

                        @Override
                        public void run() {
                            tpa.remove(Sender.getName());
                        }
                    }, 20 * 30);
                }
            } else {
                Sender.sendMessage(ChatColor.RED + "Bruk /tpa <spiller>");
            }
        }
        return false;
    }
}
