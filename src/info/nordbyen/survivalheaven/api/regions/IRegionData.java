/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.regions;

import org.bukkit.Location;

/**
 * The Interface IRegionData.
 */
public interface IRegionData {

	/**
	 * Contains location.
	 *
	 * @param loc
	 *            the loc
	 * @return true, if successful
	 */
	public boolean containsLocation(final Location loc);

	/**
	 * Gets the center.
	 *
	 * @return the center
	 */
	public Location getCenter();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public double getRadius();

	/**
	 * Gets the z value.
	 *
	 * @return the z value
	 */
	public int getZValue();

	/**
	 * Checks if is breakable.
	 *
	 * @return true, if is breakable
	 */
	public boolean isBreakable();

	/**
	 * Checks if is invincible.
	 *
	 * @return true, if is invincible
	 */
	public boolean isInvincible();

	/**
	 * Checks if is monsters.
	 *
	 * @return true, if is monsters
	 */
	public boolean isMonsters();

	/**
	 * Checks if is pvp.
	 *
	 * @return true, if is pvp
	 */
	public boolean isPvp();

	/**
	 * Sets the breakable.
	 *
	 * @param breakable
	 *            the new breakable
	 */
	public void setBreakable(final boolean breakable);

	/**
	 * Sets the center.
	 *
	 * @param center
	 *            the new center
	 */
	public void setCenter(final Location center);

	/**
	 * Sets the invincible.
	 *
	 * @param invincible
	 *            the new invincible
	 */
	public void setInvincible(final boolean invincible);

	/**
	 * Sets the monsters.
	 *
	 * @param monsters
	 *            the new monsters
	 */
	public void setMonsters(final boolean monsters);

	/**
	 * Sets the pvp.
	 *
	 * @param pvp
	 *            the new pvp
	 */
	public void setPvp(final boolean pvp);

	/**
	 * Sets the radius.
	 *
	 * @param radius
	 *            the new radius
	 */
	public void setRadius(final double radius);
}
