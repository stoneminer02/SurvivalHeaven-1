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
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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
	 * @param date
	 *            the date
	 * @param player
	 *            the player
	 * @param setter
	 *            the setter
	 * @param message
	 *            the message
	 * @param level
	 *            the level
	 * @throws SQLException
	 *             the SQL exception
	 */
	Warning(final Date date, final IPlayerData player,
			final IPlayerData setter, final String message,
			final Warning.Level level) throws SQLException {
		this.date = date;
		this.player = player;
		this.setter = setter;
		this.message = message;
		this.level = level;
		final ResultSet rs = SH
				.getManager()
				.getMysqlManager()
				.query("INSERT INTO warnings ( date, playeruuid, setteruuid, message, level ) VALUES ( \""
						+ SH.getManager().getMysqlManager().getDate(date)
						+ "\", \""
						+ player.getUUID()
						+ "\", \""
						+ (setter == null ? "NO" : setter.getUUID())
						+ "\", \""
						+ message + "\", `" + level.asInt() + "` );",
						Statement.RETURN_GENERATED_KEYS); // TODO
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
