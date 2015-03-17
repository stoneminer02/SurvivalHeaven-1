/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * The Class PrepareRecipeEvent.
 */
public class PrepareRecipeEvent extends Event {

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

	/** The result. */
	private ItemStack result;
	
	/** The inv. */
	private final ItemStack[] inv;
	
	/** The name. */
	private final String name;

	/** The handlers. */
	private static HandlerList handlers = new HandlerList();

	/**
	 * Instantiates a new prepare recipe event.
	 *
	 * @param inventory
	 *            the inventory
	 * @param result
	 *            the result
	 * @param name
	 *            the name
	 */
	public PrepareRecipeEvent(final ItemStack[] inventory,
			final ItemStack result, final String name) {
		super();
		inv = inventory;
		setResult(result);
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public ItemStack[] getInventory() {
		return inv;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public ItemStack getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result
	 *            the new result
	 */
	public void setResult(final ItemStack result) {
		this.result = result;
	}
}
