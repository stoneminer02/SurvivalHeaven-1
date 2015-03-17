/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
