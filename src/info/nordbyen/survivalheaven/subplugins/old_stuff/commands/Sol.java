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

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class Sol.
 */
public class Sol implements CommandExecutor {

    /** The i. */
    int i = 0;
    /** The sol. */
    ArrayList<String> sol = new ArrayList<String>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
     * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender Sender, final Command command, final String commandLabel, final String args[]) {
        if (Sender instanceof Player) {
            if (!(sol.contains(Sender.getName()))) {
                if (command.getName().equalsIgnoreCase("sol")) {
                    if (args.length == 0) {
                        if (((Player) Sender).getWorld().hasStorm() == true) {
                            i += 1;
                            sol.add(Sender.getName());
                            if (i < (Bukkit.getOnlinePlayers().size() / 2)) {
                                Bukkit.broadcastMessage(ChatColor.BLUE + Sender.getName() + ChatColor.AQUA + " stemte på å få sol! " + i + "/" + (Bukkit.getOnlinePlayers().size() / 2) + ". Skriv /sol for å stemme");
                            } else {
                                for (final World w : Bukkit.getWorlds()) {
                                    w.setStorm(false);
                                    w.setThundering(false);
                                }
                                sol.clear();
                                Bukkit.broadcastMessage(ChatColor.BLUE + Sender.getName() + ChatColor.AQUA + " stemte på å få sol! " + i + "/" + (Bukkit.getOnlinePlayers().size() / 2));
                                Bukkit.broadcastMessage(ChatColor.AQUA + "Været ble satt til sol!!");
                                i = 0;
                            }
                        } else {
                            Sender.sendMessage(ChatColor.RED + "Det er allerede sol i din verden");
                        }
                    }
                }
            } else {
                Sender.sendMessage(ChatColor.AQUA + "Du har allerede stemt!");
            }
        }
        return false;
    }
}
