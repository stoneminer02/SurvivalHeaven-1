/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Interface IWandManager.
 */
public interface IWandManager {

	/**
	 * Adds the.
	 *
	 * @param wand
	 *            the wand
	 */
	void add(Wand wand);

	/**
	 * Creates the wand.
	 *
	 * @param target
	 *            the target
	 * @param wand
	 *            the wand
	 * @param player
	 *            the player
	 * @return true, if successful
	 */
	boolean createWand(ItemStack target, Wand wand, Player player);

	/**
	 * Gets the.
	 *
	 * @param id
	 *            the id
	 * @return the wand
	 */
	Wand get(String id);

	/**
	 * Checks if is wand.
	 *
	 * @param itemStack
	 *            the item stack
	 * @return true, if is wand
	 */
	boolean isWand(ItemStack itemStack);

	/**
	 * Search.
	 *
	 * @param itemStack
	 *            the item stack
	 * @return the wand
	 */
	Wand search(ItemStack itemStack);
}
