/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.shop;

import info.nordbyen.survivalheaven.SH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The listener interface for receiving shop events. The class that is
 * interested in processing a shop event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addShopListener<code> method. When
 * the shop event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ShopEvent
 */
public class ShopListener implements Listener {

	/** The hash. */
	public HashMap<String, ArrayList<Long>> hash = new HashMap<String, ArrayList<Long>>();

	/** The timer is running. */
	public boolean timerIsRunning = false;

	/**
	 * Mine.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void mine(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.GOLD_ORE
				|| e.getBlock().getType() == Material.DIAMOND_ORE
				|| e.getBlock().getType() == Material.IRON_ORE
				|| e.getBlock().getType() == Material.REDSTONE_ORE) {
			ArrayList<Long> blocktimelist = hash.get(e.getPlayer().getName());
			if (blocktimelist == null) {
				blocktimelist = new ArrayList<Long>();
				hash.put(e.getPlayer().getName(), blocktimelist);
			}
			blocktimelist.add(System.currentTimeMillis());
			Bukkit.broadcastMessage(ChatColor.GOLD + "DEBUG: "
					+ e.getPlayer().getName() + ": " + blocktimelist.size());
		}
		return;
	}

	/**
	 * On click.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		if (e.getPlayer().getName().equalsIgnoreCase("l0lkj")) {
			// e.getPlayer().sendMessage( "Åpner shop" );
			// e.getPlayer().openInventory( Shop.getShopInventory( ShopType.WOOD
			// ) );
		}
	}

	/**
	 * On inventory.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onInventory(final InventoryClickEvent e) {
		final Inventory inv = e.getClickedInventory();
		@SuppressWarnings("unused")
		final ItemStack item = inv.getItem(e.getSlot());
	}

	/**
	 * On join.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		hash.put(e.getPlayer().getName(), new ArrayList<Long>());
	}

	/**
	 * On leave.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		hash.remove(e.getPlayer().getName());
	}

	/**
	 * Start timer.
	 */
	public void startTimer() {
		Bukkit.broadcastMessage(ChatColor.GREEN + "PRØVER");
		if (timerIsRunning)
			return;
		timerIsRunning = true;
		Bukkit.broadcastMessage(ChatColor.GREEN + "STARTER");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SH.getPlugin(),
				new Runnable() {

					@Override
					public void run() {
						Bukkit.broadcastMessage(ChatColor.GREEN + "KJØRER");
						for (Entry<String, ArrayList<Long>> entry : hash
								.entrySet()) {
							Bukkit.broadcastMessage(ChatColor.GREEN
									+ "LOOPER: " + entry.getKey());
							String name = entry.getKey();
							ArrayList<Long> blocktimes = entry.getValue();
							ArrayList<Long> remove = new ArrayList<Long>();
							Bukkit.broadcastMessage(ChatColor.GREEN
									+ "Antall: " + entry.getValue().size());
							for (long time : blocktimes) {
								if (time < (System.currentTimeMillis() - 30 * 1000)) {
									remove.add(time);
								}
							}
							for (long rem : remove)
								blocktimes.remove(rem);
							Bukkit.broadcastMessage(ChatColor.GREEN
									+ "Antall etter: "
									+ entry.getValue().size());

							if (blocktimes.size() >= 1) {
								Bukkit.broadcast(ChatColor.YELLOW + name
										+ ChatColor.GRAY + " har minet "
										+ ChatColor.YELLOW + blocktimes.size()
										+ ChatColor.GRAY
										+ " de siste 30 sekundene",
										"buildit.admin");
							}
						}
					}

				}, 20 * 30, 20 * 30); // 30 sec
	}
}
