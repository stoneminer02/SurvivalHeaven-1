/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.regions.IRegionData;

import org.bukkit.Location;

/**
 * The Class RegionData.
 */
public class RegionData implements IRegionData {

	/**
	 * Creates the region.
	 *
	 * @param center
	 *            the center
	 * @param name
	 *            the name
	 * @param radius
	 *            the radius
	 * @param zVal
	 *            the z val
	 * @param pvp
	 *            the pvp
	 * @param breakable
	 *            the breakable
	 * @param monsters
	 *            the monsters
	 * @param invincible
	 *            the invincible
	 * @param bp
	 *            the bp
	 * @param auto_door
	 *            the auto_door
	 * @return the region data
	 */
	public static RegionData createRegion(final Location center,
			final String name, final double radius, final int zVal,
			final boolean pvp, final boolean breakable, final boolean monsters,
			final boolean invincible, final boolean bp, final boolean auto_door) {
		final RegionData region = new RegionData(center, name, radius, zVal,
				pvp, breakable, monsters, invincible, bp, auto_door);
		SH.getManager().getRegionManager().addRegion(region);
		return region;
	}

	/** The center. */
	private Location center;
	
	/** The name. */
	private final String name;
	
	/** The radius. */
	private double radius;
	
	/** The z val. */
	private final int zVal;
	
	/** The pvp. */
	private boolean pvp;
	
	/** The breakable. */
	private boolean breakable;
	
	/** The monsters. */
	private boolean monsters;
	
	/** The invincible. */
	private boolean invincible;
	
	/** The bp. */
	private boolean bp;
	
	/** The auto_door. */
	private boolean auto_door;

	/**
	 * Instantiates a new region data.
	 *
	 * @param center
	 *            the center
	 * @param name
	 *            the name
	 * @param radius
	 *            the radius
	 * @param zVal
	 *            the z val
	 * @param pvp
	 *            the pvp
	 * @param breakable
	 *            the breakable
	 * @param monsters
	 *            the monsters
	 * @param invincible
	 *            the invincible
	 * @param bp
	 *            the bp
	 * @param auto_door
	 *            the auto_door
	 */
	protected RegionData(final Location center, final String name,
			final double radius, final int zVal, final boolean pvp,
			final boolean breakable, final boolean monsters,
			final boolean invincible, final boolean bp, final boolean auto_door) {
		this.setCenter(center);
		this.name = name;
		this.setRadius(radius);
		this.zVal = zVal;
		this.setPvp(pvp);
		this.setBreakable(breakable);
		this.setMonsters(monsters);
		this.setInvincible(invincible);
		this.setBp(bp);
		this.setAuto_door(auto_door);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#containsLocation(org
	 * .bukkit.Location )
	 */
	@Override
	public boolean containsLocation(final Location loc) {
		final Location copy = loc.clone();
		copy.setY(0);
		if( center == null ) 
			return false;
		if( loc.getWorld() == null || center.getWorld() == null ) 
			return false;
		if (!loc.getWorld().getName().equals(center.getWorld().getName()))
			return false;
		if (copy.distance(center) > radius)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#getCenter()
	 */
	@Override
	public Location getCenter() {
		return center;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#getRadius()
	 */
	@Override
	public double getRadius() {
		return radius;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#getZValue()
	 */
	@Override
	public int getZValue() {
		return zVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#isBreakable()
	 */
	@Override
	public boolean isBreakable() {
		return breakable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#isInvincible()
	 */
	@Override
	public boolean isInvincible() {
		return invincible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#isMonsters()
	 */
	@Override
	public boolean isMonsters() {
		return monsters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#isPvp()
	 */
	@Override
	public boolean isPvp() {
		return pvp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#setBreakable(boolean
	 * )
	 */
	@Override
	public void setBreakable(final boolean breakable) {
		this.breakable = breakable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#setCenter(org.bukkit
	 * .Location)
	 */
	@Override
	public void setCenter(final Location center) {
		if( center == null )
			return;
		center.setY(0);
		this.center = center;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#setInvincible(boolean
	 * )
	 */
	@Override
	public void setInvincible(final boolean invincible) {
		this.invincible = invincible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#setMonsters(boolean)
	 */
	@Override
	public void setMonsters(final boolean monsters) {
		this.monsters = monsters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionData#setPvp(boolean)
	 */
	@Override
	public void setPvp(final boolean pvp) {
		this.pvp = pvp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionData#setRadius(double)
	 */
	@Override
	public void setRadius(final double radius) {
		this.radius = radius;
	}

	/**
	 * Checks if is bp.
	 *
	 * @return true, if is bp
	 */
	public boolean isBp() {
		return bp;
	}

	/**
	 * Sets the bp.
	 *
	 * @param bp
	 *            the new bp
	 */
	public void setBp(boolean bp) {
		this.bp = bp;
	}

	/**
	 * Checks if is auto_door.
	 *
	 * @return true, if is auto_door
	 */
	public boolean isAuto_door() {
		return auto_door;
	}

	/**
	 * Sets the auto_door.
	 *
	 * @param auto_door
	 *            the new auto_door
	 */
	public void setAuto_door(boolean auto_door) {
		this.auto_door = auto_door;
	}
}
