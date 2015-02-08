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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * The Class ConnectionHandler.
 */
public class ConnectionHandler extends Thread {

	/** The plugin. */
	private final RemoteBukkitPlugin plugin;
	/** The number. */
	private final int number;
	/** The socket. */
	private final Socket socket;
	/** The out. */
	private final PrintStream out;
	/** The directive. */
	private Directive directive;
	/** The killed. */
	private volatile boolean killed = false;

	/**
	 * Instantiates a new connection handler.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param number
	 *            the number
	 * @param socket
	 *            the socket
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ConnectionHandler(final RemoteBukkitPlugin plugin, final int number,
			final Socket socket) throws IOException {
		super("RemoteBukkit-ConnectionHandler");
		setDaemon(true);
		this.plugin = plugin;
		this.number = number;
		this.socket = socket;
		this.out = new PrintStream(socket.getOutputStream());
	}

	/**
	 * Gets the number.
	 * 
	 * @return the number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Gets the socket.
	 * 
	 * @return the socket
	 */
	public Socket getSocket() {
		return this.socket;
	}

	/**
	 * Kill.
	 */
	public void kill() {
		if (this.killed)
			return;
		this.killed = true;
		this.plugin.didCloseConnection(this);
		try {
			this.socket.close();
		} catch (final IOException ex) {
		}
	}

	/**
	 * Kill.
	 * 
	 * @param reason
	 *            the reason
	 */
	public void kill(final String reason) {
		this.directive = Directive.INTERACTIVE;
		send("\nRemoteBukkit closing connection because:");
		send(reason);
		kill();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		RemoteBukkitPlugin.log("Connection #" + this.number + " from "
				+ this.socket.getInetAddress().getHostAddress() + ":"
				+ this.socket.getPort() + " was accepted.");
		try {
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			final String user = in.readLine();
			final String pass = in.readLine();
			if ((user == null) || (pass == null))
				throw new IOException(
						"Connection terminated before all credentials could be sent!");
			if (this.plugin.areValidCredentials(user, pass)) {
				final String raw = in.readLine();
				if (raw == null)
					throw new IOException(
							"Connection terminated before connection directive could be recieved!");
				this.directive = Directive.toDirective(raw);
				if (this.directive == null) {
					RemoteBukkitPlugin
							.log("Connection #"
									+ this.number
									+ " from "
									+ this.socket.getInetAddress()
											.getHostAddress()
									+ ":"
									+ this.socket.getPort()
									+ " requested the use of an unsupported directive (\""
									+ raw + "\").");
					kill("Unsported directive \"" + raw + "\".");
				} else {
					this.plugin.didEstablishConnection(this, this.directive);
					while (true) {
						final String input = in.readLine();
						if (input == null) {
							break;
						}
						if (this.plugin.doVerboseLogging()) {
							RemoteBukkitPlugin.log("Connection #"
									+ this.number
									+ " from "
									+ this.socket.getInetAddress()
											.getHostAddress() + ":"
									+ this.socket.getPort()
									+ " dispatched command: " + input);
						}
						this.plugin
								.getPlugin()
								.getServer()
								.getScheduler()
								.scheduleSyncDelayedTask(
										this.plugin.getPlugin(),
										new Runnable() {

											@Override
											public void run() {
												ConnectionHandler.this.plugin
														.getPlugin()
														.getServer()
														.dispatchCommand(
																ConnectionHandler.this.plugin
																		.getPlugin()
																		.getServer()
																		.getConsoleSender(),
																input);
											}
										});
					}
				}
			} else {
				RemoteBukkitPlugin
						.log("Connection #"
								+ this.number
								+ " from "
								+ this.socket.getInetAddress().getHostAddress()
								+ ":"
								+ this.socket.getPort()
								+ " attempted to authenticate using incorrect credentials.");
				kill("Incorrect credentials.");
			}
		} catch (final IOException ex) {
			RemoteBukkitPlugin.log("Connection #" + this.number + " from "
					+ this.socket.getInetAddress().getHostAddress() + ":"
					+ this.socket.getPort()
					+ " abruptly closed the connection during authentication.");
		}
		kill();
	}

	/**
	 * Send.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void send(final String msg) {
		if (this.directive != Directive.NOLOG) {
			this.out.println(msg);
		}
	}
}
