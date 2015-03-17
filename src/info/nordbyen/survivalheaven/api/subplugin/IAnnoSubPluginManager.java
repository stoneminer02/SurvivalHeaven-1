/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.subplugin;

/**
 * The Interface IAnnoSubPluginManager.
 */
public interface IAnnoSubPluginManager {

	/**
	 * Adds the class.
	 *
	 * @param klass
	 *            the klass
	 */
	public void addClass(final Class<?> klass);

	/**
	 * Disable all.
	 */
	public void disableAll();

	/**
	 * Enable all.
	 */
	public void enableAll();

	/**
	 * Removes the class.
	 *
	 * @param klass
	 *            the klass
	 */
	public void removeClass(final Class<?> klass);
}
