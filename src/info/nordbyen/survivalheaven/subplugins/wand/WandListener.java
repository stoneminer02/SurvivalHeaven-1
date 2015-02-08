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

package info.nordbyen.survivalheaven.subplugins.wand;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.wand.Wand;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

// TODO: Auto-generated Javadoc
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
     * @param event the event
     */
    @EventHandler
    public void onWandInteract(final PlayerInteractEvent event) {
        if (SH.getManager().getWandManager().isWand(event.getItem())) {
            final Wand wand = SH.getManager().getWandManager().search(event.getItem());
            if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
                wand.onLeftClick(event.getItem(), event.getPlayer(), event.getClickedBlock(), event.getBlockFace());
            } else if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                wand.onRightClick(event.getItem(), event.getPlayer(), event.getClickedBlock(), event.getBlockFace());
            }
        }
    }
}
