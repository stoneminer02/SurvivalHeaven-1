/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerData;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * The Class BlockManager.
 */
public class BlockManager implements IBlockManager {

	/**
	 * Instantiates a new block manager.
	 */
	public BlockManager() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager#getOwner
	 * (org.bukkit. block.Block)
	 */
	@Override
	public IPlayerData getBlockOwner(final Block b) {
		final BlockProtection bp = BlockProtection.getInstance();
		if (bp == null)
			return null;
		return bp.getWhoPlaced(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager#setBlockOwner
	 * (org.bukkit .block.Block, org.bukkit.entity.Player,
	 * info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType)
	 */
	@Override
	public void setBlockOwner(final Block b, final Player owner,
			final BlockPlacedType type) {
		final BlockProtection bp = BlockProtection.getInstance();
		if (bp == null)
			return;
		bp.setWhoPlaced(owner.getUniqueId().toString(), owner.getName(), b,
				type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager#setBlockOwner
	 * (org.bukkit .block.Block,
	 * info.nordbyen.survivalheaven.subplugins.playerdata.PlayerData,
	 * info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType)
	 */
	@Override
	public void setBlockOwner(final Block b, final PlayerData owner,
			final BlockPlacedType type) {
		final BlockProtection bp = BlockProtection.getInstance();
		if (bp == null)
			return;
		bp.setWhoPlaced(owner, b, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager#setBlockOwner
	 * (org.bukkit .block.Block, java.lang.String, java.lang.String,
	 * info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType)
	 */
	@Override
	public void setBlockOwner(final Block b, final String uuid,
			final String name, final BlockPlacedType type) {
		final BlockProtection bp = BlockProtection.getInstance();
		if (bp == null)
			return;
		bp.setWhoPlaced(uuid, name, b, type);
	}
}
