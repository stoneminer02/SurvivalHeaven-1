/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
import info.nordbyen.survivalheaven.subplugins.groupmanager.FriendManager;
import info.nordbyen.survivalheaven.subplugins.homes.HomeManager;

/**
 * The Interface ISH.
 */
public interface ISH {

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
	 * Gets the friend manager.
	 *
	 * @return the friend manager
	 */
	FriendManager getFriendManager();

	/**
	 * Gets the home manager.
	 *
	 * @return the home manager
	 */
	HomeManager getHomeManager();

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
	 * Gets the plugin name.
	 *
	 * @return the plugin name
	 */
	String getPluginName();

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
	 * Gets the version.
	 *
	 * @return the version
	 */
	String getVersion();

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
}
