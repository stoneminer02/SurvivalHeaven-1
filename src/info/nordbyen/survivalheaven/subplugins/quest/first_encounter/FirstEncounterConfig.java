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

package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;

// TODO: Auto-generated Javadoc
/**
 * The Class FirstEncounterConfig.
 */
public class FirstEncounterConfig extends CustomConfiguration {

    /** The cfg. */
    private static FirstEncounterConfig cfg;

    /**
     * Gets the door1_1.
     * 
     * @return the door1_1
     */
    public static Location getDoor1_1() {
        return new Location(Bukkit.getWorld(getInstance().getString("quest1.door1.world")), getInstance().getDouble("quest1.door1.1.x"), getInstance().getDouble("quest1.door1.1.y"), getInstance().getDouble("quest1.door1.1.z"));
    }

    /**
     * Gets the door1_2.
     * 
     * @return the door1_2
     */
    public static Location getDoor1_2() {
        return new Location(Bukkit.getWorld(getInstance().getString("quest1.door1.world")), getInstance().getDouble("quest1.door1.2.x"), getInstance().getDouble("quest1.door1.2.y"), getInstance().getDouble("quest1.door1.2.z"));
    }

    /**
     * Gets the single instance of FirstEncounterConfig.
     * 
     * @return single instance of FirstEncounterConfig
     */
    public static FirstEncounterConfig getInstance() {
        if (cfg == null) {
            cfg = new FirstEncounterConfig();
        }
        return cfg;
    }

    /**
     * Instantiates a new first encounter config.
     */
    private FirstEncounterConfig() {
        super(new File("./plugins/SurvivalHeaven/questconfig/quest1.yml"));
        cfg = this;
        load();
        save();
        saveDefault();
    }

    /**
     * Save default.
     */
    private void saveDefault() {
        if (!contains("quest1.door1.world")) {
            set("quest1.door1.world", "NyVerden");
        }
        if (!contains("quest1.door1.1.x")) {
            set("quest1.door1.1.x", 87);
        }
        if (!contains("quest1.door1.1.y")) {
            set("quest1.door1.1.y", 62);
        }
        if (!contains("quest1.door1.1.z")) {
            set("quest1.door1.1.z", 209);
        }
        if (!contains("quest1.door1.2.x")) {
            set("quest1.door1.2.x", 80);
        }
        if (!contains("quest1.door1.2.y")) {
            set("quest1.door1.2.y", 65);
        }
        if (!contains("quest1.door1.2.z")) {
            set("quest1.door1.2.z", 209);
        }
        save();
    }
}
