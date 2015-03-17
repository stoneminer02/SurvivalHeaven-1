/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.uendeligdropper;

import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.Dispensers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The listener interface for receiving infinityDispenser events. The class that
 * is interested in processing a infinityDispenser event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addInfinityDispenserListener<code> method. When
 * the infinityDispenser event occurs, that object's appropriate
 * method is invoked.
 *
 * @see InfinityDispenserEvent
 */
public class InfinityDispenserListener implements Listener {

	/**
	 * On dispenser ignite block.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispenserIgniteBlock(final BlockIgniteEvent event) {
		if ((event.isCancelled()) || (event.getIgnitingBlock() == null))
			return;
		if (event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)
			return;
		if (!(event.getIgnitingBlock().getState() instanceof Dispenser))
			return;
		final Block block = event.getIgnitingBlock();
		if ((block.getType() == Material.DISPENSER)
				&& ((Dispensers.isDispenser(block.getLocation())) || (Util
						.getSign(block)))) {
			final Dispenser disp = (Dispenser) block.getState();
			disp.getInventory().remove(Material.FLINT_AND_STEEL);
			disp.getInventory()
					.addItem(
							new ItemStack[] { new ItemStack(
									Material.FLINT_AND_STEEL) });
			disp.update();
		}
	}

	/**
	 * On dispenser use item.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispenserUseItem(final BlockDispenseEvent event) {
		if (event.isCancelled())
			return;
		if ((event.getBlock().getType() != Material.DISPENSER)
				&& (event.getBlock().getType() != Material.DROPPER))
			return;
		final Block block = event.getBlock();
		if (block.getType() == Material.DISPENSER) {
			if ((Dispensers.isDispenser(block.getLocation()))
					|| (Util.getSign(block))) {
				final Dispenser disp = (Dispenser) block.getState();
				disp.getInventory()
						.addItem(new ItemStack[] { event.getItem() });
				disp.update();
			}
		} else {
			try {
				if ((Dispensers.isDispenser(block.getLocation()))
						|| (Util.getSign(block))) {
					final Dropper drop = (Dropper) block.getState();
					drop.getInventory().addItem(
							new ItemStack[] { event.getItem() });
					drop.update();
				}
			} catch (final ClassCastException localClassCastException) {
			}
		}
	}
}
