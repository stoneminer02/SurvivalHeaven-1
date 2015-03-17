/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.bukkit.Location;

/**
 * The Interface IMysqlManager.
 */
public interface IMysqlManager {

	/**
	 * Check connection.
	 *
	 * @return true, if successful
	 * @throws SQLException
	 *             the SQL exception
	 */
	boolean checkConnection() throws SQLException;

	/**
	 * Clear table.
	 *
	 * @param table
	 *            the table
	 * @return true, if successful
	 */
	boolean clearTable(final String table);

	/**
	 * Close.
	 */
	void close();

	/**
	 * Delete table.
	 *
	 * @param table
	 *            the table
	 * @return true, if successful
	 */
	boolean deleteTable(final String table);

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	Connection getConnection();

	/**
	 * Gets the date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	String getDate(Date date);

	/**
	 * Gets the date.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	Date getDate(String date);

	/**
	 * Gets the location.
	 *
	 * @param loc
	 *            the loc
	 * @return the location
	 */
	String getLocation(Location loc);

	/**
	 * Gets the location.
	 *
	 * @param loc
	 *            the loc
	 * @return the location
	 */
	Location getLocation(String loc);

	/**
	 * Insert.
	 *
	 * @param table
	 *            the table
	 * @param column
	 *            the column
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	boolean insert(final String table, final Object[] column,
			final Object[] value);

	/**
	 * Open.
	 *
	 * @return the connection
	 */
	Connection open();

	/**
	 * Query.
	 *
	 * @param query
	 *            the query
	 * @return the result set
	 * @throws SQLException
	 *             the SQL exception
	 */
	ResultSet query(final String query) throws SQLException;

	/**
	 * Query.
	 *
	 * @param query
	 *            the query
	 * @param ret
	 *            the ret
	 * @return the result set
	 * @throws SQLException
	 *             the SQL exception
	 */
	ResultSet query(final String query, final int ret) throws SQLException;
}
