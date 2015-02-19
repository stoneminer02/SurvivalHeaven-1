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

import info.nordbyen.survivalheaven.SH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

	public HashMap<String, ArrayList<Long>> hash = new HashMap<String, ArrayList<Long>>();
	public boolean timerIsRunning = false;
	
	@EventHandler
	public void mine(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.GOLD_ORE ||
				e.getBlock().getType() == Material.DIAMOND_ORE ||
				e.getBlock().getType() == Material.IRON_ORE ||
				e.getBlock().getType() == Material.REDSTONE_ORE)
		{
			ArrayList<Long> blocktimelist = hash.get( e.getPlayer().getName() );
			if( blocktimelist == null ) {
				blocktimelist = new ArrayList< Long >();
				hash.put(e.getPlayer().getName(), blocktimelist);
			}
			blocktimelist.add(System.currentTimeMillis());
			Bukkit.broadcastMessage(ChatColor.GOLD + "DEBUG: " + e.getPlayer().getName() + ": " + blocktimelist.size());
		}
		return;
	}

	@EventHandler
	public void onLeave( PlayerQuitEvent e ) {
		hash.remove(e.getPlayer().getName());
	}
	
	@EventHandler
	public void onJoin( PlayerJoinEvent e ) {
		hash.put(e.getPlayer().getName(), new ArrayList<Long>());
	}
	
	public void startTimer() {
		Bukkit.broadcastMessage(ChatColor.GREEN + "PRØVER");
		if( timerIsRunning ) return;
		timerIsRunning = true;
		Bukkit.broadcastMessage(ChatColor.GREEN + "STARTER");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(SH.getPlugin(), new Runnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatColor.GREEN + "KJØRER");
				for( Entry<String, ArrayList<Long>> entry : hash.entrySet() ) {
					Bukkit.broadcastMessage(ChatColor.GREEN + "LOOPER: " + entry.getKey());
					String name = entry.getKey();
					ArrayList< Long > blocktimes = entry.getValue();
					ArrayList<Long> remove = new ArrayList<Long>();
					Bukkit.broadcastMessage(ChatColor.GREEN + "Antall: " + entry.getValue().size());
					for( long time : blocktimes ) {
						if( time < (System.currentTimeMillis() - 30*1000) ) {
							remove.add(time);
						}
					}
					for( long rem : remove )
						blocktimes.remove(rem);
					Bukkit.broadcastMessage(ChatColor.GREEN + "Antall etter: " + entry.getValue().size());
					
					if( blocktimes.size() >= 1 ) {
						Bukkit.broadcast(ChatColor.YELLOW + name + ChatColor.GRAY + " har minet " + ChatColor.YELLOW + blocktimes.size() + ChatColor.GRAY + " de siste 30 sekundene", "buildit.admin" );
					}
				}
			}
			
		}, 20*30, 20*30); // 30 sec
	}
}
