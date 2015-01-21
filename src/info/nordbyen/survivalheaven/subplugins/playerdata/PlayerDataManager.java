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

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager;
import info.nordbyen.survivalheaven.api.util.Translator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The Class PlayerDataManager.
 */
public final class PlayerDataManager implements IPlayerDataManager {

    /** The playerdatalist. */
    private static ArrayList<IPlayerData> playerdatalist = new ArrayList<IPlayerData>();

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager#
     * createPlayerData (org. bukkit.entity.Player)
     */
    @Override
    @SuppressWarnings("deprecation")
    public void createPlayerData(final Player p) {
        /* Is the data exists, return */
        if (getPlayerData(p.getUniqueId().toString()) != null)
            return;
        SH.getManager().getMysqlManager().insert("players", new Object[] { "uuid", "name", "ips", "gamemode", "firstlogin", "lastlogin" }, new Object[] { p.getUniqueId().toString(), p.getName(), p.getAddress().toString().replace("/", "").split(":")[0], p.getGameMode().getValue(), SH.getManager().getMysqlManager().getDate(new Date()), SH.getManager().getMysqlManager().getDate(new Date()) });
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT * FROM `players` WHERE `uuid` = \"" + p.getUniqueId().toString() + "\"");
            if (rs.next()) {
                final int id = rs.getInt("id");
                final ArrayList<String> ips = new ArrayList<String>();
                for (final String ip : rs.getString("ips").split(";")) {
                    ips.add(ip);
                }
                final String name = rs.getString("name");
                final String uuid = rs.getString("uuid");
                final Date firstlogin = SH.getManager().getMysqlManager().getDate(rs.getString("firstlogin"));
                final Date lastlogin = SH.getManager().getMysqlManager().getDate(rs.getString("lastlogin"));
                final long timeplayed = rs.getLong("timeplayed");
                final int rank = rs.getInt("rank");
                final ArrayList<Integer> badges = new ArrayList<Integer>();
                final String[] ba = rs.getString("badges").split(",");
                for (final String badge : ba) {
                    try {
                        badges.add(Integer.parseInt(badge));
                    } catch (final Exception e) {
                    } // Ignorere errorer her
                }
                final Location lastlocation = SH.getManager().getMysqlManager().getLocation(rs.getString("lastlocation"));
                Translator language;
                final String ling = rs.getString("language");
                if (ling.equalsIgnoreCase("norsk")) {
                    language = Translator.NORSK;
                } else if (ling.equalsIgnoreCase("engelsk")) {
                    language = Translator.ENGELSK;
                } else {
                    language = Translator.NORSK;
                }
                final int level = rs.getInt("level");
                final long money = rs.getInt("bank");
                final int gamemode = rs.getInt("gamemode");
                final PlayerData data = new PlayerData(id, name, ips, uuid, firstlogin, lastlogin, timeplayed, rank, badges, lastlocation, language, level, money, gamemode);
                playerdatalist.add(data);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the table.
     */
    void createTable() {
        try {
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `players` (" + "`id` INT(11) NOT NULL AUTO_INCREMENT, " + "`uuid` VARCHAR(255) NOT NULL, " + "`name` VARCHAR(255) NOT NULL, " + "`ips` LONGTEXT NOT NULL, " + "`gamemode` INT(2) NOT NULL DEFAULT 0, " + "`firstlogin` VARCHAR(255) NOT NULL, " + "`lastlogin` VARCHAR(255) NOT NULL, " + "`timeplayed` BIGINT(11) DEFAULT 0 NOT NULL, " + "`bank` INT(11) DEFAULT 300, " + "`rank` INT(11) DEFAULT 1, " + "`badges` VARCHAR(255) DEFAULT \"\", " + "`level` INT(11) DEFAULT 1, " + "`language` VARCHAR(255) DEFAULT \"norsk\", " + "`lastlocation` VARCHAR(255) DEFAULT \"NO\" NOT NULL, " + "PRIMARY KEY (`id`) );");
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update data from database.
     */
    void updateDataFromDatabase() {
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("SELECT * FROM `players`");
            while (rs.next()) {
                final int id = rs.getInt("id");
                final ArrayList<String> ips = new ArrayList<String>();
                for (final String ip : rs.getString("ips").split(";")) {
                    ips.add(ip);
                }
                final String name = rs.getString("name");
                final String uuid = rs.getString("uuid");
                final Date firstlogin = SH.getManager().getMysqlManager().getDate(rs.getString("firstlogin"));
                final Date lastlogin = SH.getManager().getMysqlManager().getDate(rs.getString("lastlogin"));
                final long timeplayed = rs.getLong("timeplayed");
                final int rank = rs.getInt("rank");
                final ArrayList<Integer> badges = new ArrayList<Integer>();
                final String[] ba = rs.getString("badges").split(",");
                for (final String badge : ba) {
                    try {
                        badges.add(Integer.parseInt(badge));
                    } catch (final Exception e) {
                    } // Ignorere errorer her
                }
                final Location lastlocation = SH.getManager().getMysqlManager().getLocation(rs.getString("lastlocation"));
                Translator language;
                final String ling = rs.getString("language");
                if (ling.equalsIgnoreCase("norsk")) {
                    language = Translator.NORSK;
                } else if (ling.equalsIgnoreCase("engelsk")) {
                    language = Translator.ENGELSK;
                } else {
                    language = Translator.NORSK;
                }
                final int level = rs.getInt("level");
                final long money = rs.getInt("bank");
                final int gamemode = rs.getInt("gamemode");
                final PlayerData data = new PlayerData(id, name, ips, uuid, firstlogin, lastlogin, timeplayed, rank, badges, lastlocation, language, level, money, gamemode);
                playerdatalist.add(data);
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager#getPlayerData
     * (java.lang .String)
     */
    @Override
    public IPlayerData getPlayerData(final String uuid) {
        if (uuid == null)
            return null;
        for (final IPlayerData data : playerdatalist) {
            if (data.getUUID().equalsIgnoreCase(uuid))
                return data;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager#
     * getPlayerDataFromName (java.lang.String)
     */
    @Override
    public IPlayerData getPlayerDataFromName(final String name) {
        if (name == null)
            return null;
        for (final IPlayerData data : playerdatalist) {
            if (data.getName().equalsIgnoreCase(name))
                return data;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager#
     * saveDataToDatabase()
     */
    @Override
    public void saveDataToDatabase() {
        for (final IPlayerData pd : playerdatalist) {
            try {
                SH.getManager().getMysqlManager().query("UPDATE players SET " + "`name` = \"" + pd.getName() + "\", " + "`ips` = \"" + pd.getIpsAsString() + "\", " + "`gamemode` = " + pd.getGamemode() + ", " + "`lastlogin` = \"" + SH.getManager().getMysqlManager().getDate(pd.getLastlogin()) + "\", " + "`timeplayed` = " + pd.getTimeplayed() + ", " + "`bank` = " + pd.getMoney() + ", " + "`rank` = " + pd.getRank() + ", " + "`badges` = \"" + pd.getBadgesAsString() + "\", " + "`level` = " + pd.getLevel() + ", " + "`language` = \"" + pd.getLanguage() + "\" WHERE `uuid` = \"" + pd.getUUID() + "\";");
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
