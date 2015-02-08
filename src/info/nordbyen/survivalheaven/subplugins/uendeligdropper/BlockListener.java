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

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.files.Dispensers;

import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

// TODO: Auto-generated Javadoc
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
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDispenserBreak(final BlockBreakEvent event) {
        if ((event.isCancelled()) || (!(event.getBlock().getState() instanceof Dispenser)) || (!(event.getBlock().getState() instanceof Dropper)))
            return;
        Dispensers.getInstance();
        if (Dispensers.isDispenser(event.getBlock().getLocation())) {
            Dispensers.getInstance().getList("dispensers").remove(event.getBlock().getLocation().toString());
            Dispensers.getInstance().save();
        }
    }

    /**
     * On sign change.
     * 
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(final SignChangeEvent event) {
        if ((event.isCancelled()) || (event.getPlayer() == null))
            return;
        if (event.getPlayer().hasPermission("infdispenser.signs"))
            return;
        if (event.getLines()[0].equalsIgnoreCase("[infdisp]")) {
            event.getPlayer().sendMessage(SH.PREFIX + "Du har ikke nok rettigheter til dette");
            event.setCancelled(true);
        }
    }
}
