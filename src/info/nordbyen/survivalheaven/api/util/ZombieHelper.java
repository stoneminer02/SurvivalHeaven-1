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

import net.minecraft.server.v1_8_R1.EntityZombie;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftZombie;
import org.bukkit.entity.Zombie;

/**
 * The Class ZombieHelper.
 */
public class ZombieHelper {

    /**
     * Checks if is baby.
     * 
     * @param zombie the zombie
     * @return true, if is baby
     */
    public static boolean isBaby(final Zombie zombie) {
        final EntityZombie ent = ((CraftZombie) zombie).getHandle();
        return ent.isBaby();
    }

    /**
     * Checks if is villager.
     * 
     * @param zombie the zombie
     * @return true, if is villager
     */
    public static boolean isVillager(final Zombie zombie) {
        final EntityZombie ent = ((CraftZombie) zombie).getHandle();
        return ent.isVillager();
    }

    /**
     * Sets the baby.
     * 
     * @param zombie the zombie
     * @param value the value
     */
    public static void setBaby(final Zombie zombie, final boolean value) {
        final EntityZombie ent = ((CraftZombie) zombie).getHandle();
        ent.setBaby(value);
    }

    /**
     * Sets the villager.
     * 
     * @param zombie the zombie
     * @param value the value
     */
    public static void setVillager(final Zombie zombie, final boolean value) {
        final EntityZombie ent = ((CraftZombie) zombie).getHandle();
        ent.setVillager(value);
    }
}
