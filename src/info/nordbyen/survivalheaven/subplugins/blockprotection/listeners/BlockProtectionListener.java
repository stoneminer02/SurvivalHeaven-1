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

package info.nordbyen.survivalheaven.subplugins.blockprotection.listeners;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * The listener interface for receiving blockProtection events. The class that
 * is interested in processing a blockProtection event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addBlockProtectionListener<code> method. When
 * the blockProtection event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see BlockProtectionEvent
 */
public class BlockProtectionListener implements Listener {

    /**
     * On break.
     * 
     * @param e the e
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(final BlockBreakEvent e) {
        final Block b = e.getBlock();
        final Player p = e.getPlayer();
        if ((b.getType() != Material.AIR) && (b.getType() != Material.WATER) && (b.getType() != Material.LAVA)) // En
        // fast
        // blokk
        {
            final IPlayerData pd = SH.getManager().getBlockManager().getOwner(b);
            if (pd != null) // En eier
            {
                // TODO Blokken er eid av noen
                final String uuid = pd.getUUID();
                if (!uuid.equals(p.getUniqueId().toString())) // Ikke samme
                // eier
                {
                    final boolean canBreak = false; // TODO
                    if (!canBreak) // Ikke kan ødelegge
                    {
                        FancyMessages.sendActionBar(p, ChatColor.RED + "Denne blokken er eid av " + pd.getName());// TODO
                        // bruke
                        // Translator
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * On place.
     * 
     * @param e the e
     */
    @EventHandler
    public void onPlace(final BlockPlaceEvent e) {
        final Block b = e.getBlock();
        final Player p = e.getPlayer();
        BlockPlacedType type = BlockPlacedType.SURVIVAL;
        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            type = BlockPlacedType.CREATIVE;
        }
        SH.getManager().getBlockManager().setBlockOwner(b, p, type);
    }
}
