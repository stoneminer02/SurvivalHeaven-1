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

package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.util.Translator;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Location;

/**
 * The Class PlayerData.
 */
public class PlayerData implements IPlayerData {

    /** The id. */
    private final int id;
    /** The ips. */
    private ArrayList<String> ips = new ArrayList<String>();
    /** The name. */
    private String name;
    /** The uuid. */
    private final String uuid;
    /** The firstlogin. */
    private final Date firstlogin;
    /** The lastlogin. */
    private Date lastlogin;
    /** The timeplayed. */
    private long timeplayed;
    /** The rank. */
    private int rank;
    /** The badges. */
    private final ArrayList<Integer> badges = new ArrayList<Integer>();
    /** The gamemode. */
    private int gamemode;
    /** The lastlocation. */
    private Location lastlocation;
    /** The language. */
    private Translator language;
    /** The level. */
    private int level;
    /** The money. */
    private long money;

    /**
     * Instantiates a new player data.
     * 
     * @param id the id
     * @param name the name
     * @param ips the ips
     * @param uuid the uuid
     * @param firstlogin the firstlogin
     * @param lastlogin the lastlogin
     * @param timeplayed the timeplayed
     * @param rank the rank
     * @param badges the badges
     * @param lastlocation the lastlocation
     * @param language the language
     * @param level the level
     * @param money the money
     * @param gamemode the gamemode
     */
    public PlayerData(final int id, final String name, final ArrayList<String> ips, final String uuid, final Date firstlogin, final Date lastlogin, final long timeplayed, final int rank, final ArrayList<Integer> badges, final Location lastlocation, final Translator language, final int level, final long money, final int gamemode) {
        this.setGamemode(gamemode);
        this.id = id;
        this.setName(name);
        this.uuid = uuid;
        this.firstlogin = firstlogin;
        this.setLastlogin(lastlogin);
        this.setTimeplayed(timeplayed);
        this.setRank(rank);
        this.setLastlocation(lastlocation);
        this.setLanguage(language);
        this.setLevel(level);
        this.setMoney(money);
        this.ips = ips;
        this.setBadges(badges);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#addBadge(int)
     */
    @Override
    public void addBadge(final int badge) {
        if (!badges.contains(badge)) {
            badges.add(badge);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#addIp(java.lang
     * .String)
     */
    @Override
    public void addIp(final String ip) {
        final boolean exists = ips.contains(ip);
        if (!exists) {
            ips.add(ip);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getBadges()
     */
    @Override
    public ArrayList<Integer> getBadges() {
        return badges;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getBadgesAsString
     * ()
     */
    @Override
    public String getBadgesAsString() {
        String list = "";
        for (final int badge : badges) {
            list += badge + ",";
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getFirstlogin()
     */
    @Override
    public Date getFirstlogin() {
        return firstlogin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getGamemode()
     */
    @Override
    public int getGamemode() {
        return gamemode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getIps()
     */
    @Override
    public ArrayList<String> getIps() {
        return ips;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getIpsAsString()
     */
    @Override
    public String getIpsAsString() {
        String s = "";
        for (final String ip : ips) {
            s += ip + ",";
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getLanguage()
     */
    @Override
    public Translator getLanguage() {
        return language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getLastlocation()
     */
    @Override
    public Location getLastlocation() {
        return lastlocation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getLastlogin()
     */
    @Override
    public Date getLastlogin() {
        return lastlogin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getLevel()
     */
    @Override
    public int getLevel() {
        return level;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getMoney()
     */
    @Override
    public long getMoney() {
        return money;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getRank()
     */
    @Override
    public int getRank() {
        return rank;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getTimeplayed()
     */
    @Override
    public long getTimeplayed() {
        return timeplayed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#getUUID()
     */
    @Override
    public String getUUID() {
        return uuid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#removeBadge(int)
     */
    @Override
    public void removeBadge(final int badge) {
        badges.remove(badge);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setBadges(java.
     * util.ArrayList)
     */
    @Override
    public void setBadges(final ArrayList<Integer> badges) {
        for (final int badge : badges) {
            addBadge(badge);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setGamemode(int)
     */
    @Override
    public void setGamemode(final int gamemode) {
        this.gamemode = gamemode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setLanguage(info
     * .nordbyen.survivalheaven.api .util.Translator)
     */
    @Override
    public void setLanguage(final Translator language) {
        this.language = language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setLastlocation
     * (org.bukkit.Location )
     */
    @Override
    public void setLastlocation(final Location lastlocation) {
        this.lastlocation = lastlocation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setLastlogin(java
     * .util.Date)
     */
    @Override
    public void setLastlogin(final Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setLevel(int)
     */
    @Override
    public void setLevel(final int level) {
        this.level = level;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setMoney(long)
     */
    @Override
    public void setMoney(final long money) {
        this.money = money;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setName(java.lang
     * .String)
     */
    @Override
    public void setName(final String name) {
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setRank(int)
     */
    @Override
    public void setRank(final int rank) {
        this.rank = rank;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerData#setTimeplayed
     * (long)
     */
    @Override
    public void setTimeplayed(final long timeplayed) {
        this.timeplayed = timeplayed;
    }
}
