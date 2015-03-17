/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import info.nordbyen.survivalheaven.SH;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * The Class BukkitHelperAPI.
 */
public class BukkitHelperAPI {

	/**
	 * Creates the book.
	 *
	 * @param title
	 *            the title
	 * @param owner
	 *            the owner
	 * @param pages
	 *            the pages
	 * @return the item stack
	 */
	public static ItemStack createBook(final String title, final String owner,
			final String... pages) {
		final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		final BookMeta bm = (BookMeta) book.getItemMeta();
		for (final String page : pages) {
			bm.addPage(page);
		}
		bm.setTitle(title);
		bm.setDisplayName(title);
		bm.setAuthor(owner);
		book.setItemMeta(bm);
		return book;
	}

	/**
	 * Gets the loc from player.
	 *
	 * @param p
	 *            the p
	 * @param dist
	 *            the dist
	 * @return the loc from player
	 */
	public static Location getLocFromPlayer(final Player p, final double dist) {
		final Location eyelocation = p.getEyeLocation();
		eyelocation.add(eyelocation.getDirection().multiply(dist));
		return eyelocation;
	}

	/**
	 * Gets the selected block.
	 *
	 * @param p
	 *            the p
	 * @return the selected block
	 */
	public static Block getSelectedBlock(final Player p) {
		@SuppressWarnings("deprecation")
		final Block b = p.getTargetBlock(null, 200);
		return b;
	}

	/**
	 * Shoot arrow.
	 *
	 * @param start
	 *            the start
	 * @param dir
	 *            the dir
	 */
	public static void shootArrow(final Location start, final Vector dir) {
		final Entity bullet = start.getWorld().spawnEntity(start,
				EntityType.ARROW);
		bullet.setVelocity(dir.multiply(4.0));
	}

	/**
	 * Update inventory later.
	 *
	 * @param p
	 *            the p
	 */
	public static void updateInventoryLater(final Player p) {
		new BukkitRunnable() {

			@Override
			public void run() {
				p.updateInventory();
			}
		}.runTaskLater(SH.getPlugin(), 1);
	}

	/**
	 * Gets the inventory.
	 *
	 * @param file
	 *            the file
	 * @param path
	 *            the path
	 * @return the inventory
	 */
	public Inventory getInventory(final FileConfiguration file,
			final String path) {
		if (file.contains(path)) {
			final Inventory inv = Bukkit.createInventory(
					(InventoryHolder) file.get(path + ".inventoryholder"),
					file.getInt(path + ".inventorysize"),
					file.getString(path + ".inventorytitle"));
			inv.setMaxStackSize(file.getInt(path + ".maxstacksize"));
			for (int i = 0; i < inv.getSize(); i++) {
				if (file.contains(path + ".contents." + i)) {
					inv.setItem(i, file.getItemStack(path + ".contents." + i));
				}
			}
			return inv;
		}
		return null;
	}

	/**
	 * Save inventory.
	 *
	 * @param inventory
	 *            the inventory
	 * @param file
	 *            the file
	 * @param path
	 *            the path
	 */
	public void saveInventory(final Inventory inventory,
			final FileConfiguration file, final String path) {
		int i = 0;
		for (final ItemStack im : inventory.getContents()) {
			file.set(path + ".contents." + i, im);
			i++;
		}
		file.set(path + ".maxstacksize", inventory.getMaxStackSize());
		file.set(path + ".inventorytitle", inventory.getTitle());
		file.set(path + ".inventorysize", inventory.getSize());
		file.set(path + ".inventoryholder", inventory.getHolder());
	}
}
