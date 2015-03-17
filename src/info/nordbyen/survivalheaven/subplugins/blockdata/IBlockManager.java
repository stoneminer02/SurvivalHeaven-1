/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.blockdata;

import info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerData;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * The Interface IBlockManager.
 */
public interface IBlockManager {

	/**
	 * Gets the owner.
	 *
	 * @param b
	 *            the b
	 * @return the owner
	 */
	public IPlayerData getBlockOwner(Block b);

	/**
	 * Sets the block owner.
	 *
	 * @param b
	 *            the b
	 * @param owner
	 *            the owner
	 * @param type
	 *            the type
	 */
	public void setBlockOwner(Block b, Player owner, BlockPlacedType type);

	/**
	 * Sets the block owner.
	 *
	 * @param b
	 *            the b
	 * @param owner
	 *            the owner
	 * @param type
	 *            the type
	 */
	public void setBlockOwner(Block b, PlayerData owner, BlockPlacedType type);

	/**
	 * Sets the block owner.
	 *
	 * @param b
	 *            the b
	 * @param uuid
	 *            the uuid
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 */
	public void setBlockOwner(Block b, String uuid, String name,
			BlockPlacedType type);
}
