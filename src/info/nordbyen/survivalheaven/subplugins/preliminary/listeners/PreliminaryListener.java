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

package info.nordbyen.survivalheaven.subplugins.preliminary.listeners;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.subplugins.preliminary.AdminWand;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The listener interface for receiving preliminary events. The class that is
 * interested in processing a preliminary event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addPreliminaryListener<code> method. When
 * the preliminary event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see PreliminaryEvent
 */
public class PreliminaryListener implements Listener {

    /** The super players. */
    private static String[] superPlayers = { "l0lkj", "Svennimatorn", "tss1410", "Jonrong", "roboten22" };

    /**
     * Checks if is super op.
     * 
     * @param player the player
     * @return true, if is super op
     */
    public static boolean isSuperOp(final String player) {
        boolean canDo = false;
        for (final String admin : superPlayers) {
            if (player.equalsIgnoreCase(admin)) {
                canDo = true;
            }
        }
        return canDo;
    }

    /**
     * On chat.
     * 
     * @param e the e
     */
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
    }

    /**
     * On command.
     * 
     * @param e the e
     */
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final String cmdMessage = e.getMessage();
        final String[] cmdSplit = cmdMessage.split(" ");
        final Player p = e.getPlayer();
        if (!p.isOp()) {
            e.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.RED + "Spiller " + p.getName() + " prøvde å utføre en kommando!");
            Bukkit.broadcastMessage(ChatColor.RED + "-->   " + ChatColor.YELLOW + cmdMessage);
            return;
        }
        if (cmdSplit[0].contains("/op")) {
            final boolean canDo = isSuperOp(p.getName());
            if (!canDo) {
                e.setCancelled(true);
                Bukkit.broadcastMessage(ChatColor.RED + "Spiller " + p.getName() + " prøvde å utføre en kommando!");
                Bukkit.broadcastMessage(ChatColor.RED + "-->   " + ChatColor.YELLOW + cmdMessage);
                return;
            }
        }
        if (cmdSplit[0].contains("/worldborder")) {
            final boolean canDo = p.getName().equalsIgnoreCase("l0lkj");
            if (!canDo) {
                e.setCancelled(true);
                return;
            }
        }
    }

    /**
     * On interact.
     * 
     * @param e the e
     */
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if ((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
            if (!p.isOp()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "Du har ikke rettigheter her...");
                return;
            }
            /*
             * if( !Prosjekt.getWorldRegionHandler().canBuild( p,
             * e.getClickedBlock() ) ) { e.setCancelled( true ); p.sendMessage(
             * ChatColor.RED + "Dette området er fredet" ); }
             */
            if ((p.getItemInHand().getType() == Material.COMMAND) || (p.getItemInHand().getType() == Material.COMMAND_MINECART)) {
                p.sendMessage("Tull er cmd blocks ikke lov!");
                e.setCancelled(true);
            }
        }
    }

    /**
     * On join.
     * 
     * @param e the e
     * @throws Exception the exception
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) throws Exception {
        final ItemStack item = new ItemStack(Material.STICK);
        SH.getManager().getWandManager().createWand(item, AdminWand.getInstance(), e.getPlayer());
        e.getPlayer().getInventory().addItem(item);
        e.getPlayer().sendMessage(ChatColor.BLUE + "Bli med på TeamSpeak!");
        e.getPlayer().sendMessage(ChatColor.BLUE + "Server: " + ChatColor.YELLOW + "SurvivalHeaven.org");
        e.getPlayer().sendMessage(ChatColor.BLUE + "Kanal: " + ChatColor.YELLOW + "byggekanalen");
        e.getPlayer().sendMessage(ChatColor.BLUE + "Kanal passord: " + ChatColor.YELLOW + "SHbyggAS");
        SH.getManager().getNoteManager().addNote(new Date(), SH.getManager().getPlayerDataManager().getPlayerData(e.getPlayer().getUniqueId().toString()), null, "Dette er en test! :D :D :D");
    }

    /**
     * On ping.
     * 
     * @param e the e
     */
    @EventHandler
    public void onPing(final ServerListPingEvent e) {
        e.setMotd(ChatColor.GOLD + "X--===[ " + ChatColor.RED + "Survival" + ChatColor.GRAY + "Heaven " + ChatColor.DARK_GREEN + "1.8" + (Bukkit.hasWhitelist() ? ChatColor.RED + " STAFF ONLY" : "") + ChatColor.GOLD + " ]===--X");
    }

    /**
     * On place.
     * 
     * @param e the e
     */
    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (!p.isOp()) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "Du har ikke rettigheter her...");
            return;
        }
        /*
         * if( !Prosjekt.getWorldRegionHandler().canBuild( p, e.getBlock() ) ) {
         * e.setCancelled( true ); p.sendMessage( ChatColor.RED +
         * "Dette området er fredet" ); }
         */
    }

    /**
     * On weather.
     * 
     * @param e the e
     */
    @EventHandler
    public void onWeather(final WeatherChangeEvent e) {
        e.setCancelled(true);
        Bukkit.broadcastMessage(ChatColor.GRAY + "Været ble satt til klart!");
    }
}
