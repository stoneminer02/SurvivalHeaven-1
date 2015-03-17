/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.ban;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;

/**
 * The Class BanManager.
 */
public class BanManager {

	/** The bans. */
	private HashMap< String, ArrayList< Ban > > bans = new HashMap< String, ArrayList< Ban > >(); 
	
	/**
	 * Instantiates a new ban manager.
	 */
	public BanManager() {
		try {
			createTable();
			updateFromDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start timer.
	 */
	public void startTimer() {
	}

	/**
	 * Creates the table.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void createTable() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query( "CREATE TABLE IF NOT EXISTS ban ("
				+ "`id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
				+ "`uuid` VARCHAR(45) NOT NULL,"
				+ "`by_uuid` VARCHAR(45) NOT NULL,"
				+ "`reason` VARCHAR(45) NOT NULL,"
				+ "`time_given` DATETIME NOT NULL,"
				+ "`time_unban` DATETIME NOT NULL DEFAULT NOW()"
				+ ");" );
	}

	/**
	 * Update from database.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void updateFromDatabase() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		ResultSet rs = sql.query( "SELECT * FROM bans;" );
		while( rs != null && rs.next() ) {
			String uuid = rs.getString( "uuid" );
			String reason = rs.getString( "reason" );
			String by_uuid = rs.getString( "by_uuid" );
			Date from = rs.getDate( "time_given" );
			Date to = rs.getDate( "time_unban" );
			Ban ban = new Ban( uuid, by_uuid, reason, from, to );
			bans.get( uuid ).add( ban );
		}
	}
	
	/**
	 * Adds the ban.
	 *
	 * @param ban
	 *            the ban
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void addBan( Ban ban ) throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query( "INSERT INTO ban ( uuid, by_uuid, reason, time_given, time_unban ) VALUES ( '" + ban.getUUID() + "', '" + ban.getGiverUUID() + "', '" + ban.getReason() + "', '" + ban.getFrom().toString() + "', '" + ban.getTo().toString() + "' )" );
	}
	
	/**
	 * Gets the bans from player.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the bans from player
	 */
	public ArrayList< Ban > getBansFromPlayer( String uuid ) {
		if( !bans.containsKey( uuid ) || bans.get( uuid ) == null ) {
			bans.put( uuid, new ArrayList< Ban >() );
		}
		return bans.get( uuid );
	}
	
	/**
	 * Removes the ban from player.
	 *
	 * @param ban
	 *            the ban
	 * @param uuid
	 *            the uuid
	 */
	public void removeBanFromPlayer( Ban ban, String uuid ) {
		
	}
}
