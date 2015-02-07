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

package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.regions.RegionData;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.api.util.FancyMessages;
import info.nordbyen.survivalheaven.subplugins.regions.teleports.RegionTeleportCommand;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * The Class RegionUpdater.
 */
public class RegionUpdater extends SubPlugin {

    /**
     * The listener interface for receiving regionUpdater events. The class that
     * is interested in processing a regionUpdater event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's
     * <code>addRegionUpdaterListener<code> method. When
     * the regionUpdater event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see RegionUpdaterEvent
     */
    public class RegionUpdaterListener implements Listener {

        /**
         * On quit.
         * 
         * @param e the e
         */
        @EventHandler
        public void onQuit(final PlayerQuitEvent e) {
            playerRegions.remove(e.getPlayer().getUniqueId().toString());
        }
    }

    /** The player regions. */
    private final HashMap<String, RegionData> playerRegions = new HashMap<String, RegionData>();

    /**
     * Instantiates a new region updater.
     * 
     * @param name the name
     */
    public RegionUpdater(final String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
     */
    @Override
    protected void disable() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
     */
    @Override
    protected void enable() {
        repeatingTask();
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(getPlugin(), new Runnable() {

            @Override
            public void run() {
                SH.getManager().getRegionManager().addRegion(RegionData.createRegion(SH.getManager().getSenter().clone(), "Midtgard", 230, 100, false, false, true, false));
                SH.getManager().getRegionManager().addRegion(RegionData.createRegion(SH.getManager().getSenter().clone(), "Utkanten", 330, 95, false, false, true, false));
                SH.getManager().getRegionManager().addRegion(RegionData.createRegion(SH.getManager().getSenter().clone(), "Fredens land", 6000, 90, false, true, true, false));
                SH.getManager().getRegionManager().addRegion(RegionData.createRegion(SH.getManager().getSenter().clone(), "Krigens land", Integer.MAX_VALUE, Integer.MIN_VALUE, true, true, true, false));
            }
        }, 1L);
        Bukkit.getPluginManager().registerEvents(new RegionUpdaterListener(), getPlugin());
        new RegionTeleportCommand();
    }

    /**
     * Repeating task.
     */
    private void repeatingTask() {
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(getPlugin(), new Runnable() {

            @Override
            public void run() {
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    final RegionData region = SH.getManager().getRegionManager().getRegionAt(o.getLocation());
                    if (region != playerRegions.get(o.getUniqueId().toString())) {
                        sendRegionName(o, region);
                    }
                    playerRegions.put(o.getUniqueId().toString(), region);
                }
            }
        }, 1L, 1L);
    }

    /**
     * Send region name.
     * 
     * @param p the p
     * @param region the region
     */
    public void sendRegionName(final Player p, final RegionData region) {
        if (region == null)
            return;
        FancyMessages.sendActionBar(p, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "------[ " + region.getName() + " ]------");
    }
}
