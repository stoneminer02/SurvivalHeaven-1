/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.irc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class Connection.
 */
public class Connection implements Runnable {

	/**
	 * The Class Channel.
	 */
	public static class Channel {

		/** The channel members. */
		private final ArrayList<Connection> channelMembers = new ArrayList<Connection>();
		
		/** The topic. */
		private String topic;
		
		/** The name. */
		protected String name;

		/**
		 * Member quit.
		 *
		 * @param nick
		 *            the nick
		 */
		public void memberQuit(final String nick) {
		}

		/**
		 * Send.
		 *
		 * @param toSend
		 *            the to send
		 */
		public void send(final String toSend) {
			sendNot(null, toSend);
		}

		/**
		 * Send not.
		 *
		 * @param not
		 *            the not
		 * @param toSend
		 *            the to send
		 */
		public void sendNot(final Connection not, final String toSend) {
			synchronized (mutex) {
				for (final Connection con : channelMembers) {
					if (con != not) {
						con.send(toSend);
					}
				}
			}
		}
	}

	/**
	 * The Enum Command.
	 */
	public enum Command {
		
		/** The nick. */
		NICK(1, 1) {

			private void doFirstTimeNick(final Connection con, final String nick)
					throws InterruptedException {
				con.nick = filterAllowedNick(nick);
				synchronized (mutex) {
					connectionMap.put(con.nick, con);
				}
				/*
				 * Now we send the user a welcome message and everything.
				 */
			}

			private void doSelfSwitchNick(final Connection con,
					final String nick) {
				synchronized (mutex) {
					final String oldNick = con.nick;
					con.nick = filterAllowedNick(nick);
					connectionMap.remove(oldNick);
					connectionMap.put(con.nick, con);
					con.send(":" + oldNick + "!" + con.username + "@"
							+ con.hostname + " NICK :" + con.nick);
					/*
					 * Now we need to notify all channels that we are on
					 */
					for (final Channel c : channelMap.values()) {
						if (c.channelMembers.contains(con)) {
							c.sendNot(con, ":" + oldNick + "!" + con.username
									+ "@" + con.hostname + " NICK :" + con.nick);
						}
					}
				}
			}

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				if (con.nick == null) {
					doFirstTimeNick(con, arguments[0]);
				} else {
					doSelfSwitchNick(con, arguments[0]);
				}
			}
		},
		
		/** The user. */
		USER(1, 4) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				if (con.username != null) {
					con.send("NOTICE AUTH :You can't change your user "
							+ "information after you've logged in right now.");
					return;
				}
				con.username = arguments[0];
				final String forDescription = arguments.length > 3 ? arguments[3]
						: "(no description)";
				con.description = forDescription;
				/*
				 * Now we'll send the user their initial information.
				 */
				con.sendGlobal("001 " + con.nick + " :Welcome to "
						+ globalServerName + ", a Jircs-powered IRC network.");
				con.sendGlobal("004 " + con.nick + " " + globalServerName
						+ " Jircs");
				con.sendGlobal("375 " + con.nick + " :- " + globalServerName
						+ " Message of the Day -");
				con.sendGlobal("372 " + con.nick + " :- Hello. Welcome to "
						+ globalServerName + ", a Jircs-powered IRC network.");
				con.sendGlobal("372 "
						+ con.nick
						+ " :- See http://code.google.com/p/jwutils/wiki/Jircs "
						+ "for more info on Jircs.");
				con.sendGlobal("376 " + con.nick + " :End of /MOTD command.");
			}
		},
		
		/** The ping. */
		PING(1, 1) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				con.send(":" + globalServerName + " PONG " + globalServerName
						+ " :" + arguments[0]);
			}
		},
		
		/** The join. */
		JOIN(1, 2) {

			public void doJoin(final Connection con, final String channelName) {
				if (!channelName.startsWith("#")) {
					con.sendSelfNotice("This server only allows channel names that "
							+ "start with a # sign.");
					return;
				}
				if (channelName.contains(" ")) {
					con.sendSelfNotice("This server does not allow spaces in channel names.");
				}
				synchronized (mutex) {
					Channel channel = channelMap.get(channelName);
					boolean added = false;
					if (channel == null) {
						added = true;
						channel = new Channel();
						channel.name = channelName;
						channelMap.put(channelName, channel);
					}
					if (channel.channelMembers.contains(con)) {
						con.sendSelfNotice("You're already a member of "
								+ channelName);
						return;
					}
					channel.channelMembers.add(con);
					channel.send(":" + con.getRepresentation() + " JOIN "
							+ channelName);
					if (added) {
						con.sendGlobal("MODE " + channelName + " +nt");
					}
					// This is commented out because channel.send takes care of
					// this for us
					// con.send(":" + con.getRepresentation() + " JOIN "
					// + channelName);
					if (channel.topic != null) {
						con.sendGlobal("332 " + con.nick + " " + channel.name
								+ " :" + channel.topic);
					} else {
						con.sendGlobal("331 " + con.nick + " " + channel.name
								+ " :No topic is set");
					}
					for (final Connection channelMember : channel.channelMembers) {// 353,366
						con.sendGlobal("353 " + con.nick + " = " + channelName
								+ " :" + channelMember.nick);
					}
					con.sendGlobal("366 " + con.nick + " " + channelName
							+ " :End of /NAMES list");
				}
			}

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				if (arguments.length == 2) {
					con.sendSelfNotice("This server does not support "
							+ "channel keys at "
							+ "this time. JOIN will act as if you "
							+ "hadn't specified any keys.");
				}
				final String[] channelNames = arguments[0].split(",");
				for (final String channelName : channelNames) {
					if (!channelName.startsWith("#")) {
						con.sendSelfNotice("This server only allows "
								+ "channel names that "
								+ "start with a # sign.");
						return;
					}
					if (channelName.contains(" ")) {
						con.sendSelfNotice("This server does not allow spaces "
								+ "in channel names.");
						return;
					}
				}
				for (final String channelName : channelNames) {
					doJoin(con, channelName);
				}
			}
		},
		
		/** The who. */
		WHO(0, 2) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				if (arguments.length > 1) {
					con.sendSelfNotice("Filtering by operator only using the WHO "
							+ "command isn't yet supported. WHO will act "
							+ "as if \"o\" has not been specified.");
				}
				String person = "";
				if (arguments.length > 0) {
					person = arguments[0];
				}
				synchronized (mutex) {
					final Channel channel = channelMap.get(person);
					if (channel != null) {
						for (final Connection channelMember : channel.channelMembers) {
							con.sendGlobal("352 " + con.nick + " " + person
									+ " " + channelMember.username + " "
									+ channelMember.hostname + " "
									+ globalServerName + " "
									+ channelMember.nick + " H :0 "
									+ channelMember.description);
						}
					} else {
						con.sendSelfNotice("WHO with something other than a channel "
								+ "as arguments is not supported right now. "
								+ "WHO will display an empty list of people.");
					}
				}
				con.send("315 " + con.nick + " " + person
						+ " :End of /WHO list.");
			}
		},
		
		/** The userhost. */
		USERHOST(1, 5) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				final ArrayList<String> replies = new ArrayList<String>();
				for (final String s : arguments) {
					final Connection user = connectionMap.get(s);
					if (user != null) {
						replies.add(user.nick + "=+" + user.username + "@"
								+ user.hostname);
					}
				}
				con.sendGlobal("302 " + con.nick + " :"
						+ delimited(replies.toArray(new String[0]), " "));
			}
		},
		
		/** The mode. */
		MODE(0, 2) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				if (arguments.length == 1) {
					if (arguments[0].startsWith("#")) {
						con.sendGlobal("324 " + con.nick + " " + arguments[0]
								+ " +nt");
					} else {
						con.sendSelfNotice("User mode querying not supported yet.");
					}
				} else if ((arguments.length == 2)
						&& (arguments[1].equals("+b") || arguments[1]
								.equals("+e"))) {
					if (arguments[0].startsWith("#")) {// 368,349
						if (arguments[1].equals("+b")) {
							con.sendGlobal("368 " + con.nick + " "
									+ arguments[0]
									+ " :End of channel ban list");
						} else {
							con.sendGlobal("349 " + con.nick + " "
									+ arguments[0]
									+ " :End of channel exception list");
						}
					} else {
						con.sendSelfNotice("User mode setting not supported yet for +b or +e.");
					}
				} else {
					con.sendSelfNotice("Specific modes not supported yet.");
				}
			}
		},
		
		/** The part. */
		PART(1, 2) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				final String[] channels = arguments[0].split(",");
				for (final String channelName : channels) {
					synchronized (mutex) {
						final Channel channel = channelMap.get(channelName);
						if (channelName == null) {
							con.sendSelfNotice("You're not a member of the channel "
									+ channelName + ", so you can't part it.");
						} else {
							channel.send(":" + con.getRepresentation()
									+ " PART " + channelName);
							channel.channelMembers.remove(con);
							if (channel.channelMembers.size() == 0) {
								channelMap.remove(channelName);
							}
						}
					}
				}
			}
		},
		
		/** The quit. */
		QUIT(1, 1) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				con.sendQuit("Quit: " + arguments[0]);
			}
		},
		
		/** The privmsg. */
		PRIVMSG(2, 2) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				final String[] recipients = arguments[0].split(",");
				final String message = arguments[1];
				for (final String recipient : recipients) {
					if (recipient.startsWith("#")) {
						final Channel channel = channelMap.get(recipient);
						if (channel == null) {
							con.sendSelfNotice("No such channel, so can't send "
									+ "a message to it: " + recipient);
						} else if (!channel.channelMembers.contains(con)) {
							con.sendSelfNotice("You can't send messages to "
									+ "channels you're not at.");
						} else {
							channel.sendNot(con, ":" + con.getRepresentation()
									+ " PRIVMSG " + recipient + " :" + message);
						}
					} else {
						final Connection recipientConnection = connectionMap
								.get(recipient);
						if (recipientConnection == null) {
							con.sendSelfNotice("The user " + recipient
									+ " is not online.");
						} else {
							recipientConnection.send(":"
									+ con.getRepresentation() + " PRIVMSG "
									+ recipient + " :" + message);
						}
					}
				}
			}
		},
		
		/** The topic. */
		TOPIC(1, 2) {

			@Override
			public void run(final Connection con, final String prefix,
					final String[] arguments) throws Exception {
				final Channel channel = channelMap.get(arguments[0]);
				if (channel == null) {
					con.sendSelfNotice("No such channel for topic viewing: "
							+ arguments[0]);
					return;
				}
				if (arguments.length == 1) {
					/*
					 * The user wants to see the channel topic.
					 */
					if (channel.topic != null) {
						con.sendGlobal("332 " + con.nick + " " + channel.name
								+ " :" + channel.topic);
					} else {
						con.sendGlobal("331 " + con.nick + " " + channel.name
								+ " :No topic is set");
					}
				} else {
					/*
					 * The user wants to set the channel topic.
					 */
					channel.topic = arguments[1];
					channel.sendNot(con, ":" + con.getRepresentation()
							+ " TOPIC " + channel.name + " :" + channel.topic);
				}
			}
		};

		/** The min argument count. */
		private int minArgumentCount;
		
		/** The max argument count. */
		private int maxArgumentCount;

		/**
		 * Instantiates a new command.
		 *
		 * @param min
		 *            the min
		 * @param max
		 *            the max
		 */
		private Command(final int min, final int max) {
			minArgumentCount = min;
			maxArgumentCount = max;
		}

		/**
		 * Gets the max.
		 *
		 * @return the max
		 */
		public int getMax() {
			return maxArgumentCount;
		}

		/**
		 * Gets the min.
		 *
		 * @return the min
		 */
		public int getMin() {
			return minArgumentCount;
		}

		/**
		 * Run.
		 *
		 * @param con
		 *            the con
		 * @param prefix
		 *            the prefix
		 * @param arguments
		 *            the arguments
		 * @throws Exception
		 *             the exception
		 */
		public abstract void run(Connection con, String prefix,
				String[] arguments) throws Exception;
	}

	/**
	 * Delimited.
	 *
	 * @param items
	 *            the items
	 * @param delimiter
	 *            the delimiter
	 * @return the string
	 */
	public static String delimited(final String[] items, final String delimiter) {
		final StringBuffer response = new StringBuffer();
		boolean first = true;
		for (final String s : items) {
			if (first) {
				first = false;
			} else {
				response.append(delimiter);
			}
			response.append(s);
		}
		return response.toString();
	}

	/**
	 * Filter allowed nick.
	 *
	 * @param theNick
	 *            the the nick
	 * @return the string
	 */
	public static String filterAllowedNick(final String theNick) {
		return theNick.replace(":", "").replace(" ", "").replace("!", "")
				.replace("@", "").replace("#", "");
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(final String[] args) throws Throwable {
		if (args.length == 0) {
			System.out.println("Usage: java jw.jircs.Connection <servername>");
			return;
		}
		globalServerName = args[0];
		try (ServerSocket ss = new ServerSocket(6667)) {
			while (true) {
				final Socket s = ss.accept();
				final Connection jircs = new Connection(s);
				final Thread thread = new Thread(jircs);
				thread.start();
			}
		} catch (final Exception e) {
		}
	}

	/**
	 * Start server.
	 *
	 * @param name
	 *            the name
	 * @throws Throwable
	 *             the throwable
	 */
	public static void startServer(final String name) throws Throwable {
		globalServerName = name;
		try (ServerSocket ss = new ServerSocket(6667)) {
			while (true) {
				final Socket s = ss.accept();
				final Connection jircs = new Connection(s);
				final Thread thread = new Thread(jircs);
				thread.start();
			}
		} catch (final Exception e) {
		}
	}

	/** The Constant mutex. */
	public static final Object mutex = new Object();
	
	/** The socket. */
	private final Socket socket;
	
	/** The username. */
	private String username;
	
	/** The hostname. */
	private String hostname;
	
	/** The nick. */
	private String nick;
	
	/** The description. */
	private String description;
	
	/** The connection map. */
	public static Map<String, Connection> connectionMap = new HashMap<String, Connection>();
	
	/** The channel map. */
	public static Map<String, Channel> channelMap = new HashMap<String, Channel>();

	/** The global server name. */
	private static String globalServerName;

	/** The out queue. */
	private LinkedBlockingQueue<String> outQueue = new LinkedBlockingQueue<String>(
			1000);
	
	/** The out thread. */
	private final Thread outThread = new Thread() {

		@Override
		public void run() {
			try {
				final OutputStream out = socket.getOutputStream();
				while (true) {
					String s = outQueue.take();
					s = s.replace("\n", "").replace("\r", "");
					s = s + "\r\n";
					out.write(s.getBytes());
					out.flush();
				}
			} catch (final Exception e) {
				System.out.println("Outqueue died");
				outQueue.clear();
				outQueue = null;
				e.printStackTrace();
				try {
					socket.close();
				} catch (final Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	};

	/**
	 * Instantiates a new connection.
	 *
	 * @param socket
	 *            the socket
	 */
	public Connection(final Socket socket) {
		this.socket = socket;
	}

	/**
	 * Do server.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void doServer() throws Exception {
		final InetSocketAddress address = (InetSocketAddress) socket
				.getRemoteSocketAddress();
		hostname = address.getAddress().getHostAddress();
		System.out.println("Connection from host " + hostname);
		outThread.start();
		final InputStream socketIn = socket.getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				socketIn));
		String line;
		while ((line = reader.readLine()) != null) {
			processLine(line);
		}
	}

	/**
	 * Gets the representation.
	 *
	 * @return the representation
	 */
	public String getRepresentation() {
		return nick + "!" + username + "@" + hostname;
	}

	/**
	 * Pad split.
	 *
	 * @param line
	 *            the line
	 * @param regex
	 *            the regex
	 * @param max
	 *            the max
	 * @return the string[]
	 */
	@SuppressWarnings("unused")
	private String[] padSplit(final String line, final String regex,
			final int max) {
		final String[] split = line.split(regex);
		final String[] output = new String[max];
		for (int i = 0; i < output.length; i++) {
			output[i] = "";
		}
		for (int i = 0; i < split.length; i++) {
			output[i] = split[i];
		}
		return output;
	}

	/**
	 * Process line.
	 *
	 * @param line
	 *            the line
	 * @throws Exception
	 *             the exception
	 */
	private void processLine(String line) throws Exception {
		System.out.println("Processing line from " + nick + ": " + line);
		String prefix = "";
		if (line.startsWith(":")) {
			final String[] tokens = line.split(" ", 2);
			prefix = tokens[0];
			line = (tokens.length > 1 ? tokens[1] : "");
		}
		final String[] tokens1 = line.split(" ", 2);
		String command = tokens1[0];
		line = tokens1.length > 1 ? tokens1[1] : "";
		final String[] tokens2 = line.split("(^| )\\:", 2);
		String trailing = null;
		line = tokens2[0];
		if (tokens2.length > 1) {
			trailing = tokens2[1];
		}
		final ArrayList<String> argumentList = new ArrayList<String>();
		if (!line.equals("")) {
			argumentList.addAll(Arrays.asList(line.split(" ")));
		}
		if (trailing != null) {
			argumentList.add(trailing);
		}
		final String[] arguments = argumentList.toArray(new String[0]);
		/*
		 * Now we actually process the command.
		 */
		if (command.matches("[0-9][0-9][0-9]")) {
			command = "n" + command;
		}
		Command commandObject = null;
		try {
			Command.valueOf(command.toLowerCase());
		} catch (final Exception e) {
		}
		if (commandObject == null) {
			try {
				commandObject = Command.valueOf(command.toUpperCase());
			} catch (final Exception e) {
			}
		}
		if (commandObject == null) {
			sendSelfNotice("That command (" + command
					+ ") isnt a supported command at this server.");
			return;
		}
		if ((arguments.length < commandObject.getMin())
				|| (arguments.length > commandObject.getMax())) {
			sendSelfNotice("Invalid number of arguments for this"
					+ " command, expected not more than "
					+ commandObject.getMax() + " and not less than "
					+ commandObject.getMin() + " but got " + arguments.length
					+ " arguments");
			return;
		}
		commandObject.run(this, prefix, arguments);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			doServer();
		} catch (final Exception e) {
			try {
				socket.close();
			} catch (final Exception e2) {
			}
			e.printStackTrace();
		} finally {
			if ((nick != null) && (connectionMap.get(nick) == this)) {
				sendQuit("Client disconnected");
			}
		}
	}

	/**
	 * Send.
	 *
	 * @param s
	 *            the s
	 */
	public void send(final String s) {
		final Queue<String> testQueue = outQueue;
		if (testQueue != null) {
			System.out.println("Sending line to " + nick + ": " + s);
			testQueue.add(s);
		}
	}

	/**
	 * Send global.
	 *
	 * @param string
	 *            the string
	 */
	protected void sendGlobal(final String string) {
		send(":" + globalServerName + " " + string);
	}

	/**
	 * Send quit.
	 *
	 * @param quitMessage
	 *            the quit message
	 */
	protected void sendQuit(final String quitMessage) {
		synchronized (mutex) {
			for (final String channelName : new ArrayList<String>(
					channelMap.keySet())) {
				final Channel channel = channelMap.get(channelName);
				channel.channelMembers.remove(this);
				channel.send(":" + getRepresentation() + " QUIT :"
						+ quitMessage);
				if (channel.channelMembers.size() == 0) {
					channelMap.remove(channel.name);
				}
			}
		}
	}

	/**
	 * Send self notice.
	 *
	 * @param string
	 *            the string
	 */
	private void sendSelfNotice(final String string) {
		send(":" + globalServerName + " NOTICE " + nick + " :" + string);
	}
}
