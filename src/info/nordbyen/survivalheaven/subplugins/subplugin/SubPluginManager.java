/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.subplugin;

import info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.util.ArrayList;

/**
 * The Class SubPluginManager.
 */
public final class SubPluginManager implements ISubPluginManager {

	/** The subplugins. */
	private final ArrayList<SubPlugin> subplugins = new ArrayList<SubPlugin>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#addSubPlugin
	 * (info.nordbyen.survivalheaven .api.subplugin.SubPlugin)
	 */
	@Override
	public void addSubPlugin(final SubPlugin plugin) {
		try {
			if (subplugins.contains(plugin))
				return;
			subplugins.add(plugin);
			plugin.enablePlugin();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#disableAll()
	 */
	@Override
	public void disableAll() {
		for (final SubPlugin spl : subplugins) {
			try {
				spl.disablePlugin();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#enableAll()
	 */
	@Override
	public void enableAll() {
		for (final SubPlugin spl : subplugins) {
			try {
				spl.enablePlugin();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#getSubplugins
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<SubPlugin> getSubplugins() {
		return (ArrayList<SubPlugin>) subplugins.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#removeSubPlugin
	 * (info.nordbyen.survivalheaven .api.subplugin.SubPlugin)
	 */
	@Override
	public void removeSubPlugin(final SubPlugin plugin) {
		try {
			if (!subplugins.contains(plugin))
				return;
			subplugins.remove(plugin);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager#unregisterAll
	 * ()
	 */
	@Override
	public void unregisterAll() {
		subplugins.clear();
	}
}
