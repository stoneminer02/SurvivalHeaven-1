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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sql.ConnectionEvent;

/**
 * The listener interface for receiving connection events. The class that is
 * interested in processing a connection event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addConnectionListener<code> method. When
 * the connection event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ConnectionEvent
 */
public class ConnectionListener extends Thread {

	/** The plugin. */
	private final RemoteBukkitPlugin plugin;
	/** The s. */
	private final ServerSocket s;
	/** The number. */
	private int number = 0;

	/**
	 * Instantiates a new connection listener.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param port
	 *            the port
	 */
	public ConnectionListener(final RemoteBukkitPlugin plugin, final int port) {
		super("RemoteBukkit-ConnectionListener");
		setDaemon(true);
		this.plugin = plugin;
		try {
			this.s = new ServerSocket(port);
		} catch (final IOException ex) {
			throw new RuntimeException("Failed to listen on port:" + port, ex);
		}
	}

	/**
	 * Kill.
	 */
	public void kill() {
		try {
			this.s.close();
		} catch (final IOException ex) {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (!this.s.isClosed()) {
			Socket socket = null;
			try {
				socket = this.s.accept();
				final ConnectionHandler con = new ConnectionHandler(
						this.plugin, this.number++, socket);
				con.start();
			} catch (final IOException ex) {
				if (socket != null) {
					RemoteBukkitPlugin.log(
							"Exception while attempting to accept connection #"
									+ (this.number - 1) + " from "
									+ socket.getInetAddress().getHostAddress()
									+ ":" + socket.getPort(), ex);
				}
			}
		}
	}
}
