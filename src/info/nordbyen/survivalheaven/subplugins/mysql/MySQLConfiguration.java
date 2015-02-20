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

package info.nordbyen.survivalheaven.subplugins.mysql;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;

/**
 * The Class MySQLConfiguration.
 */
public class MySQLConfiguration extends CustomConfiguration {

	/**
	 * Gets the database name.
	 * 
	 * @return the database name
	 */
	public static String getDatabaseName() {
		getInstance().reload();
		return getInstance().getString("database");
	}

	/**
	 * Gets the host name.
	 * 
	 * @return the host name
	 */
	public static String getHostName() {
		getInstance().reload();
		return getInstance().getString("host");
	}

	/**
	 * Gets the host port.
	 * 
	 * @return the host port
	 */
	public static int getHostPort() {
		getInstance().reload();
		return getInstance().getInt("port");
	}

	/**
	 * Gets the single instance of MySQLConfiguration.
	 * 
	 * @return single instance of MySQLConfiguration
	 */
	public static MySQLConfiguration getInstance() {
		if (cfg == null) {
			cfg = new MySQLConfiguration();
		}
		return cfg;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public static String getUserName() {
		getInstance().reload();
		return getInstance().getString("user");
	}

	/**
	 * Gets the user password.
	 * 
	 * @return the user password
	 */
	public static String getUserPassword() {
		getInstance().reload();
		return getInstance().getString("pass");
	}

	/** The cfg. */
	private static MySQLConfiguration cfg;

	/**
	 * Instantiates a new my sql configuration.
	 */
	public MySQLConfiguration() {
		super(new File("./plugins/SurvivalHeaven/mysql.yml"));
		cfg = this;
		load();
		save();
		saveDefault();
	}

	/**
	 * Save default.
	 */
	private void saveDefault() {
		if (!contains("host")) {
			set("host", "localhost");
		}
		if (!contains("port")) {
			set("port", 3306);
		}
		if (!contains("database")) {
			set("database", "SurvivalHeaven");
		}
		if (!contains("user")) {
			set("user", "root");
		}
		if (!contains("pass")) {
			set("pass", "");
		}
		save();
	}
}
