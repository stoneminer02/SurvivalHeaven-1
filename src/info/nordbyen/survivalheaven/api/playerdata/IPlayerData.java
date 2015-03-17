/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.playerdata;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Location;

/**
 * The Interface IPlayerData.
 */
public interface IPlayerData {

	/**
	 * Adds the badge.
	 *
	 * @param badge
	 *            the badge
	 */
	public void addBadge(int badge);

	/**
	 * Adds the ip.
	 *
	 * @param ip
	 *            the ip
	 */
	public void addIp(String ip);

	/**
	 * Gets the badges.
	 *
	 * @return the badges
	 */
	public ArrayList<Integer> getBadges();

	/**
	 * Gets the badges as string.
	 *
	 * @return the badges as string
	 */
	public String getBadgesAsString();

	/**
	 * Gets the firstlogin.
	 *
	 * @return the firstlogin
	 */
	public Date getFirstlogin();

	/**
	 * Gets the gamemode.
	 *
	 * @return the gamemode
	 */
	public int getGamemode();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId();

	/**
	 * Gets the ips.
	 *
	 * @return the ips
	 */
	public ArrayList<String> getIps();

	/**
	 * Gets the ips as string.
	 *
	 * @return the ips as string
	 */
	public String getIpsAsString();

	/**
	 * Gets the lastlocation.
	 *
	 * @return the lastlocation
	 */
	public Location getLastlocation();

	/**
	 * Gets the lastlogin.
	 *
	 * @return the lastlogin
	 */
	public Date getLastlogin();

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel();

	/**
	 * Gets the money.
	 *
	 * @return the money
	 */
	public long getMoney();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	public int getRank();

	/**
	 * Gets the timeplayed.
	 *
	 * @return the timeplayed
	 */
	public long getTimeplayed();

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUUID();

	/**
	 * Removes the badge.
	 *
	 * @param badge
	 *            the badge
	 */
	public void removeBadge(int badge);

	/**
	 * Sets the badges.
	 *
	 * @param badges
	 *            the new badges
	 */
	public void setBadges(ArrayList<Integer> badges);

	/**
	 * Sets the gamemode.
	 *
	 * @param gamemode
	 *            the new gamemode
	 */
	public void setGamemode(int gamemode);

	/**
	 * Sets the lastlocation.
	 *
	 * @param lastlocation
	 *            the new lastlocation
	 */
	public void setLastlocation(Location lastlocation);

	/**
	 * Sets the lastlogin.
	 *
	 * @param lastlogin
	 *            the new lastlogin
	 */
	public void setLastlogin(Date lastlogin);

	/**
	 * Sets the level.
	 *
	 * @param level
	 *            the new level
	 */
	public void setLevel(int level);

	/**
	 * Sets the money.
	 *
	 * @param money
	 *            the new money
	 */
	public void setMoney(long money);

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name);

	/**
	 * Sets the rank.
	 *
	 * @param rank
	 *            the new rank
	 */
	public void setRank(int rank);

	/**
	 * Sets the timeplayed.
	 *
	 * @param timeplayed
	 *            the new timeplayed
	 */
	public void setTimeplayed(long timeplayed);
}
