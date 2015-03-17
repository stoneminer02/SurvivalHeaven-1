/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit;

import org.bukkit.Material;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.logging.AbstractLoggingExtent;

/**
 * The Class MyLogger.
 */
public class MyLogger extends AbstractLoggingExtent {

	/** The actor. */
	@SuppressWarnings("unused")
	private final Actor actor;
	
	/** The world. */
	@SuppressWarnings("unused")
	private final com.sk89q.worldedit.world.World world;

	/**
	 * Instantiates a new my logger.
	 *
	 * @param actor
	 *            the actor
	 * @param extent
	 *            the extent
	 * @param world
	 *            the world
	 */
	public MyLogger(final Actor actor, final Extent extent,
			final com.sk89q.worldedit.world.World world) {
		super(extent);
		this.actor = actor;
		this.world = world;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sk89q.worldedit.extent.logging.AbstractLoggingExtent#onBlockChange
	 * (com.sk89q.worldedit.Vector, com.sk89q.worldedit.blocks.BaseBlock)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onBlockChange(final Vector position, final BaseBlock newBlock) {
		if (!Material.getMaterial(newBlock.getType()).isSolid())
			return;
		// final World world = Bukkit.getWorld(this.world.getName());
		// final Player p = Bukkit.getPlayer(actor.getUniqueId());
		// if (p != null) {
		// SH.getBlockManager().setBlockOwner(world.getBlockAt((int)
		// position.getX(), (int) position.getY(), (int) position.getZ()), p,
		// BlockPlacedType.WORLDEDIT); // TODO
		// } TODO
	}
}
