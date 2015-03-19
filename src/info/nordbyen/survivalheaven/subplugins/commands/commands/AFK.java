/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.SH;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * The Class AFK.
 */
@SuppressWarnings("deprecation")
public class AFK implements CommandExecutor
{

	/**
	 * The listener interface for receiving AFK events. The class that is
	 * interested in processing a AFK event implements this interface, and the
	 * object created with that class is registered with a component using the
	 * component's <code>addAFKListener<code> method. When
	 * the AFK event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see AFKEvent
	 */
	public class AFKListener implements Listener
	{

		/**
		 * On move.
		 *
		 * @param e
		 *            the e
		 */
		@EventHandler
		public void onMove(PlayerMoveEvent e)
		{
			Location from = e.getFrom();
			Location to = e.getTo();
			if (from == to)
				return;
			hashmap.remove(e.getPlayer().getName());
		}

		/**
		 * On chat.
		 * 
		 * @param e
		 *            the event
		 */
		@EventHandler
		public void onChat(PlayerChatEvent e)
		{
			if (e.getMessage() == null)
				return;
			hashmap.remove(e.getPlayer().getName());
		}
	}

	/** The hashmap. */
	public static List<String> hashmap = new ArrayList<String>();

	/**
	 * Instantiates a new afk.
	 */
	public AFK()
	{
		Bukkit.getPluginManager().registerEvents(new AFKListener(),
				SH.getPlugin());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Du må være en spiller.");
			return true;
		}
		if ((sender instanceof Player))
		{
			final String player = sender.getName();
			if (!hashmap.contains(player))
			{
				hashmap.add(player);
				Bukkit.broadcastMessage(ChatColor.DARK_RED + player
						+ ChatColor.RED + " er nå AFK.");
				return true;
			} else
			{
				hashmap.remove(player);
				Bukkit.broadcastMessage(ChatColor.DARK_GREEN + player
						+ ChatColor.GREEN + " er ikke lenger AFK.");
				return true;
			}
		}
		return false;
	}
}
