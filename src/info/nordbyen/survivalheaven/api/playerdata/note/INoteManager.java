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

package info.nordbyen.survivalheaven.api.playerdata.note;

import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface INoteManager.
 */
public interface INoteManager {

    /**
     * The Interface INote.
     */
    public interface INote {

        /**
         * Gets the date.
         * 
         * @return the date
         */
        Date getDate();

        /**
         * Gets the id.
         * 
         * @return the id
         */
        int getId();

        /**
         * Gets the message.
         * 
         * @return the message
         */
        String getMessage();

        /**
         * Gets the player.
         * 
         * @return the player
         */
        IPlayerData getPlayer();

        /**
         * Gets the setter.
         * 
         * @return the setter
         */
        IPlayerData getSetter();
    }

    /**
     * Adds the note.
     * 
     * @param date the date
     * @param player the player
     * @param setter the setter
     * @param message the message
     * @throws SQLException the SQL exception
     * @throws Exception the exception
     */
    public void addNote(Date date, IPlayerData player, IPlayerData setter, String message) throws SQLException, Exception;

    /**
     * Gets the every notes.
     * 
     * @return the every notes
     */
    public List<INote> getEveryNotes();

    /**
     * Gets the note from id.
     * 
     * @param id the id
     * @return the note from id
     */
    public INote getNoteFromId(int id);

    /**
     * Gets the notes from name.
     * 
     * @param name the name
     * @return the notes from name
     */
    public List<INote> getNotesFromName(String name);

    /**
     * Gets the notes from player.
     * 
     * @param pd the pd
     * @return the notes from player
     */
    public List<INote> getNotesFromPlayer(IPlayerData pd);

    /**
     * Gets the notes from uuid.
     * 
     * @param uuid the uuid
     * @return the notes from uuid
     */
    public List<INote> getNotesFromUuid(String uuid);

    /**
     * Removes the note.
     * 
     * @param note the note
     * @throws SQLException the SQL exception
     */
    public void removeNote(INote note) throws SQLException;

    /**
     * Removes the note.
     * 
     * @param id the id
     * @throws SQLException the SQL exception
     */
    public void removeNote(int id) throws SQLException;
}
