/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.regions;

import org.bukkit.Location;

/**
 * The Class DefaultRegion.
 */
public class DefaultRegion extends RegionData {
	
	/**
	 * Instantiates a new default region.
	 */
	DefaultRegion() {
		super( null, "Unknown", 0, 0, true, true, true, false, false, false );
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#getCenter()
	 */
	@Override
	public Location getCenter() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#getName()
	 */
	@Override
	public String getName() {
		return "Unknown";
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#getRadius()
	 */
	@Override
	public double getRadius() {
		return Double.MAX_VALUE;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#getZValue()
	 */
	@Override
	public int getZValue() {
		return Integer.MIN_VALUE;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#isBreakable()
	 */
	@Override
	public boolean isBreakable() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#isMonsters()
	 */
	@Override
	public boolean isMonsters() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#isPvp()
	 */
	@Override
	public boolean isPvp() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.subplugins.regions.RegionData#containsLocation(org.bukkit.Location)
	 */
	@Override
	public boolean containsLocation(final Location loc) {
		return true;
	}
}	
