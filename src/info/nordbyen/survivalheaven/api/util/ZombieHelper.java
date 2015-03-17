/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
	 * @param zombie
	 *            the zombie
	 * @return true, if is baby
	 */
	public static boolean isBaby(final Zombie zombie) {
		final EntityZombie ent = ((CraftZombie) zombie).getHandle();
		return ent.isBaby();
	}

	/**
	 * Checks if is villager.
	 *
	 * @param zombie
	 *            the zombie
	 * @return true, if is villager
	 */
	public static boolean isVillager(final Zombie zombie) {
		final EntityZombie ent = ((CraftZombie) zombie).getHandle();
		return ent.isVillager();
	}

	/**
	 * Sets the baby.
	 *
	 * @param zombie
	 *            the zombie
	 * @param value
	 *            the value
	 */
	public static void setBaby(final Zombie zombie, final boolean value) {
		final EntityZombie ent = ((CraftZombie) zombie).getHandle();
		ent.setBaby(value);
	}

	/**
	 * Sets the villager.
	 *
	 * @param zombie
	 *            the zombie
	 * @param value
	 *            the value
	 */
	public static void setVillager(final Zombie zombie, final boolean value) {
		final EntityZombie ent = ((CraftZombie) zombie).getHandle();
		ent.setVillager(value);
	}
}
