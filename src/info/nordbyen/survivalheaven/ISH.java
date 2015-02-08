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

package info.nordbyen.survivalheaven;

import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager;
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager;
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.regions.IRegionManager;
import info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager;
import info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager;
import info.nordbyen.survivalheaven.api.wand.IWandManager;
import info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager;

import org.bukkit.Location;

/**
 * The Interface ISH.
 * 
 * @author l0lkj
 */
public interface ISH {

	/**
	 * Debug.
	 * 
	 * @param strings
	 *            the strings
	 */
	void debug(final Object... strings);

	/**
	 * Gets the senter.
	 * 
	 * @return the senter
	 */
	Location getSenter();

	/**
	 * Gets the anno sub plugin manager.
	 * 
	 * @return the anno sub plugin manager
	 */
	IAnnoSubPluginManager getAnnoSubPluginManager();

	/**
	 * Gets the block manager.
	 * 
	 * @return the block manager
	 */
	IBlockManager getBlockManager();

	/**
	 * Gets the mysql manager.
	 * 
	 * @return the mysql manager
	 */
	IMysqlManager getMysqlManager();

	/**
	 * Gets the note manager.
	 * 
	 * @return the note manager
	 */
	INoteManager getNoteManager();

	/**
	 * Gets the player data manager.
	 * 
	 * @return the player data manager
	 */
	IPlayerDataManager getPlayerDataManager();

	/**
	 * Gets the rank manager.
	 * 
	 * @return the rank manager
	 */
	IRankManager getRankManager();

	/**
	 * Gets the region manager.
	 * 
	 * @return the region manager
	 */
	IRegionManager getRegionManager();

	/**
	 * Gets the sub plugin manager.
	 * 
	 * @return the sub plugin manager
	 */
	ISubPluginManager getSubPluginManager();

	/**
	 * Gets the wand manager.
	 * 
	 * @return the wand manager
	 */
	IWandManager getWandManager();

	/**
	 * Gets the warning manager.
	 * 
	 * @return the warning manager
	 */
	IWarningManager getWarningManager();

	/**
	 * Gets the plugin name.
	 * 
	 * @return the plugin name
	 */
	String getPluginName();

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	String getVersion();
}
