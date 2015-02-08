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

package info.nordbyen.survivalheaven.api.playerdata;

import info.nordbyen.survivalheaven.api.util.Translator;

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
	 * Gets the language.
	 * 
	 * @return the language
	 */
	public Translator getLanguage();

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
	 * Sets the language.
	 * 
	 * @param language
	 *            the new language
	 */
	public void setLanguage(Translator language);

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
