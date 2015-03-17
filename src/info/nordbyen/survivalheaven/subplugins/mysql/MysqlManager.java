/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.mysql;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * The Class MysqlManager.
 */
public final class MysqlManager implements IMysqlManager {

	/** The date format. */
	private final String DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss_SSS";
	
	/** The hostname. */
	private String hostname = "";
	
	/** The portnmbr. */
	private String portnmbr = "";
	
	/** The username. */
	private String username = "";
	
	/** The password. */
	private String password = "";
	
	/** The database. */
	private String database = "";
	
	/** The connection. */
	protected Connection connection = null;

	/**
	 * Instantiates a new mysql manager.
	 */
	public MysqlManager() {
		MySQLConfiguration.getInstance();
		this.hostname = MySQLConfiguration.getHostName();
		this.portnmbr = MySQLConfiguration.getHostPort() + "";
		this.username = MySQLConfiguration.getUserName();
		this.password = MySQLConfiguration.getUserPassword();
		this.database = MySQLConfiguration.getDatabaseName();
		open();
	}

	/**
	 * Instantiates a new mysql manager.
	 *
	 * @param hostname
	 *            the hostname
	 * @param portnmbr
	 *            the portnmbr
	 * @param database
	 *            the database
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public MysqlManager(final String hostname, final String portnmbr,
			final String database, final String username, final String password) {
		this.hostname = hostname;
		this.portnmbr = portnmbr;
		this.database = database;
		this.username = username;
		this.password = password;
		open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#checkConnection()
	 */
	@Override
	public boolean checkConnection() throws SQLException {
		if (connection.isValid(5))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#clearTable(java.lang
	 * .String)
	 */
	@Override
	public boolean clearTable(final String table) {
		Statement statement = null;
		String query = null;
		try {
			statement = this.connection.createStatement();
			query = "DELETE FROM " + table;
			SH.debug(query);
			statement.executeUpdate(query);
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.mysql.IMysqlManager#close()
	 */
	@Override
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#deleteTable(java
	 * .lang .String)
	 */
	@Override
	public boolean deleteTable(final String table) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			SH.debug("DROP TABLE " + table);
			statement.executeUpdate("DROP TABLE " + table);
			return true;
		} catch (final SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.mysql.IMysqlManager#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return this.connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#getDate(java.util.
	 * Date)
	 */
	@Override
	public String getDate(final Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#getDate(java.lang.
	 * String)
	 */
	@Override
	public Date getDate(final String date) {
		if (date.equalsIgnoreCase("no"))
			return new Date();
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.parse(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#getLocation(org.
	 * bukkit .Location)
	 */
	@Override
	public String getLocation(final Location loc) {
		final String location = loc.getWorld().getName() + ";" + loc.getX()
				+ ";" + loc.getY() + ";" + loc.getZ();
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#getLocation(java
	 * .lang .String)
	 */
	@Override
	public Location getLocation(final String loc) {
		if (loc.equalsIgnoreCase("no"))
			return Bukkit.getWorlds().get(0).getSpawnLocation();
		final String[] split = loc.split(";");
		final Location location = new Location(Bukkit.getWorld(split[0]),
				Double.parseDouble(split[1]), Double.parseDouble(split[2]),
				Double.parseDouble(split[3]));
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#insert(java.lang
	 * .String , java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public boolean insert(final String table, final Object[] column,
			final Object[] value) {
		Statement statement = null;
		final StringBuilder sb1 = new StringBuilder();
		final StringBuilder sb2 = new StringBuilder();
		for (final Object s : column) {
			sb1.append(s.toString() + ",");
		}
		for (final Object s : value) {
			sb2.append("'" + s.toString() + "',");
		}
		final String columns = sb1.toString().substring(0,
				sb1.toString().length() - 1);
		final String values = sb2.toString().substring(0,
				sb2.toString().length() - 1);
		try {
			statement = this.connection.createStatement();
			SH.debug(
					"INSERT INTO " + table + "(" + columns + ") VALUES ("
							+ values + ")");
			statement.execute("INSERT INTO " + table + "(" + columns
					+ ") VALUES (" + values + ")");
			return true;
		} catch (final SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.mysql.IMysqlManager#open()
	 */
	@Override
	public Connection open() {
		String url = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/"
					+ this.database
					+ "?autoReconnect=true&allowMultiQueries=true";
			this.connection = DriverManager.getConnection(url, this.username,
					this.password);
			return this.connection;
		} catch (final SQLException e) {
			System.out.print("Kan ikke koble til mySQL server!");
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			System.out.print("Finner ikke JDBC Driver");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#query(java.lang.
	 * String )
	 */
	@Override
	public ResultSet query(final String query) throws SQLException {
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			SH.debug(query); /* Debug */
			result = statement.executeQuery(query);
			return result;
		} catch (final SQLException e) {
			if (e.getMessage()
					.equals("Can not issue data manipulation statements with executeQuery().")) {
				try {
					statement.executeUpdate(query);
				} catch (final SQLException ex) {
					if (e.getMessage().startsWith(
							"Du har en feil i mySQL-syntaxen;")) {
						String temp = (e.getMessage().split(";")[0].substring(
								0, 36) + e.getMessage().split(";")[1]
								.substring(91));
						temp = temp.substring(0, temp.lastIndexOf("'"));
						throw new SQLException(temp);
					} else {
						ex.printStackTrace();
					}
				}
			} else if (e.getMessage().startsWith(
					"Du har en feil i mySQL-syntaxen;")) {
				String temp = (e.getMessage().split(";")[0].substring(0, 36) + e
						.getMessage().split(";")[1].substring(91));
				temp = temp.substring(0, temp.lastIndexOf("'"));
				throw new SQLException(temp);
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.mysql.IMysqlManager#query(java.lang.
	 * String , int)
	 */
	@Override
	public ResultSet query(final String query, final int ret)
			throws SQLException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(query, ret);
			SH.debug(query); /* Debug */
			statement.executeUpdate();
			result = statement.getGeneratedKeys();
			return result;
		} catch (final SQLException e) {
			if (e.getMessage()
					.equals("Can not issue data manipulation statements with executeQuery().")) {
				try {
					statement.executeUpdate(query);
				} catch (final SQLException ex) {
					if (e.getMessage().startsWith(
							"Du har en feil i mySQL-syntaxen;")) {
						String temp = (e.getMessage().split(";")[0].substring(
								0, 36) + e.getMessage().split(";")[1]
								.substring(91));
						temp = temp.substring(0, temp.lastIndexOf("'"));
						throw new SQLException(temp);
					} else {
						ex.printStackTrace();
					}
				}
			} else if (e.getMessage().startsWith(
					"Du har en feil i mySQL-syntaxen;")) {
				String temp = (e.getMessage().split(";")[0].substring(0, 36) + e
						.getMessage().split(";")[1].substring(91));
				temp = temp.substring(0, temp.lastIndexOf("'"));
				throw new SQLException(temp);
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}
}
