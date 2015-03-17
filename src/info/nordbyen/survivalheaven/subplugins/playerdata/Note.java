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
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * The Class Note.
 */
public class Note implements INote {

	/** The date. */
	private final Date date;
	
	/** The player. */
	private final IPlayerData player;
	
	/** The setter. */
	private final IPlayerData setter;
	
	/** The message. */
	private final String message;
	
	/** The id. */
	private final int id;

	/**
	 * Instantiates a new note.
	 *
	 * @param date
	 *            the date
	 * @param player2
	 *            the player2
	 * @param setter2
	 *            the setter2
	 * @param message
	 *            the message
	 * @throws Exception
	 *             the exception
	 */
	Note(final Date date, final IPlayerData player2, final IPlayerData setter2,
			final String message) throws Exception {
		this.date = date;
		this.player = player2;
		this.setter = setter2;
		this.message = message;
		SH.getManager()
				.getMysqlManager()
				.query("INSERT INTO notes ( date, playeruuid, setteruuid, message ) VALUES ( \""
						+ SH.getManager().getMysqlManager().getDate(date)
						+ "\", \""
						+ player2.getUUID()
						+ "\", \""
						+ (setter2 == null ? "NO" : setter2.getUUID())
						+ "\", \"" + message + "\" )");
		final ResultSet rs = SH
				.getManager()
				.getMysqlManager()
				.query("SELECT id FROM notes WHERE playeruuid = \""
						+ player2.getUUID() + "\" AND message = \"" + message
						+ "\" AND date = \""
						+ SH.getManager().getMysqlManager().getDate(date)
						+ "\";");
		if ((rs != null) && rs.next()) {
			id = rs.getInt("id");
		} else
			throw new Exception("Noe gikk galt!");
	}

	/**
	 * Instantiates a new note.
	 *
	 * @param date
	 *            the date
	 * @param iPlayerData
	 *            the i player data
	 * @param iPlayerData2
	 *            the i player data2
	 * @param message
	 *            the message
	 * @param id
	 *            the id
	 * @throws SQLException
	 *             the SQL exception
	 */
	Note(final Date date, final IPlayerData iPlayerData,
			final IPlayerData iPlayerData2, final String message, final int id)
			throws SQLException {
		this.date = date;
		this.player = iPlayerData;
		this.setter = iPlayerData2;
		this.message = message;
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote#getDate
	 * ()
	 */
	@Override
	public Date getDate() {
		return new Date(date.getTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote#getId
	 * ()
	 */
	@Override
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote#
	 * getMessage ()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote#getPlayer
	 * ()
	 */
	@Override
	public IPlayerData getPlayer() {
		return player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.playerdata.note.INoteManager.INote#getSetter
	 * ()
	 */
	@Override
	public IPlayerData getSetter() {
		return setter;
	}
}
