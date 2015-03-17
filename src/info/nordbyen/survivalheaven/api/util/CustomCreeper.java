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

import net.minecraft.server.v1_8_R1.EntityCreeper;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftCreeper;
import org.bukkit.entity.Creeper;

/**
 * The Class CustomCreeper.
 */
public class CustomCreeper {

	/**
	 * Gets the fuse.
	 *
	 * @param creeper
	 *            the creeper
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
	 * @param creeper
	 *            the creeper
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
	 * @param creeper
	 *            the creeper
	 * @param fuse
	 *            the fuse
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
	 * @param creeper
	 *            the creeper
	 * @param radius
	 *            the radius
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
