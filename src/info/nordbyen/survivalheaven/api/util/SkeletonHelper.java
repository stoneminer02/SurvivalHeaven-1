/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
	 * @param skeleton
	 *            the skeleton
	 * @param giveRandomEnchantments
	 *            the give random enchantments
	 */
	private static void changeIntoNormal(final Skeleton skeleton,
			final boolean giveRandomEnchantments) {
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
			final Field selector = EntitySkeleton.class
					.getDeclaredField("goalSelector");
			selector.setAccessible(true);
			final Field d = EntitySkeleton.class.getDeclaredField("d");
			d.setAccessible(true);
			final PathfinderGoalSelector goals = (PathfinderGoalSelector) selector
					.get(ent);
			goals.a(4, (PathfinderGoal) d.get(ent));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Change into wither.
	 *
	 * @param skeleton
	 *            the skeleton
	 */
	private static void changeIntoWither(final Skeleton skeleton) {
		final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
		try {
			ent.setSkeletonType(1);
			final Field selector = EntitySkeleton.class
					.getDeclaredField("goalSelector");
			selector.setAccessible(true);
			final Field e = EntitySkeleton.class.getDeclaredField("e");
			e.setAccessible(true);
			final PathfinderGoalSelector goals = (PathfinderGoalSelector) selector
					.get(ent);
			goals.a(4, (PathfinderGoal) e.get(ent));
			ent.setEquipment(
					0,
					new net.minecraft.server.v1_8_R1.ItemStack(Item
							.getById(272)));
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the type.
	 *
	 * @param skeleton
	 *            the skeleton
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
	 * @param skeleton
	 *            the skeleton
	 * @return true, if is wither
	 */
	public static boolean isWither(final Skeleton skeleton) {
		final EntitySkeleton ent = ((CraftSkeleton) skeleton).getHandle();
		return ent.getSkeletonType() == 1;
	}

	/**
	 * Sets the type.
	 *
	 * @param skeleton
	 *            the skeleton
	 * @param type
	 *            the type
	 */
	public static void setType(final Skeleton skeleton, final SkeletonType type) {
		if (type == SkeletonType.NORMAL) {
			changeIntoNormal(skeleton, true);
		} else {
			changeIntoWither(skeleton);
		}
	}
}
