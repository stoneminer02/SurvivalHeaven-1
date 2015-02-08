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

package info.nordbyen.survivalheaven.api.wand;

import org.bukkit.block.Block;

// TODO: Auto-generated Javadoc
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
     * @param block1 the block1
     * @param block2 the block2
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
     * @param block1 the new block1
     */
    public void setBlock1(final Block block1) {
        this.block1 = block1;
    }

    /**
     * Sets the block2.
     * 
     * @param block2 the new block2
     */
    public void setBlock2(final Block block2) {
        this.block2 = block2;
    }
}
