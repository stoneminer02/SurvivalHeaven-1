/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class NoteManager.
 */
@SurvivalHeavenSubPlugin(name = "NoteManager")
public class NoteManager implements INoteManager {

	/**
	 * Creates the table.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static void createTable() throws SQLException {
		SH.getManager()
				.getMysqlManager()
				.query("CREATE TABLE IF NOT EXISTS `notes` ("
						+ "`id` INT(11) NOT NULL AUTO_INCREMENT, "
						+ "`playeruuid` VARCHAR(255) NOT NULL, "
						+ "`setteruuid` VARCHAR(255) NOT NULL, "
						+ "`message` VARCHAR(255) NOT NULL, "
						+ "`date` VARCHAR(255) NOT NULL, "
						+ "PRIMARY KEY (`id`) );");
	}

	/**
	 * Disable.
	 *
	 * @param plugin
	 *            the plugin
	 * @throws SQLException
	 *             the SQL exception
	 */
	@SurvivalHeavenDisable
	private static void disable(final JavaPlugin plugin) throws SQLException {
	}

	/**
	 * Enable.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenEnable
	private static void enable(final JavaPlugin plugin) {
		try {
			createTable();
			loadFromMysql();
		} catch (final SQLException e) {
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.RED + "Noe galt skjedde under loading av mysql");
			e.printStackTrace();
		}
	}

	/**
	 * Load from mysql.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static void loadFromMysql() throws SQLException {
		final ResultSet rs = SH.getManager().getMysqlManager()
				.query("SELECT * FROM notes");
		while (rs.next()) {
			final NoteManager manager = (NoteManager) SH.getManager()
					.getNoteManager();
			final INote note = new Note(SH.getManager().getMysqlManager()
					.getDate(rs.getString("date")), SH.getManager()
					.getPlayerDataManager()
					.getPlayerData(rs.getString("playeruuid")), SH.getManager()
					.getPlayerDataManager()
					.getPlayerData(rs.getString("setteruuid")),
					rs.getString("message"), rs.getInt("id"));
			manager.notes.add(note);
		}
	}

	/** The notes. */
	private final List<INote> notes = new ArrayList<INote>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#addNote
	 * (java .util.Date,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * java.lang.String)
	 */
	@Override
	public void addNote(final Date date, final IPlayerData pd,
			final IPlayerData setter, final String message) throws Exception {
		if (date == null)
			throw new IllegalArgumentException("date cannot be null!");
		if (pd == null)
			throw new IllegalArgumentException("player cannot be null!");
		if (message == null)
			throw new IllegalArgumentException("message cannot be null!");
		final INote note = new Note(date, pd, setter, message);
		notes.add(note);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#getEveryNotes
	 * ()
	 */
	@Override
	public List<INote> getEveryNotes() {
		return new ArrayList<INote>(notes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#getNoteFromId
	 * (int)
	 */
	@Override
	public INote getNoteFromId(final int id) {
		for (final INote n : notes)
			if (n.getId() == id)
				return n;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#
	 * getNotesFromName (java. lang.String)
	 */
	@Override
	public List<INote> getNotesFromName(final String name) {
		final List<INote> pn = new ArrayList<INote>();
		for (final INote n : notes)
			if (n.getPlayer().getName().equals(name)) {
				pn.add(n);
			}
		return pn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#
	 * getNotesFromPlayer(info .nordbyen.api.playerdata.IPlayerData)
	 */
	@Override
	public List<INote> getNotesFromPlayer(final IPlayerData pd) {
		final List<INote> pn = new ArrayList<INote>();
		for (final INote n : notes)
			if (n.getPlayer().getUUID().equals(pd.getUUID())) {
				pn.add(n);
			}
		return pn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#
	 * getNotesFromUuid (java. lang.String)
	 */
	@Override
	public List<INote> getNotesFromUuid(final String uuid) {
		final List<INote> pn = new ArrayList<INote>();
		for (final INote n : notes)
			if (n.getPlayer().getUUID().equals(uuid)) {
				pn.add(n);
			}
		return pn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#removeNote
	 * (info.nordbyen.survivalheaven .api.playerdata.note.INoteManager.INote)
	 */
	@Override
	public void removeNote(final INote note) throws SQLException {
		if (!notes.contains(note))
			return;
		notes.remove(note);
		SH.getManager().getMysqlManager()
				.query("DELETE FROM notes WHERE id = " + note.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager#removeNote
	 * (int)
	 */
	@Override
	public void removeNote(final int id) throws SQLException {
		final List<INote> removed = new ArrayList<INote>();
		for (final INote w : notes)
			if (w.getId() == id) {
				removed.add(w);
			}
		for (final INote rem : removed) {
			removeNote(rem);
		}
	}
}
