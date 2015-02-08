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

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.util.ArrayList;

import org.bukkit.Material;

/**
 * The Class ShopHandler.
 */
public class ShopHandler extends SubPlugin {

	/**
	 * The Class Shop.
	 */
	public static class Shop {

		/**
		 * The Class ShopItem.
		 */
		public static class ShopItem {

			/** The item. */
			Material item;
			/** The name. */
			String name;
			/** The price_per. */
			int price_per;

			/**
			 * Instantiates a new shop item.
			 * 
			 * @param item
			 *            the item
			 * @param name
			 *            the name
			 * @param price_per
			 *            the price_per
			 */
			private ShopItem(final Material item, final String name,
					final int price_per) {
				this.item = item;
				this.name = name;
				this.price_per = price_per;
			}

			/**
			 * Gets the individual price.
			 * 
			 * @return the individual price
			 */
			int getIndividualPrice() {
				return price_per;
			}

			/**
			 * Gets the name.
			 * 
			 * @return the name
			 */
			String getName() {
				return name;
			}

			/**
			 * Gets the type.
			 * 
			 * @return the type
			 */
			Material getType() {
				return item;
			}
		}

		/** The items. */
		ArrayList<ShopItem> items = new ArrayList<ShopItem>();

		/**
		 * Adds the item.
		 * 
		 * @param item
		 *            the item
		 */
		void addItem(final ShopItem item) {
			items.add(item);
		}

		/**
		 * Gets the item.
		 * 
		 * @param name
		 *            the name
		 * @return the item
		 */
		ShopItem getItem(final String name) {
			for (final ShopItem i : items) {
				if (i.getName().equalsIgnoreCase(name))
					return i;
			}
			return null;
		}
	}

	/** The shops. */
	ArrayList<Shop> shops = new ArrayList<Shop>();

	/**
	 * Instantiates a new shop handler.
	 * 
	 * @param name
	 *            the name
	 */
	public ShopHandler(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
	}
}
