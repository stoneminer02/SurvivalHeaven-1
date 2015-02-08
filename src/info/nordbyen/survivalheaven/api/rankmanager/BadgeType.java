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

package info.nordbyen.survivalheaven.api.rankmanager;

import org.bukkit.ChatColor;

// TODO: Auto-generated Javadoc
/**
 * The Enum BadgeType.
 */
public enum BadgeType {
    /** The bygger. */
    BYGGER(1, "BYGGER", "+", ChatColor.YELLOW),
    /** The pensjonist. */
    PENSJONIST(2, "PENSJONIST", "+", ChatColor.DARK_PURPLE);

    /**
     * Gets the badge from id.
     * 
     * @param id the id
     * @return the badge from id
     */
    public static BadgeType getBadgeFromId(final int id) {
        if (id == 1)
            return BYGGER;
        if (id == 2)
            return PENSJONIST;
        return null;
    }

    /** The badge. */
    private final int badge;
    /** The name. */
    private final String name;
    /** The prefix. */
    private final String prefix;
    /** The color. */
    private final ChatColor color;

    /**
     * Instantiates a new badge type.
     * 
     * @param badge the badge
     * @param name the name
     * @param prefix the prefix
     * @param color the color
     */
    BadgeType(final int badge, final String name, final String prefix, final ChatColor color) {
        this.badge = badge;
        this.name = name;
        this.prefix = prefix;
        this.color = color;
    }

    /**
     * Gets the color.
     * 
     * @return the color
     */
    public ChatColor getColor() {
        return color;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getId() {
        return badge;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the prefix.
     * 
     * @return the prefix
     */
    public String getPrefix() {
        return color + prefix;
    }
}
