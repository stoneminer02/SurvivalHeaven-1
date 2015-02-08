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

import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;
import info.nordbyen.survivalheaven.subplugins.quest.first_encounter.FirstEncounterConfig;
import info.nordbyen.survivalheaven.subplugins.quest.first_encounter.FirstEncounterListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class QuestHandler.
 */
@SurvivalHeavenSubPlugin(name = "QuestHandler")
public class QuestHandler {

    /**
     * Disble.
     * 
     * @param plugin the plugin
     */
    @SurvivalHeavenDisable
    private static void disble(final JavaPlugin plugin) {
    }

    /**
     * Enable.
     * 
     * @param plugin the plugin
     */
    @SurvivalHeavenEnable
    private static void enable(final JavaPlugin plugin) {
        FirstEncounterConfig.getInstance();
        Bukkit.getPluginManager().registerEvents(new FirstEncounterListener(), plugin);
        Godta_Command.initCommand();
    }
}
