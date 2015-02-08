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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

// TODO: Auto-generated Javadoc
/**
 * The Class FirstEncounter.
 */
public class FirstEncounter {

    /** The Constant waiting. */
    private static final List<String> waiting = new ArrayList<String>();

    /**
     * Adds the waiting player.
     * 
     * @param uuid the uuid
     */
    public static void addWaitingPlayer(final String uuid) {
        waiting.add(uuid);
    }

    /**
     * Gets the waiting players.
     * 
     * @return the waiting players
     */
    public static List<String> getWaitingPlayers() {
        return waiting;
    }

    /**
     * Checks if is inside.
     * 
     * @param location the location
     * @param min the min
     * @param max the max
     * @return true, if is inside
     */
    public static boolean isInside(final Location location, final Location min, final Location max) {
        final double xmin = Math.min(min.getX(), max.getX());
        final double xmax = Math.max(min.getX(), max.getX());
        final double ymin = Math.min(min.getY(), max.getY());
        final double ymax = Math.max(min.getY(), max.getY());
        final double zmin = Math.min(min.getZ(), max.getZ());
        final double zmax = Math.max(min.getZ(), max.getZ());
        if ((location.getBlockX() <= xmax) && (location.getBlockX() >= xmin)) {
            if ((location.getBlockY() <= ymax) && (location.getBlockY() >= ymin)) {
                if ((location.getBlockZ() <= zmax) && (location.getBlockZ() >= zmin))
                    return true;
            } else
                return false;
        }
        return false;
    }

    /**
     * Removes the waiting player.
     * 
     * @param uuid the uuid
     */
    public static void removeWaitingPlayer(final String uuid) {
        waiting.remove(uuid);
    }
}
