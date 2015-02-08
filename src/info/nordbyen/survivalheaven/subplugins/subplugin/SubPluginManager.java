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
