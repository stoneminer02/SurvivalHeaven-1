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

package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.subplugins.playerdata.listenere.PlayerDatalistener;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerDataManagerPlugin.
 */
public class PlayerDataManagerPlugin extends SubPlugin {

    /**
     * The Class PlayerDataTask.
     */
    private class PlayerDataTask extends BukkitRunnable {

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            SH.getManager().getPlayerDataManager().saveDataToDatabase();
        }
    }

    /** The scheduler_running. */
    private boolean scheduler_running = false;

    /**
     * Instantiates a new player data manager plugin.
     * 
     * @param name the name
     */
    public PlayerDataManagerPlugin(final String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
     */
    @Override
    public void disable() {
        SH.getManager().getPlayerDataManager().saveDataToDatabase();
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
     */
    @Override
    public void enable() {
        ((PlayerDataManager) SH.getManager().getPlayerDataManager()).createTable();
        ((PlayerDataManager) SH.getManager().getPlayerDataManager()).updateDataFromDatabase();
        startScheduler();
        Bukkit.getPluginManager().registerEvents(new PlayerDatalistener(), getPlugin());
    }

    /**
     * Start scheduler.
     */
    @SuppressWarnings("deprecation")
    private void startScheduler() {
        if (scheduler_running)
            return;
        scheduler_running = true;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), new PlayerDataTask(), 20 * 60 * 5L, 20 * 60 * 5L);
    }
}
