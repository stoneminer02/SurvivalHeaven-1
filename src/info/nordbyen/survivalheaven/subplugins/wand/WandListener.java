/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.wand;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.wand.Wand;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The listener interface for receiving wand events. The class that is
 * interested in processing a wand event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addWandListener<code> method. When
 * the wand event occurs, that object's appropriate
 * method is invoked.
 *
 * @see WandEvent
 */
public class WandListener implements Listener {

	/**
	 * On wand interact.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler
	public void onWandInteract(final PlayerInteractEvent event) {
		if (SH.getManager().getWandManager().isWand(event.getItem())) {
			final Wand wand = SH.getManager().getWandManager()
					.search(event.getItem());
			if ((event.getAction() == Action.LEFT_CLICK_AIR)
					|| (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
				wand.onLeftClick(event.getItem(), event.getPlayer(),
						event.getClickedBlock(), event.getBlockFace());
			} else if ((event.getAction() == Action.RIGHT_CLICK_AIR)
					|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				wand.onRightClick(event.getItem(), event.getPlayer(),
						event.getClickedBlock(), event.getBlockFace());
			}
		}
	}
}
