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

package info.nordbyen.survivalheaven.subplugins.regions;

import info.nordbyen.survivalheaven.api.regions.IRegionManager;

import java.util.ArrayList;

import org.bukkit.Location;

/**
 * The Class RegionManager.
 */
public class RegionManager implements IRegionManager {

	/** The regions. */
	private final ArrayList<RegionData> regions = new ArrayList<RegionData>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionManager#addRegion(info.
	 * nordbyen.survivalheaven.api. regions.RegionData)
	 */
	@Override
	public void addRegion(final RegionData region) {
		if (!this.regions.contains(region)) {
			this.regions.add(region);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionManager#getRegionAt(org
	 * .bukkit.Location)
	 */
	@Override
	public RegionData getRegionAt(final Location loc) {
		if (getRegionsAt(loc).length <= 0)
			return null;
		return getRegionsAt(loc)[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.regions.IRegionManager#getRegions()
	 */
	@Override
	public RegionData[] getRegions() {
		final RegionData[] res = new RegionData[regions.size()];
		int i = 0;
		for (final RegionData region : regions) {
			res[i++] = region;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionManager#getRegionsAt(org
	 * .bukkit.Location )
	 */
	@Override
	public RegionData[] getRegionsAt(final Location loc) {
		final ArrayList<RegionData> res = new ArrayList<RegionData>();
		for (final RegionData region : getRegions()) {
			if (region.containsLocation(loc)) {
				res.add(region);
			}
		}
		final int num = res.size();
		final RegionData[] result = new RegionData[num];
		for (int i = 0; i < num; i++) {
			RegionData topRegion = null;
			final ArrayList<RegionData> res_removed = new ArrayList<RegionData>();
			for (final RegionData region : res) {
				if (topRegion == null) {
					topRegion = region;
					res_removed.add(topRegion);
					continue;
				}
				if (region.getZValue() > topRegion.getZValue()) {
					topRegion = region;
					res_removed.add(topRegion);
					continue;
				}
			}
			for (final RegionData region : res_removed) {
				res.remove(region);
			}
			result[i] = topRegion;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.regions.IRegionManager#removeRegion(info
	 * .nordbyen.survivalheaven.api .regions.RegionData)
	 */
	@Override
	public void removeRegion(final RegionData region) {
		if (this.regions.contains(region)) {
			this.regions.remove(region);
		}
	}
}
