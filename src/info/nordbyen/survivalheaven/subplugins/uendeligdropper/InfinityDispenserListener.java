/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
     * @param event the event
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
        if ((block.getType() == Material.DISPENSER) && ((Dispensers.isDispenser(block.getLocation())) || (Util.getSign(block)))) {
            final Dispenser disp = (Dispenser) block.getState();
            disp.getInventory().remove(Material.FLINT_AND_STEEL);
            disp.getInventory().addItem(new ItemStack[] { new ItemStack(Material.FLINT_AND_STEEL) });
            disp.update();
        }
    }

    /**
     * On dispenser use item.
     * 
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDispenserUseItem(final BlockDispenseEvent event) {
        if (event.isCancelled())
            return;
        if ((event.getBlock().getType() != Material.DISPENSER) && (event.getBlock().getType() != Material.DROPPER))
            return;
        final Block block = event.getBlock();
        if (block.getType() == Material.DISPENSER) {
            if ((Dispensers.isDispenser(block.getLocation())) || (Util.getSign(block))) {
                final Dispenser disp = (Dispenser) block.getState();
                disp.getInventory().addItem(new ItemStack[] { event.getItem() });
                disp.update();
            }
        } else {
            try {
                if ((Dispensers.isDispenser(block.getLocation())) || (Util.getSign(block))) {
                    final Dropper drop = (Dropper) block.getState();
                    drop.getInventory().addItem(new ItemStack[] { event.getItem() });
                    drop.update();
                }
            } catch (final ClassCastException localClassCastException) {
            }
        }
    }
}
