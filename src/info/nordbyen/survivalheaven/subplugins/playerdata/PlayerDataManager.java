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
import info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The Class PlayerDataManager.
 */
public final class PlayerDataManager implements IPlayerDataManager {

	/** The playerdatalist. */
	private static HashMap<String, IPlayerData> playerdatalist = new HashMap<String, IPlayerData>();

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
		SH.getManager()
				.getMysqlManager()
				.insert("players",
						new Object[] { "uuid", "name", "ips", "gamemode",
								"firstlogin", "lastlogin" },
						new Object[] {
								p.getUniqueId().toString(),
								p.getName(),
								p.getAddress().toString().replace("/", "")
										.split(":")[0],
								p.getGameMode().getValue(),
								SH.getManager().getMysqlManager()
										.getDate(new Date()),
								SH.getManager().getMysqlManager()
										.getDate(new Date()) });
		try {
			final ResultSet rs = SH
					.getManager()
					.getMysqlManager()
					.query("SELECT * FROM `players` WHERE `uuid` = \""
							+ p.getUniqueId().toString() + "\"");
			if (rs.next()) {
				final int id = rs.getInt("id");
				final ArrayList<String> ips = new ArrayList<String>();
				for (final String ip : rs.getString("ips").split(";")) {
					ips.add(ip);
				}
				final String name = rs.getString("name");
				final String uuid = rs.getString("uuid");
				final Date firstlogin = SH.getManager().getMysqlManager()
						.getDate(rs.getString("firstlogin"));
				final Date lastlogin = SH.getManager().getMysqlManager()
						.getDate(rs.getString("lastlogin"));
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
				final Location lastlocation = SH.getManager().getMysqlManager()
						.getLocation(rs.getString("lastlocation"));
				final int level = rs.getInt("level");
				final long money = rs.getInt("bank");
				final int gamemode = rs.getInt("gamemode");
				final PlayerData data = new PlayerData(id, name, ips, uuid,
						firstlogin, lastlogin, timeplayed, rank, badges,
						lastlocation, level, money, gamemode);
				playerdatalist.put(data.getUUID(), data);
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
			SH.getManager()
					.getMysqlManager()
					.query("CREATE TABLE IF NOT EXISTS `players` ("
							+ "`id` INT(11) NOT NULL AUTO_INCREMENT, "
							+ "`uuid` VARCHAR(255) NOT NULL, "
							+ "`name` VARCHAR(255) NOT NULL, "
							+ "`ips` LONGTEXT NOT NULL, "
							+ "`gamemode` INT(2) NOT NULL DEFAULT 0, "
							+ "`firstlogin` VARCHAR(255) NOT NULL, "
							+ "`lastlogin` VARCHAR(255) NOT NULL, "
							+ "`timeplayed` BIGINT(11) DEFAULT 0 NOT NULL, "
							+ "`bank` INT(11) DEFAULT 300, "
							+ "`rank` INT(11) DEFAULT 1, "
							+ "`badges` VARCHAR(255) DEFAULT \"\", "
							+ "`level` INT(11) DEFAULT 1, "
							+ "`language` VARCHAR(255) DEFAULT \"norsk\", "
							+ "`lastlocation` VARCHAR(255) DEFAULT \"NO\" NOT NULL, "
							+ "PRIMARY KEY (`id`) );");
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
		return playerdatalist.get(uuid);
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
		for (final Entry<String, IPlayerData> entry : playerdatalist.entrySet()) {
			final IPlayerData data = entry.getValue();
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
		for (final Entry<String, IPlayerData> entry : playerdatalist.entrySet()) {
			final IPlayerData pd = entry.getValue();
			try {
				SH.getManager()
						.getMysqlManager()
						.query("UPDATE players SET " + "`name` = \""
								+ pd.getName()
								+ "\", "
								+ "`ips` = \""
								+ pd.getIpsAsString()
								+ "\", "
								+ "`gamemode` = "
								+ pd.getGamemode()
								+ ", "
								+ "`lastlogin` = \""
								+ SH.getManager().getMysqlManager()
										.getDate(pd.getLastlogin()) + "\", "
								+ "`timeplayed` = " + pd.getTimeplayed() + ", "
								+ "`bank` = " + pd.getMoney() + ", "
								+ "`rank` = " + pd.getRank() + ", "
								+ "`badges` = \"" + pd.getBadgesAsString()
								+ "\", " + "`level` = " + pd.getLevel()
								+ " WHERE `uuid` = \"" + pd.getUUID() + "\";");
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update data from database.
	 */
	void updateDataFromDatabase() {
		try {
			final ResultSet rs = SH.getManager().getMysqlManager()
					.query("SELECT * FROM `players`");
			while (rs.next()) {
				final int id = rs.getInt("id");
				final ArrayList<String> ips = new ArrayList<String>();
				for (final String ip : rs.getString("ips").split(";")) {
					ips.add(ip);
				}
				final String name = rs.getString("name");
				final String uuid = rs.getString("uuid");
				final Date firstlogin = SH.getManager().getMysqlManager()
						.getDate(rs.getString("firstlogin"));
				final Date lastlogin = SH.getManager().getMysqlManager()
						.getDate(rs.getString("lastlogin"));
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
				final Location lastlocation = SH.getManager().getMysqlManager()
						.getLocation(rs.getString("lastlocation"));
				final int level = rs.getInt("level");
				final long money = rs.getInt("bank");
				final int gamemode = rs.getInt("gamemode");
				final PlayerData data = new PlayerData(id, name, ips, uuid,
						firstlogin, lastlogin, timeplayed, rank, badges,
						lastlocation, level, money, gamemode);
				playerdatalist.put(data.getUUID(), data);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
