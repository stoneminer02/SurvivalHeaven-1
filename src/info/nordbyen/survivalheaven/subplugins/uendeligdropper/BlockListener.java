/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.Dispensers;

import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.SignChangeEvent;

/**
 * The listener interface for receiving block events. The class that is
 * interested in processing a block event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addBlockListener<code> method. When
 * the block event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BlockEvent
 */
public class BlockListener implements Listener {

	/**
	 * On dispenser break.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispenserBreak(final BlockBreakEvent event) {
		if ((event.isCancelled())
				|| (!(event.getBlock().getState() instanceof Dispenser))
				|| (!(event.getBlock().getState() instanceof Dropper)))
			return;
		Dispensers.getInstance();
		if (Dispensers.isDispenser(event.getBlock().getLocation())) {
			Dispensers.getInstance().getList("dispensers")
					.remove(event.getBlock().getLocation().toString());
			Dispensers.getInstance().save();
		}
	}

	/**
	 * On sign change.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignChange(final SignChangeEvent event) {
		if ((event.isCancelled()) || (event.getPlayer() == null))
			return;
		if (event.getPlayer().hasPermission("infdispenser.signs"))
			return;
		if (event.getLines()[0].equalsIgnoreCase("[infdisp]")) {
			event.getPlayer().sendMessage(
					SH.PREFIX + "Du har ikke nok rettigheter til dette");
			event.setCancelled(true);
		}
	}
}
