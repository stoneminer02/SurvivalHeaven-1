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
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager;
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning.Level;
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
 * The Class WarningManager.
 */
@SurvivalHeavenSubPlugin(name = "WarningManager")
public class WarningManager implements IWarningManager {

	/**
	 * Creates the table.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static void createTable() throws SQLException {
		SH.getManager()
				.getMysqlManager()
				.query("CREATE TABLE IF NOT EXISTS `warnings` ("
						+ "`id` INT(11) NOT NULL AUTO_INCREMENT, "
						+ "`playeruuid` VARCHAR(255) NOT NULL, "
						+ "`setteruuid` VARCHAR(255) NOT NULL, "
						+ "`message` VARCHAR(255) NOT NULL, "
						+ "`date` VARCHAR(255) NOT NULL, `level` INT(11) NOT NULL, "
						+ "PRIMARY KEY (`id`) );");
	}

	/**
	 * Disable.
	 *
	 * @param plugin
	 *            the plugin
	 */
	@SurvivalHeavenDisable
	private static void disable(final JavaPlugin plugin) {
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
	@SuppressWarnings("unused")
	private static void loadFromMysql() throws SQLException {
		final ResultSet rs = SH.getManager().getMysqlManager()
				.query("SELECT * FROM warnings");
		while (rs.next()) {
			final int id = rs.getInt("id");
			final String puuid = rs.getString("playeruuid");
			final String suuid = rs.getString("setteruuid");
			final String message = rs.getString("message");
			final Date date = SH.getManager().getMysqlManager()
					.getDate(rs.getString("date"));
			final IWarning warning = new Warning(date, SH.getManager()
					.getPlayerDataManager().getPlayerData(puuid), SH
					.getManager().getPlayerDataManager().getPlayerData(suuid),
					message, null);
		}
	}

	/** The warnings. */
	private final List<IWarning> warnings = new ArrayList<IWarning>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * addWarning (java. util.Date,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * java.lang.String)
	 */
	@Override
	public void addWarning(final Date date, final IPlayerData player,
			final IPlayerData setter, final String message) throws SQLException {
		addWarning(date, player, setter, message, Level.LOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * addWarning (java. util.Date,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * info.nordbyen.survivalheaven.api.playerdata.IPlayerData,
	 * java.lang.String, info
	 * .nordbyen.survivalheaven.api.playerdata.warning.IWarningManager.IWarning
	 * .Level)
	 */
	@Override
	public void addWarning(final Date date, final IPlayerData player,
			final IPlayerData setter, final String message,
			final Warning.Level level) throws SQLException {
		if (date == null)
			throw new IllegalArgumentException("date cannot be null!");
		if (player == null)
			throw new IllegalArgumentException("player cannot be null!");
		if (message == null)
			throw new IllegalArgumentException("message cannot be null!");
		final IWarning warning = new Warning(date, player, setter, message,
				level);
		warnings.add(warning);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * getEveryWarnings()
	 */
	@Override
	public List<IWarning> getEveryWarnings() {
		return new ArrayList<IWarning>(warnings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * getWarningFromId (int)
	 */
	@Override
	public IWarning getWarningFromId(final int id) {
		for (final IWarning w : warnings)
			if (w.getId() == id)
				return w;
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * getWarningsFromName (java.lang.String)
	 */
	@Override
	public List<IWarning> getWarningsFromName(final String name) {
		final List<IWarning> pw = new ArrayList<IWarning>();
		for (final IWarning w : warnings)
			if (w.getPlayer().getName().equals(name)) {
				pw.add(w);
			}
		return pw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * getWarningsFromPlayer
	 * (info.nordbyen.survivalheaven.api.playerdata.IPlayerData)
	 */
	@Override
	public List<IWarning> getWarningsFromPlayer(final IPlayerData pd) {
		final List<IWarning> pw = new ArrayList<IWarning>();
		for (final IWarning w : warnings)
			if (w.getPlayer().getUUID().equals(pd.getUUID())) {
				pw.add(w);
			}
		return pw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * getWarningsFromUuid (java.lang.String)
	 */
	@Override
	public List<IWarning> getWarningsFromUuid(final String uuid) {
		final List<IWarning> pw = new ArrayList<IWarning>();
		for (final IWarning w : warnings)
			if (w.getPlayer().getUUID().equals(uuid)) {
				pw.add(w);
			}
		return pw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * removeWarning(int)
	 */
	@Override
	public void removeWarning(final int id) {
		final List<IWarning> removed = new ArrayList<IWarning>();
		for (final IWarning w : warnings)
			if (w.getId() == id) {
				removed.add(w);
			}
		for (final IWarning rem : removed) {
			removeWarning(rem);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager#
	 * removeWarning(info
	 * .nordbyen.api.playerdata.warning.IWarningManager.IWarning)
	 */
	@Override
	public void removeWarning(final IWarning warning) {
		if (!warnings.contains(warning))
			return;
		warnings.remove(warning);
	}
}
