/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import org.bukkit.Achievement;
import org.bukkit.entity.Player;

/**
 * The Class PlayerAPI.
 */
public class PlayerAPI {

	/**
	 * Adds the health.
	 *
	 * @param player
	 *            the player
	 * @param amount
	 *            the amount
	 * @return true, if successful
	 */
	public boolean addHealth(final Player player, final int amount) {
		final double health = player.getHealth();
		if (health == 20.0D) {
			player.setHealth(20.0D);
			return true;
		}
		if (health > (20 - amount)) {
			player.setHealth(20.0D);
			return true;
		}
		if (health < (20 - amount)) {
			player.setHealth(health + amount);
			return true;
		}
		return false;
	}

	/**
	 * Award.
	 *
	 * @param player
	 *            the player
	 * @param achievement
	 *            the achievement
	 */
	public void award(final Player player, final Achievement achievement) {
		player.awardAchievement(achievement);
	}

	/**
	 * Burn.
	 *
	 * @param player
	 *            the player
	 */
	public void burn(final Player player) {
		player.setFireTicks(20);
	}

	/**
	 * Burn.
	 *
	 * @param player
	 *            the player
	 * @param ticks
	 *            the ticks
	 */
	public void burn(final Player player, final int ticks) {
		player.setFireTicks(ticks);
	}

	/**
	 * Extinguish.
	 *
	 * @param player
	 *            the player
	 */
	public void extinguish(final Player player) {
		player.setFireTicks(0);
	}

	/**
	 * Kill.
	 *
	 * @param player
	 *            the player
	 */
	public void kill(final Player player) {
		player.setHealth(0.0D);
	}

	/**
	 * Removes the health.
	 *
	 * @param player
	 *            the player
	 * @param amount
	 *            the amount
	 * @return true, if successful
	 */
	public boolean removeHealth(final Player player, final int amount) {
		final double health = player.getHealth();
		if (health > (health - amount)) {
			player.setHealth(health - amount);
			return true;
		}
		if (health < (health - amount)) {
			player.setHealth(0.0D);
			return true;
		}
		return false;
	}
}
