/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
