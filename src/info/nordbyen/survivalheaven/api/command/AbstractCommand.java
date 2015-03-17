/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.command;

import info.nordbyen.survivalheaven.SH;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/**
 * The Class AbstractCommand.
 */
public abstract class AbstractCommand implements TabExecutor {

	/**
	 * The Class ReflectCommand.
	 */
	private final class ReflectCommand extends Command {

		/** The exe. */
		private TabExecutor exe = null;

		/**
		 * Instantiates a new reflect command.
		 *
		 * @param command
		 *            the command
		 */
		protected ReflectCommand(final String command) {
			super(command);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.bukkit.command.Command#execute(org.bukkit.command.CommandSender,
		 * java.lang.String, java.lang.String[])
		 */
		@Override
		public boolean execute(final CommandSender sender,
				final String commandLabel, final String[] args) {
			if (exe != null) {
				exe.onCommand(sender, this, commandLabel, args);
			}
			return false;
		}

		/**
		 * Sets the executor.
		 *
		 * @param exe
		 *            the new executor
		 */
		public void setExecutor(final TabExecutor exe) {
			this.exe = exe;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.bukkit.command.Command#tabComplete(org.bukkit.command.CommandSender
		 * , java.lang.String, java.lang.String[])
		 */
		@Override
		public List<String> tabComplete(final CommandSender sender,
				final String alias, final String[] args) {
			if (exe != null)
				return exe.onTabComplete(sender, this, alias, args);
			return null;
		}
	}

	/** The command. */
	protected final String command;
	
	/** The description. */
	protected final String description;
	
	/** The alias. */
	protected final List<String> alias;
	
	/** The usage. */
	protected final String usage;
	
	/** The perm message. */
	protected final String permMessage;
	
	/** The cmap. */
	protected static CommandMap cmap;

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 */
	public AbstractCommand(final String command) {
		this(command, null, null, null, null);
	}

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 * @param usage
	 *            the usage
	 */
	public AbstractCommand(final String command, final String usage) {
		this(command, usage, null, null, null);
	}

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 * @param usage
	 *            the usage
	 * @param description
	 *            the description
	 */
	public AbstractCommand(final String command, final String usage,
			final String description) {
		this(command, usage, description, null, null);
	}

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 * @param usage
	 *            the usage
	 * @param description
	 *            the description
	 * @param aliases
	 *            the aliases
	 */
	public AbstractCommand(final String command, final String usage,
			final String description, final List<String> aliases) {
		this(command, usage, description, null, aliases);
	}

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 * @param usage
	 *            the usage
	 * @param description
	 *            the description
	 * @param permissionMessage
	 *            the permission message
	 */
	public AbstractCommand(final String command, final String usage,
			final String description, final String permissionMessage) {
		this(command, usage, description, permissionMessage, null);
	}

	/**
	 * Instantiates a new abstract command.
	 *
	 * @param command
	 *            the command
	 * @param usage
	 *            the usage
	 * @param description
	 *            the description
	 * @param permissionMessage
	 *            the permission message
	 * @param aliases
	 *            the aliases
	 */
	public AbstractCommand(final String command, final String usage,
			final String description, final String permissionMessage,
			final List<String> aliases) {
		this.command = command.toLowerCase();
		this.usage = usage;
		this.description = description;
		this.permMessage = permissionMessage;
		this.alias = aliases;
		register();
	}

	/**
	 * Gets the command map.
	 *
	 * @return the command map
	 */
	final CommandMap getCommandMap() {
		if (cmap == null) {
			try {
				final Field f = Bukkit.getServer().getClass()
						.getDeclaredField("commandMap");
				f.setAccessible(true);
				cmap = (CommandMap) f.get(Bukkit.getServer());
				return getCommandMap();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		} else if (cmap != null)
			return cmap;
		return getCommandMap();
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param sender
	 *            the sender
	 * @param perm
	 *            the perm
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(final CommandSender sender,
			final Permission perm) {
		return sender.hasPermission(perm);
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param sender
	 *            the sender
	 * @param permission
	 *            the permission
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(final CommandSender sender,
			final String permission) {
		return sender.hasPermission(permission);
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param player
	 *            the player
	 * @param perm
	 *            the perm
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(final Player player, final Permission perm) {
		return player.hasPermission(perm);
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param player
	 *            the player
	 * @param permission
	 *            the permission
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(final Player player, final String permission) {
		return player.hasPermission(permission);
	}

	/**
	 * Checks if is player.
	 *
	 * @param sender
	 *            the sender
	 * @return true, if is player
	 */
	public boolean isPlayer(final CommandSender sender) {
		return (sender instanceof Player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public abstract boolean onCommand(CommandSender sender, Command command,
			String label, String[] args);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.command.TabCompleter#onTabComplete(org.bukkit.command.
	 * CommandSender, org.bukkit.command.Command, java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public List<String> onTabComplete(final CommandSender sender,
			final Command command, final String label, final String[] args) {
		return null;
	}

	/**
	 * Register.
	 */
	public void register() {
		((SH) SH.getManager()).commands.put(command, this);
		final ReflectCommand cmd = new ReflectCommand(this.command);
		if (this.alias != null) {
			cmd.setAliases(this.alias);
		}
		if (this.description != null) {
			cmd.setDescription(this.description);
		}
		if (this.usage != null) {
			cmd.setUsage(this.usage);
		}
		if (this.permMessage != null) {
			cmd.setPermissionMessage(this.permMessage);
		}
		getCommandMap().register("", cmd);
		cmd.setExecutor(this);
	};
}
