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
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;
import info.nordbyen.survivalheaven.api.regions.IRegionData;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Door;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * The Class RegionUpdater.
 */
@SuppressWarnings("deprecation")
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

		/**
		 * On damage.
		 *
		 * @param e
		 *            the e
		 */
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

		/**
		 * On interact.
		 *
		 * @param e
		 *            the e
		 */
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

		/**
		 * On monster spawn.
		 *
		 * @param e
		 *            the e
		 */
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

		/**
		 * On pv p.
		 *
		 * @param e
		 *            the e
		 */
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

		/**
		 * On quit.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler
		public void onQuit(final PlayerQuitEvent e) {
			playerRegions.remove(e.getPlayer().getUniqueId().toString());
		}

		/**
		 * On player door open.
		 *
		 * @param event
		 *            the event
		 */
		@EventHandler
		public void onPlayerDoorOpen(PlayerInteractEvent event) {
			Action action = event.getAction();
			final Block b = event.getClickedBlock();
			if ((action == Action.RIGHT_CLICK_BLOCK) || (action == Action.LEFT_CLICK_BLOCK)) {

				if( !SH.getManager().getRegionManager().getRegionAt( b.getLocation() ).isAuto_door() ) {
					return;
				}

				if(
						(b.getType() == Material.IRON_DOOR) || 
						(b.getType() == Material.WOODEN_DOOR) || 
						(b.getType() == Material.TRAP_DOOR) || 
						(b.getType() == Material.BIRCH_DOOR) || 
						(b.getType() == Material.BIRCH_DOOR_ITEM) || 
						(b.getType() == Material.SPRUCE_DOOR) || 
						(b.getType() == Material.SPRUCE_DOOR_ITEM) || 
						(b.getType() == Material.JUNGLE_DOOR) || 
						(b.getType() == Material.JUNGLE_DOOR_ITEM) || 
						(b.getType() == Material.ACACIA_DOOR) || 
						(b.getType() == Material.ACACIA_DOOR_ITEM) || 
						(b.getType() == Material.DARK_OAK_DOOR)) {
					BlockState blockState = (CraftBlockState) b.getState();
					if( blockState.getData() instanceof Door ) {
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						scheduler.scheduleSyncDelayedTask(SH.getPlugin(), new Runnable() {
							@Override
							public void run() {

								toggleDoor( b, false );

							}
						}, 20L * 10 );
					}
				}
			}
		}
	}

	/**
	 * Checks if is door.
	 *
	 * @param type
	 *            the type
	 * @return true, if is door
	 */
	public static boolean isDoor(Material type) {
		if(
				(type == Material.IRON_DOOR) || 
				(type == Material.WOODEN_DOOR) || 
				(type == Material.TRAP_DOOR) || 
				(type == Material.IRON_TRAPDOOR) || 
				(type == Material.BIRCH_DOOR) || 
				(type == Material.BIRCH_DOOR_ITEM) || 
				(type == Material.SPRUCE_DOOR) || 
				(type == Material.SPRUCE_DOOR_ITEM) || 
				(type == Material.JUNGLE_DOOR) || 
				(type == Material.JUNGLE_DOOR_ITEM) || 
				(type == Material.ACACIA_DOOR) || 
				(type == Material.ACACIA_DOOR_ITEM) || 
				(type == Material.DARK_OAK_DOOR)) {
			return true;
		}
		return false;
	}

	/**
	 * Toggle door.
	 *
	 * @param b
	 *            the b
	 * @param toggled
	 *            the toggled
	 */
	public void toggleDoor( Block b, boolean toggled ) {

		if( !isDoor( b.getType() ) ) {
			return;
		}

		if( !isDoor( b.getRelative( BlockFace.UP ).getType() ) ) {
			b = b.getRelative( BlockFace.DOWN );
		}

		Material type = b.getType();
		Door door = (Door) type.getNewData(b.getData());
		if (toggled != door.isOpen()) {
			door.setOpen(toggled);
			Block above = b.getRelative(BlockFace.UP);
			Block below = b.getRelative(BlockFace.DOWN);
			if (isDoor(above.getType())) {
				b.setData(door.getData(), true);
				door.setTopHalf(true);
				above.setData(door.getData(), true);
			} else if (isDoor(below.getType())) {
				door.setTopHalf(false);
				below.setData(door.getData(), true);
				door.setTopHalf(true);
				b.setData(door.getData(), true);
			}
			b.getWorld().playEffect(b.getLocation(), Effect.DOOR_TOGGLE, 0);
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

		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), -232, 0,
								-6071), "Nord-byen", 150, 100,
								false, false, false, true, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 145, 0,
								6234), "Sør-byen", 150, 100,
								false, false, false, true, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 6251, 0,
								757), "Øst-byen", 200, 100,
								false, false, false, true, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), -5774,
								0, 95), "Vest-byen", 150, 100,
								false, false, false, true, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 140, 0,
								89), "Spawn Sentrum", 230, 100,
								false, false, false, true, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 140, 0,
								89), "Spawn Utkant", 330, 95,
								false, false, true, false, false, true));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 140, 0,
								89), "Survival", 6000, 90,
								false, true, true, false, true, false));
		SH.getManager()
		.getRegionManager()
		.addRegion(
				RegionData.createRegion(
						new Location(Bukkit
								.getWorld("NyVerden"), 140, 0,
								89), "Villmark",
								Integer.MAX_VALUE, Integer.MIN_VALUE + 100,
								true, true, true, false, false, false));

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
					RegionData region = SH.getManager()
							.getRegionManager().getRegionAt(o.getLocation());
					if( region == null ) {
						region = new DefaultRegion();
					}
					if( playerRegions.get(o.getUniqueId().toString()) == null ) {
						playerRegions.put( o.getUniqueId().toString(), new DefaultRegion() );
					}
					if (!region.getName().equals( playerRegions.get(o.getUniqueId().toString()).getName())) {
						sendRegionName(o, region);
						SH.getManager().getRankManager().updateNames();
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
