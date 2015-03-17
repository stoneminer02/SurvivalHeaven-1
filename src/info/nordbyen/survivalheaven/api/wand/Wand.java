/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Interface Wand.
 */
public interface Wand {

	/**
	 * Can create.
	 *
	 * @param itemStack
	 *            the item stack
	 * @param player
	 *            the player
	 * @return true, if successful
	 */
	public boolean canCreate(ItemStack itemStack, Player player);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * On left click.
	 *
	 * @param itemStack
	 *            the item stack
	 * @param player
	 *            the player
	 * @param bockedClick
	 *            the bocked click
	 * @param face
	 *            the face
	 */
	public void onLeftClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face);

	/**
	 * On right click.
	 *
	 * @param itemStack
	 *            the item stack
	 * @param player
	 *            the player
	 * @param bockedClick
	 *            the bocked click
	 * @param face
	 *            the face
	 */
	public void onRightClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face);
}
