/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.playerdata;

import org.bukkit.entity.Player;

/**
 * The Interface IPlayerDataManager.
 */
public interface IPlayerDataManager {

	/**
	 * Creates the player data.
	 *
	 * @param p
	 *            the p
	 */
	void createPlayerData(Player p);

	/**
	 * Gets the player data.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the player data
	 */
	IPlayerData getPlayerData(String uuid);

	/**
	 * Gets the player data from name.
	 *
	 * @param name
	 *            the name
	 * @return the player data from name
	 */
	IPlayerData getPlayerDataFromName(String name);

	/**
	 * Save data to database.
	 */
	void saveDataToDatabase();
}
