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

package info.nordbyen.survivalheaven.api.util;

import net.minecraft.server.v1_8_R1.NBTBase;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

import org.bukkit.enchantments.Enchantment;

// TODO: Auto-generated Javadoc
/**
 * The Class StoredEnchantment.
 */
public class StoredEnchantment {

    /** The ench. */
    private Enchantment ench;
    /** The lvl. */
    private short lvl;

    /**
     * Instantiates a new stored enchantment.
     * 
     * @param ench the ench
     * @param lvl the lvl
     */
    public StoredEnchantment(final Enchantment ench, final int lvl) {
        this.setEnchantment(ench);
        setLevel((short) lvl);
    }

    /**
     * Instantiates a new stored enchantment.
     * 
     * @param tag the tag
     */
    public StoredEnchantment(final NBTTagCompound tag) {
        setEnchantment(tag.getShort("id"));
        setLevel(tag.getShort("lvl"));
    }

    /**
     * Gets the enchantment.
     * 
     * @return the enchantment
     */
    public Enchantment getEnchantment() {
        return ench;
    }

    /**
     * Gets the level.
     * 
     * @return the level
     */
    public short getLevel() {
        return lvl;
    }

    /**
     * Gets the tag.
     * 
     * @return the tag
     */
    @SuppressWarnings("deprecation")
    public NBTBase getTag() {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setShort("id", (short) getEnchantment().getId());
        tag.setShort("lvl", getLevel());
        return tag;
    }

    /**
     * Sets the enchantment.
     * 
     * @param ench the new enchantment
     */
    public void setEnchantment(final Enchantment ench) {
        this.ench = ench;
    }

    /**
     * Sets the enchantment.
     * 
     * @param ench the new enchantment
     */
    @SuppressWarnings("deprecation")
    public void setEnchantment(final int ench) {
        this.ench = Enchantment.getById(ench);
    }

    /**
     * Sets the level.
     * 
     * @param lvl the new level
     */
    public void setLevel(final short lvl) {
        this.lvl = lvl;
    }
}
