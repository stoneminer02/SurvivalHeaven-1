/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * The Class TeleportAPI.
 */
public class TeleportAPI {

	/**
	 * New location.
	 *
	 * @param target
	 *            the target
	 * @return the location
	 */
	public Location newLocation(final Player target) {
		final Location location = target.getLocation();
		return location;
	}

	/**
	 * New location.
	 *
	 * @param world
	 *            the world
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 * @return the location
	 */
	public Location newLocation(final World world, final double x,
			final double y, final double z) {
		final Location location = new Location(world, x, y, z);
		return location;
	}

	/**
	 * New location.
	 *
	 * @param world
	 *            the world
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 * @param pitch
	 *            the pitch
	 * @param yaw
	 *            the yaw
	 * @return the location
	 */
	public Location newLocation(final World world, final double x,
			final double y, final double z, final float pitch, final float yaw) {
		final Location location = new Location(world, x, y, z, pitch, yaw);
		return location;
	}

	/**
	 * Teleport.
	 *
	 * @param player
	 *            the player
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 */
	public void teleport(final Player player, final double x, final double y,
			final double z) {
		final Location location = new Location(player.getWorld(), x, y, z);
		player.teleport(location);
	}

	/**
	 * Teleport.
	 *
	 * @param player
	 *            the player
	 * @param location
	 *            the location
	 */
	public void teleport(final Player player, final Location location) {
		player.teleport(location);
	}

	/**
	 * Teleport.
	 *
	 * @param player
	 *            the player
	 * @param target
	 *            the target
	 */
	public void teleport(final Player player, final Player target) {
		player.teleport(target);
	}

	/**
	 * Teleport.
	 *
	 * @param player
	 *            the player
	 * @param world
	 *            the world
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 */
	public void teleport(final Player player, final World world,
			final double x, final double y, final double z) {
		final Location location = new Location(world, x, y, z);
		player.teleport(location);
	}

	/**
	 * Teleport.
	 *
	 * @param player
	 *            the player
	 * @param world
	 *            the world
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 * @param pitch
	 *            the pitch
	 * @param yaw
	 *            the yaw
	 */
	public void teleport(final Player player, final World world,
			final double x, final double y, final double z, final float pitch,
			final float yaw) {
		final Location location = new Location(world, x, y, z, pitch, yaw);
		player.teleport(location);
	}

	/**
	 * Teleport all.
	 *
	 * @param location
	 *            the location
	 */
	public void teleportAll(final Location location) {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(location);
		}
	}

	/**
	 * Teleport all.
	 *
	 * @param players
	 *            the players
	 * @param location
	 *            the location
	 */
	public void teleportAll(final Player[] players, final Location location) {
		for (final Player player : players)
			if (player.isOnline()) {
				player.teleport(location);
			}
	}

	/**
	 * Teleport all.
	 *
	 * @param players
	 *            the players
	 * @param location
	 *            the location
	 */
	public void teleportAll(final String[] players, final Location location) {
		for (final String target : players) {
			final Player player = Bukkit.getPlayer(target);
			if (player.isOnline()) {
				player.teleport(location);
			}
		}
	}
}
