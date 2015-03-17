/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.subplugin;

import java.util.ArrayList;

/**
 * The Interface ISubPluginManager.
 */
public interface ISubPluginManager {

	/**
	 * Adds the sub plugin.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public void addSubPlugin(final SubPlugin plugin);

	/**
	 * Disable all.
	 */
	public void disableAll();

	/**
	 * Enable all.
	 */
	public void enableAll();

	/**
	 * Gets the subplugins.
	 *
	 * @return the subplugins
	 */
	public ArrayList<SubPlugin> getSubplugins();

	/**
	 * Removes the sub plugin.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public void removeSubPlugin(final SubPlugin plugin);

	/**
	 * Unregister all.
	 */
	public void unregisterAll();
}
