/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
