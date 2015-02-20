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
import info.nordbyen.survivalheaven.api.wand.IWandManager;
import info.nordbyen.survivalheaven.api.wand.Wand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The Class WandManager.
 */
public class WandManager implements IWandManager {

	/** The Wands. */
	private static HashMap<String, Wand> Wands = new HashMap<String, Wand>();

	/**
	 * Instantiates a new wand manager.
	 */
	public WandManager() {
		Bukkit.getPluginManager().registerEvents(new WandListener(),
				SH.getPlugin());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.wand.IWandManager#add(info.nordbyen.
	 * SurvivalHeaven.api.wand.Wand)
	 */
	@Override
	public void add(final Wand wand) {
		Wands.put(wand.getName(), wand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.wand.IWandManager#createWand(org.bukkit
	 * .inventory.ItemStack , info.nordbyen.survivalheaven.api.wand.Wand,
	 * org.bukkit.entity.Player)
	 */
	@Override
	public boolean createWand(final ItemStack target, final Wand wand,
			final Player player) {
		if (isWand(target))
			return false;
		if (wand.canCreate(target, player)) {
			final ItemMeta meta = target.getItemMeta();
			meta.setDisplayName(SH.NAME);
			List<String> data = new ArrayList<String>();
			if (meta.hasLore()) {
				data = meta.getLore();
			}
			if (!data.contains(wand.getName())) {
				data.add(wand.getName());
			}
			meta.setLore(data);
			target.setItemMeta(meta);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.wand.IWandManager#get(java.lang.String)
	 */
	@Override
	public Wand get(final String id) {
		if (Wands.containsKey(id))
			return Wands.get(id);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.wand.IWandManager#isWand(org.bukkit.
	 * inventory .ItemStack )
	 */
	@Override
	public boolean isWand(final ItemStack itemStack) {
		for (final String o : Wands.keySet()) {
			if (itemStack == null)
				return false;
			final ItemMeta meta = itemStack.getItemMeta();
			if (meta.hasLore()) {
				if (meta.getLore().contains(o))
					return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.wand.IWandManager#search(org.bukkit.
	 * inventory .ItemStack )
	 */
	@Override
	public Wand search(final ItemStack itemStack) {
		for (final Object o : Wands.keySet()) {
			final String s = o.toString();
			if (itemStack.getItemMeta().getLore().contains(s))
				return get(s);
		}
		return null;
	}
}
