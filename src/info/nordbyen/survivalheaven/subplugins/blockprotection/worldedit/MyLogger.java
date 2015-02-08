/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
