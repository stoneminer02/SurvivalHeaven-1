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

package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.Dispensers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class Commands.
 */
public class Commands implements CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
     * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("infdisp")) {
            if (!sender.hasPermission("infdispenser.command"))
                return true;
            if ((sender instanceof ConsoleCommandSender)) {
                sender.sendMessage(ChatColor.RED + "du kan ikke bruke /infdisp kommandoen utenfor spillet!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GRAY + "Bruk " + ChatColor.YELLOW + "/infdisp sett" + ChatColor.GRAY + " - for å sette en uendelig dispenser");
                sender.sendMessage(ChatColor.GRAY + "Brukt " + ChatColor.YELLOW + "/infdisp fjern" + ChatColor.GRAY + " - for å fjerne en uendelig dispenser");
                return true;
            }
            if (args[0].equalsIgnoreCase("sett")) {
                final Player player = (Player) sender;
                if ((Util.getBlockTarget(player).getType() != Material.DISPENSER) && (Util.getBlockTarget(player).getType() != Material.DROPPER)) {
                    player.sendMessage(SH.PREFIX + "Du må se på en dispenser eller dropper!");
                    return true;
                }
                final Block dispenser = Util.getBlockTarget(player);
                if (!Dispensers.getInstance().getList("dispensers").contains(dispenser.getLocation().toString())) {
                    Dispensers.getInstance();
                    Dispensers.setLocation(dispenser.getLocation());
                    player.sendMessage(SH.PREFIX + "Fyll denne dispenseren/dropperen med uendelig stacks!");
                } else {
                    player.sendMessage(SH.PREFIX + "Denne dispenseren/dropperen er allerede uendelig");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("fjern")) {
                final Player player = (Player) sender;
                if ((Util.getBlockTarget(player).getType() != Material.DISPENSER) && (Util.getBlockTarget(player).getType() != Material.DROPPER)) {
                    player.sendMessage(SH.PREFIX + "Du må se på en dispenser eller dropper!");
                    return true;
                }
                final Block dispenser = Util.getBlockTarget(player);
                if (Dispensers.getInstance().getList("dispensers").contains(dispenser.getLocation().toString())) {
                    Dispensers.getInstance().getList("dispensers").remove(dispenser.getLocation().toString());
                    Dispensers.getInstance().save();
                }
                player.sendMessage(SH.PREFIX + "Fjernet dispenseren/dropperen fra listen");
                return true;
            }
            sender.sendMessage(SH.PREFIX + ChatColor.RED + "Feil syntaks!");
            return true;
        }
        return true;
    }
}
