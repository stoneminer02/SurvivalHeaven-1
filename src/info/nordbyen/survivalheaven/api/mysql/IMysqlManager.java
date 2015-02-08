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
