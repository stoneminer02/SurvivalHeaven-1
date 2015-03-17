/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.DenyPlayerMode;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The Class DenyPlayerMode.
 */
public class DenyPlayerMode extends SubPlugin {

	/**
	 * The listener interface for receiving denyPlayerMode events. The class
	 * that is interested in processing a denyPlayerMode event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addDenyPlayerModeListener<code> method. When
	 * the denyPlayerMode event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see DenyPlayerModeEvent
	 */
	class DenyPlayerModeListener implements Listener {

		/** The denymessage. */
		private final String denymessage = ChatColor.RED + "" + ChatColor.BOLD
				+ "Du er bannet og har ikke lov til dette!";

		/**
		 * On chat.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
		public void onChat(final AsyncPlayerChatEvent e) {
			if (!isDenied(e.getPlayer()))
				return;
			FancyMessages.sendActionBar(e.getPlayer(), denymessage);
			e.setCancelled(true);
			e.setMessage(null);
		}

		/**
		 * On command.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onCommand(final PlayerCommandPreprocessEvent e) {
			if (!isDenied(e.getPlayer()))
				return;
			FancyMessages.sendActionBar(e.getPlayer(), denymessage);
			e.setCancelled(true);
		}

		/**
		 * On entity damage.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onEntityDamage(final EntityDamageByEntityEvent e) {
			if (e.getDamager() instanceof Player) {
				final Player p = (Player) e.getDamager();
				if (!isDenied(p))
					return;
				FancyMessages.sendActionBar(p, denymessage);
				e.setCancelled(true);
			}
		}

		/**
		 * On interact.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onInteract(final PlayerInteractEvent e) {
			if (!isDenied(e.getPlayer()))
				return;
			FancyMessages.sendActionBar(e.getPlayer(), denymessage);
			e.setCancelled(true);
		}

		/**
		 * On place.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onPlace(final BlockPlaceEvent e) {
			if (!isDenied(e.getPlayer()))
				return;
			FancyMessages.sendActionBar(e.getPlayer(), denymessage);
			e.setCancelled(true);
		}

		/**
		 * On test entity damage.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
		public void onTestEntityDamage(final EntityDamageEvent e) {
			if (e.getEntity() instanceof Player) {
				final Player p = (Player) e.getEntity();
				if (!isDenied(p))
					return;
				e.setCancelled(true);
			}
		}
	}

	/**
	 * Instantiates a new deny player mode.
	 *
	 * @param name
	 *            the name
	 */
	public DenyPlayerMode(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		Bukkit.getPluginManager().registerEvents(new DenyPlayerModeListener(),
				getPlugin());
	}

	/**
	 * Checks if is denied.
	 *
	 * @param p
	 *            the p
	 * @return true, if is denied
	 */
	public boolean isDenied(final Player p) {
		final RankType rank = SH.getManager().getRankManager()
				.getRank(p.getUniqueId().toString());
		if (rank == RankType.BANNED)
			return true;
		// if ( PlayerDataManager.getPlayerData( p.getUniqueId().toString()
		// ).isBanned() )
		// return true; // TODO
		return false;
	}
}
