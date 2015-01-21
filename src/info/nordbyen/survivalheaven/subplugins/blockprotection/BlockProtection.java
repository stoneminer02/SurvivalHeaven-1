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

package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.subplugins.blockprotection.listeners.BlockProtectionListener;
import info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit.MyEventHandler;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.sk89q.worldedit.WorldEdit;

/**
 * The Class BlockProtection.
 */
public final class BlockProtection extends SubPlugin {

    /** The instance. */
    private static BlockProtection instance;

    /**
     * Gets the single instance of BlockProtection.
     * 
     * @return single instance of BlockProtection
     */
    static BlockProtection getInstance() {
        return instance;
    }

    /** The registered worlds. */
    private final ArrayList<String> registeredWorlds = new ArrayList<String>();

    /**
     * Instantiates a new block protection.
     * 
     * @param name the name
     */
    public BlockProtection(final String name) {
        super(name);
        instance = this;
    }

    /**
     * Creates the tables.
     */
    private void createTables() {
        for (final World world : Bukkit.getWorlds()) {
            createWorldTable(world.getName());
        }
    }

    /**
     * Creates the world table.
     * 
     * @param world the world
     */
    private void createWorldTable(final String world) {
        if (registeredWorlds.contains(world))
            return;
        try {
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.SURVIVAL.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ") " + "ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.CREATIVE.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ") " + "ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            SH.getManager().getMysqlManager().query("CREATE TABLE IF NOT EXISTS `blocks_" + BlockPlacedType.WORLDEDIT.name + "_" + world + "` (" + "`id` INT(22) NOT NULL AUTO_INCREMENT, " + "`name` VARCHAR(255) NOT NULL, " + "`uuid` VARCHAR(255) NOT NULL, " + "`x` INT(11) NOT NULL, " + "`y` INT(11) NOT NULL, " + "`z` INT(11) NOT NULL, " + "`time` BIGINT NOT NULL, " + "PRIMARY KEY (`id`), " + "UNIQUE KEY idx_table_x_y_z ( x, y, z )" + ") " + "ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
            registeredWorlds.add(world);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
     */
    @Override
    protected void disable() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
     */
    @Override
    protected void enable() {
        createTables();
        Bukkit.getPluginManager().registerEvents(new BlockProtectionListener(), SH.getPlugin());
        WorldEdit.getInstance().getEventBus().register(new MyEventHandler());
    }

    /**
     * Gets the who placed.
     * 
     * @param b the b
     * @return the who placed
     */
    IPlayerData getWhoPlaced(final Block b) {
        final String w = b.getWorld().getName();
        createWorldTable(w);
        try {
            final ResultSet rs = SH.getManager().getMysqlManager().query("(SELECT uuid, time FROM `blocks_" + BlockPlacedType.SURVIVAL.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\") UNION (SELECT uuid, time FROM `blocks_" + BlockPlacedType.CREATIVE.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\") UNION (SELECT uuid, time FROM `blocks_" + BlockPlacedType.WORLDEDIT.name + "_" + w + "` WHERE x = \"" + b.getX() + "\" AND y = \"" + b.getY() + "\" AND z = \"" + b.getZ() + "\")");
            String uuid = null;
            long time = -1;
            while (rs.next()) {
                final String uuid_ = rs.getString("uuid");
                final Long time_ = rs.getLong("time");
                if (time_ > time) {
                    time = time_;
                    uuid = uuid_;
                }
            }
            if (time == -1)
                return null;
            return SH.getManager().getPlayerDataManager().getPlayerData(uuid);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the who placed.
     * 
     * @param p the p
     * @param b the b
     * @param type the type
     */
    void setWhoPlaced(final PlayerData p, final Block b, final BlockPlacedType type) {
        setWhoPlaced(p.getUUID(), p.getName(), b, type);
    }

    /**
     * Sets the who placed.
     * 
     * @param uuid the uuid
     * @param name the name
     * @param b the b
     * @param type the type
     */
    void setWhoPlaced(final String uuid, final String name, final Block b, final BlockPlacedType type) {
        new Thread() {

            @Override
            public void run() {
                try {
                    SH.getManager().getMysqlManager().query("REPLACE INTO blocks_" + type.name + "_" + b.getWorld().getName() + " ( name, uuid, x, y, z, time ) " + "VALUES ( \"" + name + "\", \"" + uuid + "\", " + b.getX() + ", " + b.getY() + ", " + b.getZ() + ", " + System.currentTimeMillis() + " );");
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
