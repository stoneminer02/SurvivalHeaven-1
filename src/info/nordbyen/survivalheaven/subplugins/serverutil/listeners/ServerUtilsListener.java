/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.serverutil.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * The listener interface for receiving serverUtils events. The class that is
 * interested in processing a serverUtils event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addServerUtilsListener<code> method. When
 * the serverUtils event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ServerUtilsEvent
 */
public class ServerUtilsListener implements Listener {

	/**
	 * On sign change.
	 *
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onSignChange(final SignChangeEvent e) {
		if (e.getPlayer().hasPermission("sh.colorsign")) {
			final String[] lines = e.getLines();
			for (int i = 0; i < lines.length; i++) {
				e.setLine(i,
						ChatColor.translateAlternateColorCodes('&', lines[i]));
			}
		}
	}
}
