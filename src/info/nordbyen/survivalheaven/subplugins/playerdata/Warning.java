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
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Warning.
 */
public class Warning implements IWarning {

    /** The date. */
    private final Date date;
    /** The player. */
    private final IPlayerData player;
    /** The setter. */
    private final IPlayerData setter;
    /** The message. */
    private final String message;
    /** The level. */
    private final Warning.Level level;
    /** The id. */
    private final int id;

    /**
     * Instantiates a new warning.
     * 
     * @param date the date
     * @param player the player
     * @param setter the setter
     * @param message the message
     * @param level the level
     * @throws SQLException the SQL exception
     */
    Warning(final Date date, final IPlayerData player, final IPlayerData setter, final String message, final Warning.Level level) throws SQLException {
        this.date = date;
        this.player = player;
        this.setter = setter;
        this.message = message;
        this.level = level;
        final ResultSet rs = SH.getManager().getMysqlManager().query("INSERT INTO warnings ( date, playeruuid, setteruuid, message, level ) VALUES ( \"" + SH.getManager().getMysqlManager().getDate(date) + "\", \"" + player.getUUID() + "\", \"" + (setter == null ? "NO" : setter.getUUID()) + "\", \"" + message + "\", `" + level.asInt() + "` );", Statement.RETURN_GENERATED_KEYS); // TODO
        id = rs.getInt("id");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getDate()
     */
    @Override
    public Date getDate() {
        return new Date(date.getTime());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getLevel()
     */
    @Override
    public Level getLevel() {
        return level;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getMessage ()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getPlayer()
     */
    @Override
    public IPlayerData getPlayer() {
        return player;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
     * #getSetter()
     */
    @Override
    public IPlayerData getSetter() {
        return setter;
    }
}
