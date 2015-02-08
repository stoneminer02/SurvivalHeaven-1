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

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.EntityCreeper;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomCreeper.
 */
public class CustomCreeper {

    /**
     * Gets the fuse.
     * 
     * @param creeper the creeper
     * @return the fuse
     */
    public static int getFuse(final Creeper creeper) {
        final EntityCreeper entCreeper = ((CraftCreeper) creeper).getHandle();
        Field fuseF = null;
        try {
            fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        }
        fuseF.setAccessible(true);
        int fuse = 0;
        try {
            fuse = fuseF.getInt(entCreeper);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return fuse;
    }

    /**
     * Gets the radius.
     * 
     * @param creeper the creeper
     * @return the radius
     */
    public static int getRadius(final Creeper creeper) {
        final EntityCreeper entCreeper = ((CraftCreeper) creeper).getHandle();
        Field radiusF = null;
        try {
            radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        }
        radiusF.setAccessible(true);
        int radius = 0;
        try {
            radius = radiusF.getInt(entCreeper);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        return radius;
    }

    /**
     * Sets the fuse.
     * 
     * @param creeper the creeper
     * @param fuse the fuse
     */
    public static void setFuse(final Creeper creeper, final int fuse) {
        final EntityCreeper entCreeper = ((CraftCreeper) creeper).getHandle();
        Field fuseF = null;
        try {
            fuseF = EntityCreeper.class.getDeclaredField("maxFuseTicks");
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        }
        fuseF.setAccessible(true);
        try {
            fuseF.setInt(entCreeper, fuse);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the radius.
     * 
     * @param creeper the creeper
     * @param radius the radius
     */
    public static void setRadius(final Creeper creeper, final int radius) {
        final EntityCreeper entCreeper = ((CraftCreeper) creeper).getHandle();
        Field radiusF = null;
        try {
            radiusF = EntityCreeper.class.getDeclaredField("explosionRadius");
        } catch (final NoSuchFieldException e) {
            e.printStackTrace();
        } catch (final SecurityException e) {
            e.printStackTrace();
        }
        radiusF.setAccessible(true);
        try {
            radiusF.setInt(entCreeper, radius);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
