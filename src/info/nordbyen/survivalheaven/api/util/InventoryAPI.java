/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The Class InventoryAPI.
 */
public class InventoryAPI {

	/**
	 * Adds the.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 */
	public void add(final Inventory inventory, final Material material) {
		if (!isFull(inventory)) {
			inventory.addItem(new ItemStack[] { new ItemStack(material, 1) });
		}
	}

	/**
	 * Adds the.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 * @param amount
	 *            the amount
	 */
	public void add(final Inventory inventory, final Material material,
			final int amount) {
		int count = 0;
		final ItemStack items = new ItemStack(material, 1);
		do {
			inventory.addItem(new ItemStack[] { items });
			count++;
		} while ((!isFull(inventory)) && (count < amount));
	}

	/**
	 * Adds the.
	 *
	 * @param player
	 *            the player
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 * @param amount
	 *            the amount
	 */
	public void add(final Player player, final Inventory inventory,
			final Material material, final int amount) {
		int count = 0;
		final ItemStack item = new ItemStack(material, 1);
		do {
			if (!isFull(inventory)) {
				inventory.addItem(new ItemStack[] { item });
				count++;
			} else {
				player.getWorld().dropItemNaturally(player.getLocation(), item);
				count++;
			}
		} while (count < amount);
	}

	/**
	 * Adds the stack.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 */
	public void addStack(final Inventory inventory, final Material material) {
		if (hasEmptySlot(inventory)) {
			ItemStack item = new ItemStack(material);
			final int max = item.getMaxStackSize();
			item = new ItemStack(material, max);
			if (max != -1) {
				inventory.addItem(new ItemStack[] { item });
			}
		}
	}

	/**
	 * Clear.
	 *
	 * @param inventory
	 *            the inventory
	 */
	public void clear(final Inventory inventory) {
		inventory.clear();
	}

	/**
	 * Contains.
	 *
	 * @param inventory
	 *            the inventory
	 * @param itemstack
	 *            the itemstack
	 * @return true, if successful
	 */
	public boolean contains(final Inventory inventory, final ItemStack itemstack) {
		if (inventory.contains(itemstack))
			return true;
		return false;
	}

	/**
	 * Contains.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 * @return true, if successful
	 */
	public boolean contains(final Inventory inventory, final Material material) {
		if (inventory.contains(material))
			return true;
		return false;
	}

	/**
	 * Contains.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 * @param amount
	 *            the amount
	 * @return true, if successful
	 */
	public boolean contains(final Inventory inventory, final Material material,
			final int amount) {
		if (inventory.contains(material, amount))
			return true;
		return false;
	}

	/**
	 * Copy.
	 *
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 */
	public void copy(final Inventory source, final Inventory target) {
		final ItemStack[] items = source.getContents();
		target.setContents(items);
	}

	/**
	 * Copy.
	 *
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 */
	public void copy(final Inventory source, final Player target) {
		final Inventory targetInv = target.getInventory();
		copy(source, targetInv);
	}

	/**
	 * Copy.
	 *
	 * @param player
	 *            the player
	 * @param target
	 *            the target
	 */
	public void copy(final Player player, final Player target) {
		final Inventory sourceInv = player.getInventory();
		final Inventory targetInv = target.getInventory();
		copy(sourceInv, targetInv);
	}

	/**
	 * Gets the name.
	 *
	 * @param inventory
	 *            the inventory
	 * @return the name
	 */
	public String getName(final Inventory inventory) {
		return inventory.getName();
	}

	/**
	 * Gets the title.
	 *
	 * @param inventory
	 *            the inventory
	 * @return the title
	 */
	public String getTitle(final Inventory inventory) {
		return inventory.getTitle();
	}

	/**
	 * Checks for empty slot.
	 *
	 * @param inventory
	 *            the inventory
	 * @return true, if successful
	 */
	public boolean hasEmptySlot(final Inventory inventory) {
		for (final ItemStack items : inventory.getContents()) {
			if (items == null)
				return false;
		}
		return true;
	}

	/**
	 * Checks if is full.
	 *
	 * @param inventory
	 *            the inventory
	 * @return true, if is full
	 */
	boolean isFull(final Inventory inventory) {
		if (inventory.firstEmpty() != -1)
			return false;
		return true;
	}

	/**
	 * Removes the.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 * @param amount
	 *            the amount
	 */
	public void remove(final Inventory inventory, final Material material,
			int amount) {
		if (contains(inventory, material, amount)) {
			do {
				inventory.remove(new ItemStack(material, 1));
				amount--;
			} while (amount > 0);
		}
	}

	/**
	 * Removes the all.
	 *
	 * @param inventory
	 *            the inventory
	 * @param material
	 *            the material
	 */
	public void removeAll(final Inventory inventory, final Material material) {
		if (contains(inventory, material)) {
			inventory.remove(material);
		}
	}
}
