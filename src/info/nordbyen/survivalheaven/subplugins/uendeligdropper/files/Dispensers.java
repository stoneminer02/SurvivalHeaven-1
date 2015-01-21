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

package info.nordbyen.survivalheaven.subplugins.uendeligdropper.files;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

/**
 * The Class Dispensers.
 */
public class Dispensers extends CustomConfiguration {

    /** The dispensers. */
    private static Dispensers dispensers;
    /** The empty list. */
    private static List<String> emptyList = new ArrayList<String>();

    /**
     * Gets the single instance of Dispensers.
     * 
     * @return single instance of Dispensers
     */
    public static Dispensers getInstance() {
        if (dispensers == null) {
            dispensers = new Dispensers();
        }
        return dispensers;
    }

    /**
     * Checks if is dispenser.
     * 
     * @param location the location
     * @return true, if is dispenser
     */
    public static boolean isDispenser(final Location location) {
        getInstance().reload();
        if (getInstance().getList("dispensers").contains(location.toString()))
            return true;
        return false;
    }

    /**
     * Sets the location.
     * 
     * @param location the new location
     */
    @SuppressWarnings("unchecked")
    public static void setLocation(final Location location) {
        ((ArrayList<String>) getInstance().getList("dispensers")).add(location.toString());
        getInstance().save();
    }

    /**
     * Instantiates a new dispensers.
     */
    public Dispensers() {
        super(new File("./plugins/SurvivalHeaven/dispensers.yml"));
        dispensers = this;
        load();
        save();
        saveDefault();
    }

    /**
     * Save default.
     */
    private void saveDefault() {
        if (!contains("dispensers")) {
            set("dispensers", emptyList);
            save();
        }
    }
}
