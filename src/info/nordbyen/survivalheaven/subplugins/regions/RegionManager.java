/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
			return new DefaultRegion();
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
