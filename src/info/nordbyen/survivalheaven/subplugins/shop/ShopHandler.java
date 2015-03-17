/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
