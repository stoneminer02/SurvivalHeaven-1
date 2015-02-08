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

package info.nordbyen.survivalheaven.subplugins.quest;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class Godta_Command.
 */
public class Godta_Command extends AbstractCommand {

    /** The Constant players. */
    public static final HashMap<String, Acceptable> players = new HashMap<String, Acceptable>();
    /** The instance. */
    private static Godta_Command instance = null;

    /**
     * Clear command.
     */
    public static void clearCommand() {
        instance = null;
    }

    /**
     * Inits the command.
     */
    public static void initCommand() {
        if (instance == null) {
            instance = new Godta_Command();
        }
    }

    /**
     * Instantiates a new godta_ command.
     */
    private Godta_Command() {
        super("godta", "/<command>", "En kommando for bruk i quester");
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
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        sender.sendMessage(ChatColor.RED + "HAHA");
        if (!isAuthorized(sender, "sh.godta")) {
            sender.sendMessage(ChatColor.RED + "Du har ikke tillatelse til dette");
        }
        if (!isPlayer(sender)) {
            sender.sendMessage(ChatColor.RED + "Du må være en spiller ( Sry Thomas :P )");
        }
        final Player p = (Player) sender;
        if (players.containsKey(p.getUniqueId().toString())) {
            players.get(p.getUniqueId().toString()).executedAccept(p.getUniqueId().toString());
            players.remove(p.getUniqueId().toString());
        } else {
            p.sendMessage(ChatColor.RED + "Du har ingenting å godta");
        }
        return true;
    }
}
