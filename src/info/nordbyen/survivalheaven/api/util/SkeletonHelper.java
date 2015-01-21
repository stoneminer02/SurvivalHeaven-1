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
import java.lang.reflect.Method;

import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftSkeleton;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;

/**
 * The Class SkeletonHelper.
 */
public class SkeletonHelper {

    /**
     * Change into normal.
     * 
     * @param skeleton the skeleton
     * @param giveRandomEnchantments the give random enchantments
     */
    private static void changeIntoNormal(final Skeleton skeleton, final boolean giveRandomEnchantments) {
        final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
        try {
            ent.setSkeletonType(0);
            final Method be = EntitySkeleton.class.getDeclaredMethod("bE");
            be.setAccessible(true);
            be.invoke(ent);
            if (giveRandomEnchantments) {
                final Method bf = EntityLiving.class.getDeclaredMethod("bF");
                bf.setAccessible(true);
                bf.invoke(ent);
            }
            final Field selector = EntitySkeleton.class.getDeclaredField("goalSelector");
            selector.setAccessible(true);
            final Field d = EntitySkeleton.class.getDeclaredField("d");
            d.setAccessible(true);
            final PathfinderGoalSelector goals = (PathfinderGoalSelector) selector.get(ent);
            goals.a(4, (PathfinderGoal) d.get(ent));
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Change into wither.
     * 
     * @param skeleton the skeleton
     */
    private static void changeIntoWither(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
        try {
            ent.setSkeletonType(1);
            final Field selector = EntitySkeleton.class.getDeclaredField("goalSelector");
            selector.setAccessible(true);
            final Field e = EntitySkeleton.class.getDeclaredField("e");
            e.setAccessible(true);
            final PathfinderGoalSelector goals = (PathfinderGoalSelector) selector.get(ent);
            goals.a(4, (PathfinderGoal) e.get(ent));
            ent.setEquipment(0, new net.minecraft.server.v1_8_R1.ItemStack(Item.getById(272)));
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the type.
     * 
     * @param skeleton the skeleton
     * @return the type
     */
    @SuppressWarnings("deprecation")
    public static SkeletonType getType(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
        return SkeletonType.getType(ent.getSkeletonType());
    }

    /**
     * Checks if is wither.
     * 
     * @param skeleton the skeleton
     * @return true, if is wither
     */
    public static boolean isWither(final Skeleton skeleton) {
        final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
        return ent.getSkeletonType() == 1;
    }

    /**
     * Sets the type.
     * 
     * @param skeleton the skeleton
     * @param type the type
     */
    public static void setType(final Skeleton skeleton, final SkeletonType type) {
        if (type == SkeletonType.NORMAL) {
            changeIntoNormal(skeleton, true);
        } else {
            changeIntoWither(skeleton);
        }
    }
}
