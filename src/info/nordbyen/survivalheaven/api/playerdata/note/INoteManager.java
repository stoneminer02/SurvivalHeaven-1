/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.playerdata.note;

import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
	 * @param date
	 *            the date
	 * @param player
	 *            the player
	 * @param setter
	 *            the setter
	 * @param message
	 *            the message
	 * @throws SQLException
	 *             the SQL exception
	 * @throws Exception
	 *             the exception
	 */
	public void addNote(Date date, IPlayerData player, IPlayerData setter,
			String message) throws SQLException, Exception;

	/**
	 * Gets the every notes.
	 *
	 * @return the every notes
	 */
	public List<INote> getEveryNotes();

	/**
	 * Gets the note from id.
	 *
	 * @param id
	 *            the id
	 * @return the note from id
	 */
	public INote getNoteFromId(int id);

	/**
	 * Gets the notes from name.
	 *
	 * @param name
	 *            the name
	 * @return the notes from name
	 */
	public List<INote> getNotesFromName(String name);

	/**
	 * Gets the notes from player.
	 *
	 * @param pd
	 *            the pd
	 * @return the notes from player
	 */
	public List<INote> getNotesFromPlayer(IPlayerData pd);

	/**
	 * Gets the notes from uuid.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the notes from uuid
	 */
	public List<INote> getNotesFromUuid(String uuid);

	/**
	 * Removes the note.
	 *
	 * @param note
	 *            the note
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void removeNote(INote note) throws SQLException;

	/**
	 * Removes the note.
	 *
	 * @param id
	 *            the id
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void removeNote(int id) throws SQLException;
}
