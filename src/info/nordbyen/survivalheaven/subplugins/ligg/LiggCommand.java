/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.ligg;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class LiggCommand.
 */
public class LiggCommand extends AbstractCommand {

	/**
	 * Clear command.
	 */
	public static void clearCommand() {
		instance = null;
	}

	/**
	 * Inits the command.
	 */
	public static void initCommand() {
		if (instance == null) {
			instance = new LiggCommand("ligg");
		}
	}

	/** The instance. */
	private static LiggCommand instance;

	/**
	 * Instantiates a new ligg command.
	 *
	 * @param command
	 *            the command
	 */
	public LiggCommand(final String command) {
		super(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.command.AbstractCommand#onCommand(org.
	 * bukkit.command .CommandSender, org.bukkit.command.Command,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String label, final String[] args) {
		if (args.length > 0) {
			sender.sendMessage("This command doesn't accept any arguments.");
			return true;
		}
		if (sender instanceof Player) {
			Ligg.getInstance().performAction((Player) sender);
		} else {
			Ligg.getInstance().performAction(sender);
		}
		return true;
	}
}
