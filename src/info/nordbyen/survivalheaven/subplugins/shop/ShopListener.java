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

package info.nordbyen.survivalheaven.subplugins.shop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The listener interface for receiving shop events. The class that is
 * interested in processing a shop event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addShopListener<code> method. When
 * the shop event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ShopEvent
 */
public class ShopListener implements Listener {

	/**
	 * On click.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		if (e.getPlayer().getName().equalsIgnoreCase("l0lkj")) {
			// e.getPlayer().sendMessage( "Åpner shop" );
			// e.getPlayer().openInventory( Shop.getShopInventory( ShopType.WOOD
			// ) );
		}
	}

	/**
	 * On inventory.
	 * 
	 * @param e
	 *            the e
	 */
	@EventHandler
	public void onInventory(final InventoryClickEvent e) {
		final Inventory inv = e.getClickedInventory();
		@SuppressWarnings("unused")
		final ItemStack item = inv.getItem(e.getSlot());
	}
}
