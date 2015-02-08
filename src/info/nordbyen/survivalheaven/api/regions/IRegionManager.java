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
