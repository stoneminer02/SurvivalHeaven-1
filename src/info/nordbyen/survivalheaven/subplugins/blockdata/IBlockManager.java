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
	public IPlayerData getOwner(Block b);

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
