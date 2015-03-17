/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

/**
 * The Class FirstEncounter.
 */
public class FirstEncounter {

	/**
	 * Adds the waiting player.
	 *
	 * @param uuid
	 *            the uuid
	 */
	public static void addWaitingPlayer(final String uuid) {
		waiting.add(uuid);
	}

	/**
	 * Gets the waiting players.
	 *
	 * @return the waiting players
	 */
	public static List<String> getWaitingPlayers() {
		return waiting;
	}

	/**
	 * Checks if is inside.
	 *
	 * @param location
	 *            the location
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return true, if is inside
	 */
	public static boolean isInside(final Location location, final Location min,
			final Location max) {
		final double xmin = Math.min(min.getX(), max.getX());
		final double xmax = Math.max(min.getX(), max.getX());
		final double ymin = Math.min(min.getY(), max.getY());
		final double ymax = Math.max(min.getY(), max.getY());
		final double zmin = Math.min(min.getZ(), max.getZ());
		final double zmax = Math.max(min.getZ(), max.getZ());
		if ((location.getBlockX() <= xmax) && (location.getBlockX() >= xmin)) {
			if ((location.getBlockY() <= ymax)
					&& (location.getBlockY() >= ymin)) {
				if ((location.getBlockZ() <= zmax)
						&& (location.getBlockZ() >= zmin))
					return true;
			} else
				return false;
		}
		return false;
	}

	/**
	 * Removes the waiting player.
	 *
	 * @param uuid
	 *            the uuid
	 */
	public static void removeWaitingPlayer(final String uuid) {
		waiting.remove(uuid);
	}

	/** The Constant waiting. */
	private static final List<String> waiting = new ArrayList<String>();
}
