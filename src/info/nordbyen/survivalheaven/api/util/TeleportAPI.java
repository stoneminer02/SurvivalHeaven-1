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
