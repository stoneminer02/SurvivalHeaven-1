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

package info.nordbyen.survivalheaven.api.regions;

import info.nordbyen.survivalheaven.SH;

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
	 * @return the region data
	 */
	public static RegionData createRegion(final Location center,
			final String name, final double radius, final int zVal,
			final boolean pvp, final boolean breakable, final boolean monsters,
			final boolean invincible) {
		final RegionData region = new RegionData(center, name, radius, zVal,
				pvp, breakable, monsters, invincible);
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
	 */
	private RegionData(final Location center, final String name,
			final double radius, final int zVal, final boolean pvp,
			final boolean breakable, final boolean monsters,
			final boolean invincible) {
		this.setCenter(center);
		this.name = name;
		this.setRadius(radius);
		this.zVal = zVal;
		this.setPvp(pvp);
		this.setBreakable(breakable);
		this.setMonsters(monsters);
		this.setInvincible(invincible);
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
}
