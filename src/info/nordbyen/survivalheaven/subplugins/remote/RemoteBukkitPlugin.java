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

package info.nordbyen.survivalheaven.subplugins.remote;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;

/**
 * The Class RemoteBukkitPlugin.
 */
public class RemoteBukkitPlugin extends SubPlugin {

	/** The Constant log. */
	private static final java.util.logging.Logger log = java.util.logging.Logger
			.getLogger("Minecraft-Server");
	/** The Constant logger. */
	private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager
			.getRootLogger();
	/** The Constant oldMsgs. */
	private static final ArrayList<String> oldMsgs = new ArrayList<String>();

	/**
	 * Log.
	 * 
	 * @param msg
	 *            the msg
	 */
	public static void log(final String msg) {
		log.log(Level.INFO, "[REMOTE] " + msg);
	}

	/**
	 * Log.
	 * 
	 * @param msg
	 *            the msg
	 * @param ex
	 *            the ex
	 */
	public static void log(final String msg, final IOException ex) {
		log.log(Level.INFO, "[REMOTE] " + msg, ex);
	}

	/** The verbose. */
	private boolean verbose;
	/** The connections. */
	private final ArrayList<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();
	/** The users. */
	private final ArrayList<User> users = new ArrayList<User>();
	/** The appender. */
	private LogAppender appender;
	/** The listener. */
	private ConnectionListener listener;
	/** The logsize. */
	private int logsize;

	/**
	 * Instantiates a new remote bukkit plugin.
	 * 
	 * @param name
	 *            the name
	 */
	public RemoteBukkitPlugin(final String name) {
		super(name);
	}

	/**
	 * Are valid credentials.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return true, if successful
	 */
	public boolean areValidCredentials(final String username,
			final String password) {
		for (final User user : this.users) {
			if ((user.getUsername().equals(username))
					&& (user.getPassword().equals(password)))
				return true;
		}
		return false;
	}

	/**
	 * Broadcast.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void broadcast(final String msg) {
		synchronized (oldMsgs) {
			oldMsgs.add(msg);
			if (oldMsgs.size() > this.logsize) {
				oldMsgs.remove(this.logsize == 0 ? 0 : 1);
			}
		}
		for (final ConnectionHandler con : new ArrayList<ConnectionHandler>(
				this.connections)) {
			con.send(msg);
		}
	}

	/**
	 * Did close connection.
	 * 
	 * @param con
	 *            the con
	 */
	public void didCloseConnection(final ConnectionHandler con) {
		log("Connection #" + con.getNumber() + " from "
				+ con.getSocket().getInetAddress().getHostAddress() + ":"
				+ con.getSocket().getPort() + " was closed.");
		this.connections.remove(con);
	}

	/**
	 * Did establish connection.
	 * 
	 * @param con
	 *            the con
	 * @param directive
	 *            the directive
	 */
	public void didEstablishConnection(final ConnectionHandler con,
			final Directive directive) {
		log("Connection #" + con.getNumber() + " from "
				+ con.getSocket().getInetAddress().getHostAddress() + ":"
				+ con.getSocket().getPort() + " was successfully established.");
		this.connections.add(con);
		if (directive == Directive.NOLOG) {
			con.send("Connection was successfully established.");
		} else {
			synchronized (oldMsgs) {
				for (final String msg : oldMsgs) {
					con.send(msg);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
		logger.removeAppender(this.appender);
		this.listener.kill();
		for (final ConnectionHandler con : new ArrayList<ConnectionHandler>(
				this.connections)) {
			con.kill("Plugin is being disabled!");
		}
	}

	/**
	 * Do verbose logging.
	 * 
	 * @return true, if successful
	 */
	public boolean doVerboseLogging() {
		return this.verbose;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void enable() {
		this.appender = new LogAppender(this);
		logger.addAppender(this.appender);
		int port = 25564;
		try {
			int num = 0;
			ArrayList<Map<String, String>> usersSection = null;
			try {
				usersSection = (ArrayList<Map<String, String>>) RemoteBukkitConfig
						.getInstance().getList("users");
			} catch (final Exception e) {
			}
			if (usersSection != null) {
				for (final Map<String, String> entry : usersSection) {
					num++;
					try {
						final Object rawUsername = entry.get("user");
						String username;
						if ((rawUsername instanceof String)) {
							username = (String) rawUsername;
						} else {
							if ((rawUsername instanceof Integer)) {
								username = ((Integer) rawUsername).toString();
							} else {
								log.log(Level.WARNING,
										"[REMOTE] Illegal or no username specified for entry #"
												+ num
												+ ", defaulting to \"username\"");
								continue;
							}
						}
						final Object rawPassword = entry.get("pass");
						String password;
						if ((rawPassword instanceof String)) {
							password = (String) rawPassword;
						} else {
							if ((rawPassword instanceof Integer)) {
								password = ((Integer) rawPassword).toString();
							} else {
								log.log(Level.WARNING,
										"[REMOTE] Illegal or no password specified for entry #"
												+ num
												+ ", defaulting to \"password\"");
								continue;
							}
						}
						this.users.add(new User(username, password));
					} catch (final Exception e) {
						log.log(Level.WARNING,
								"[REMOTE] Could not parse user entry #"
										+ num
										+ ", ignoring it (this entry will be deleted).");
					}
				}
			} else {
				System.out.println("NULLL! OMGPOY!");
			}
			if (this.users.isEmpty()) {
				log.log(Level.WARNING,
						"[REMOTE] No user entries could be successfully parsed or no entries were provided. A default entry has been added (username = \"username\", password = \"password\").");
				this.users.add(new User("username", "password"));
			}
			port = RemoteBukkitConfig.getInstance().getInt("port");
			if (port <= 1024) {
				log.log(Level.WARNING,
						"[REMOTE] Illegal or no port specified (must be greater than 1024), using default port 25564");
				port = 25564;
			}
			this.verbose = RemoteBukkitConfig.getInstance().getBoolean(
					"verbose");
			final Object logsizeRaw = RemoteBukkitConfig.getInstance().get(
					"logsize");
			if ((logsizeRaw instanceof Integer)) {
				this.logsize = ((Integer) logsizeRaw).intValue();
			} else {
				log.log(Level.WARNING,
						"[REMOTE] Illegal or no maximum logsize specified (must be greater than or equal to 0), defaulting to \"500\"");
				this.logsize = 500;
			}
		} catch (final Exception ex) {
			log.log(Level.SEVERE,
					"[REMOTE] Fatal error while parsing configuration file. The defaults have been assumed. PLEASE REPORT THIS ERROR AS AN ISSUE AT: http://github.com/escortkeel/RemoteBukkit/issues",
					ex);
			this.users.clear();
			this.users.add(new User("username", "password"));
			this.logsize = 500;
		}
		this.listener = new ConnectionListener(this, port);
		this.listener.start();
		getPlugin().saveConfig();
	}
}
