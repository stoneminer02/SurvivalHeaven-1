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

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;
import info.nordbyen.survivalheaven.api.regions.IRegionData;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.api.util.FancyMessages;
import info.nordbyen.survivalheaven.subplugins.regions.teleports.RegionTeleportCommand;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * The Class RegionUpdater.
 */
public class RegionUpdater extends SubPlugin {

	/**
	 * The listener interface for receiving regionUpdater events. The class that
	 * is interested in processing a regionUpdater event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addRegionUpdaterListener<code> method. When
	 * the regionUpdater event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see RegionUpdaterEvent
	 */
	public class RegionUpdaterListener implements Listener {

		@EventHandler
		public void onDamage(final EntityDamageEvent e) {
			if (!(e.getEntity() instanceof Player))
				return;
			Player p = (Player) e.getEntity();
			IRegionData rd = SH.getManager().getRegionManager()
					.getRegionAt(p.getLocation());
			if (rd == null)
				return;
			if (!rd.isInvincible())
				return;
			e.setCancelled(true);
		}

		@EventHandler
		public void onInteract(final PlayerInteractEvent e) {
			if (e.getClickedBlock() == null)
				return;
			IRegionData rd = SH.getManager().getRegionManager()
					.getRegionAt(e.getClickedBlock().getLocation());
			if (rd == null)
				return;
			if (rd.isBreakable())
				return;
			IRankManager rm = SH.getManager().getRankManager();
			RankType rank = rm.getRank(e.getPlayer().getUniqueId().toString());
			if (rank != RankType.ADMINISTRATOR && rank != RankType.MODERATOR) {
				e.setCancelled(true);
			}
		}

		@EventHandler
		public void onMonsterSpawn(CreatureSpawnEvent e) {
			if (e.getEntityType() == EntityType.BLAZE
					|| e.getEntityType() == EntityType.ZOMBIE
					|| e.getEntityType() == EntityType.SKELETON
					|| e.getEntityType() == EntityType.SLIME
					|| e.getEntityType() == EntityType.SPIDER
					|| e.getEntityType() == EntityType.CREEPER
					|| e.getEntityType() == EntityType.ENDERMAN
					|| e.getEntityType() == EntityType.GHAST
					|| e.getEntityType() == EntityType.WITHER
					|| e.getEntityType() == EntityType.GUARDIAN) {
				IRegionData rd = SH.getManager().getRegionManager()
						.getRegionAt(e.getLocation());
				if (rd == null)
					return;
				if (rd.isMonsters())
					return;
				e.setCancelled(true);
			}
		}

		@EventHandler
		public void onPvP(final EntityDamageByEntityEvent e) {
			if (!(e.getEntity() instanceof Player))
				return;
			if (!(e.getDamager() instanceof Player))
				return;
			Player s = (Player) e.getEntity();
			Player o = (Player) e.getDamager();
			IRegionData rd1 = SH.getManager().getRegionManager()
					.getRegionAt(o.getLocation());
			IRegionData rd2 = SH.getManager().getRegionManager()
					.getRegionAt(s.getLocation());
			if (rd1 == null && rd2 == null)
				return;
			if (rd1.isPvp() && rd2.isPvp())
				return;
			e.setCancelled(true);
			FancyMessages.sendActionBar(s, ChatColor.RED
					+ "PvP er av i denne regionen");
		}

		@EventHandler
		public void onQuit(final PlayerQuitEvent e) {
			playerRegions.remove(e.getPlayer().getUniqueId().toString());
		}
	}

	/** The player regions. */
	private final HashMap<String, RegionData> playerRegions = new HashMap<String, RegionData>();

	/**
	 * Instantiates a new region updater.
	 * 
	 * @param name
	 *            the name
	 */
	public RegionUpdater(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	protected void disable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	protected void enable() {
		repeatingTask();
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(getPlugin(), new Runnable() {

			@Override
			public void run() {
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), -232, 0,
												-6071), "Nord-byen", 150, 100,
										false, false, false, true));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 145, 0,
												6234), "Sør-byen", 150, 100,
										false, false, false, true));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 6251, 0,
												757), "Øst-byen", 200, 100,
										false, false, false, true));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), -5774,
												0, 95), "Vest-byen", 150, 100,
										false, false, false, true));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 140, 0,
												89), "Spawn Sentrum", 230, 100,
										false, false, false, true));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 140, 0,
												89), "Spawn Utkant", 330, 95,
										false, false, true, false));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 140, 0,
												89), "Survival", 6000, 90,
										false, true, true, false));
				SH.getManager()
						.getRegionManager()
						.addRegion(
								RegionData.createRegion(
										new Location(Bukkit
												.getWorld("NyVerden"), 140, 0,
												89), "Villmark",
										Integer.MAX_VALUE, Integer.MIN_VALUE,
										true, true, true, false));
			}
		}, 1L);
		Bukkit.getPluginManager().registerEvents(new RegionUpdaterListener(),
				getPlugin());
		new RegionTeleportCommand();
	}

	/**
	 * Repeating task.
	 */
	private void repeatingTask() {
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(getPlugin(), new Runnable() {

			@Override
			public void run() {
				for (final Player o : Bukkit.getOnlinePlayers()) {
					final RegionData region = SH.getManager()
							.getRegionManager().getRegionAt(o.getLocation());
					if (region != playerRegions.get(o.getUniqueId().toString())) {
						sendRegionName(o, region);
					}
					playerRegions.put(o.getUniqueId().toString(), region);
				}
			}
		}, 1L, 1L);
	}

	/**
	 * Send region name.
	 * 
	 * @param p
	 *            the p
	 * @param region
	 *            the region
	 */
	public void sendRegionName(final Player p, final RegionData region) {
		SH.getManager().getRankManager().updateNames();
		if (region == null)
			return;
		// FancyMessages.sendActionBar(p, ChatColor.DARK_GREEN + ""
		// + ChatColor.BOLD + "------[ " + region.getName() + " ]------");
		boolean pvp = region.isPvp();
		FancyMessages.sendTitle(p, 20, 50, 20, ChatColor.GREEN + "---[ "
				+ region.getName() + " ]---", pvp ? ChatColor.RED
				+ "Her er det PvP!" : ChatColor.BLUE
				+ "Her er du trygg fra andre spillere");
	}
}
