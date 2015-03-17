/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.merchant;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * The Class Merchant.
 */
public class Merchant extends SubPlugin {

	// private ArrayList< Villager > villagers = new ArrayList< Villager >();
	/**
	 * The listener interface for receiving merchant events. The class that is
	 * interested in processing a merchant event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addMerchantListener<code> method. When
	 * the merchant event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see MerchantEvent
	 */
	public class MerchantListener implements Listener {
	}

	/**
	 * Instantiates a new merchant.
	 *
	 * @param name
	 *            the name
	 */
	public Merchant(final String name) {
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
		Bukkit.getPluginManager().registerEvents(new MerchantListener(),
				getPlugin());
	}
}
