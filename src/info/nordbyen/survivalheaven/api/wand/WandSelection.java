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

/**
 * The Class WandSelection.
 */
public final class WandSelection {

	/** The block1. */
	private Block block1 = null;
	
	/** The block2. */
	private Block block2 = null;

	/**
	 * Instantiates a new wand selection.
	 */
	public WandSelection() {
	}

	/**
	 * Instantiates a new wand selection.
	 *
	 * @param block1
	 *            the block1
	 * @param block2
	 *            the block2
	 */
	public WandSelection(final Block block1, final Block block2) {
		this.setBlock1(block1);
		this.setBlock2(block2);
	}

	/**
	 * Gets the block1.
	 *
	 * @return the block1
	 */
	public Block getBlock1() {
		return block1;
	}

	/**
	 * Gets the block2.
	 *
	 * @return the block2
	 */
	public Block getBlock2() {
		return block2;
	}

	/**
	 * Sets the block1.
	 *
	 * @param block1
	 *            the new block1
	 */
	public void setBlock1(final Block block1) {
		this.block1 = block1;
	}

	/**
	 * Sets the block2.
	 *
	 * @param block2
	 *            the new block2
	 */
	public void setBlock2(final Block block2) {
		this.block2 = block2;
	}
}
