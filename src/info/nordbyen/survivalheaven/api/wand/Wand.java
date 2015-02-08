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
