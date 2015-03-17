/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.regions;

import info.nordbyen.survivalheaven.subplugins.regions.RegionData;

import org.bukkit.Location;

/**
 * The Interface IRegionManager.
 */
public interface IRegionManager {

	/**
	 * Adds the region.
	 *
	 * @param region
	 *            the region
	 */
	public void addRegion(final RegionData region);

	/**
	 * Gets the region at.
	 *
	 * @param loc
	 *            the loc
	 * @return the region at
	 */
	public RegionData getRegionAt(final Location loc);

	/**
	 * Gets the regions.
	 *
	 * @return the regions
	 */
	public RegionData[] getRegions();

	/**
	 * Gets the regions at.
	 *
	 * @param loc
	 *            the loc
	 * @return the regions at
	 */
	public RegionData[] getRegionsAt(final Location loc);

	/**
	 * Removes the region.
	 *
	 * @param region
	 *            the region
	 */
	public void removeRegion(final RegionData region);
}
