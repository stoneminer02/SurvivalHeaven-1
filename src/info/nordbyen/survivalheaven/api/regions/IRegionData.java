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

package info.nordbyen.survivalheaven.api.regions;

import org.bukkit.Location;

/**
 * The Interface IRegionData.
 */
public interface IRegionData {

    /**
     * Contains location.
     * 
     * @param loc the loc
     * @return true, if successful
     */
    public boolean containsLocation(final Location loc);

    /**
     * Gets the center.
     * 
     * @return the center
     */
    public Location getCenter();

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName();

    /**
     * Gets the radius.
     * 
     * @return the radius
     */
    public double getRadius();

    /**
     * Gets the z value.
     * 
     * @return the z value
     */
    public int getZValue();

    /**
     * Checks if is breakable.
     * 
     * @return true, if is breakable
     */
    public boolean isBreakable();

    /**
     * Checks if is invincible.
     * 
     * @return true, if is invincible
     */
    public boolean isInvincible();

    /**
     * Checks if is monsters.
     * 
     * @return true, if is monsters
     */
    public boolean isMonsters();

    /**
     * Checks if is pvp.
     * 
     * @return true, if is pvp
     */
    public boolean isPvp();

    /**
     * Sets the breakable.
     * 
     * @param breakable the new breakable
     */
    public void setBreakable(final boolean breakable);

    /**
     * Sets the center.
     * 
     * @param center the new center
     */
    public void setCenter(final Location center);

    /**
     * Sets the invincible.
     * 
     * @param invincible the new invincible
     */
    public void setInvincible(final boolean invincible);

    /**
     * Sets the monsters.
     * 
     * @param monsters the new monsters
     */
    public void setMonsters(final boolean monsters);

    /**
     * Sets the pvp.
     * 
     * @param pvp the new pvp
     */
    public void setPvp(final boolean pvp);

    /**
     * Sets the radius.
     * 
     * @param radius the new radius
     */
    public void setRadius(final double radius);
}
