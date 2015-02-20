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

package info.nordbyen.survivalheaven.api.scheduler;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * The Class L0lTaskManager.
 */
public class L0lTaskManager extends BukkitRunnable {

	/**
	 * Adds the task.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param task
	 *            the task
	 * @param ticks
	 *            the ticks
	 */
	public static void addTask(final Plugin plugin, final L0lTask task,
			int ticks) {
		if (task == null)
			throw new IllegalArgumentException(
					"'L0lTask task' cannot be 'null'!");
		if (ticks < 1) {
			ticks = 1;
		}
		taskMap_r.put(new L0lTaskWrapper(task, ticks), plugin);
	}

	/**
	 * Null all.
	 */
	public static void nullAll() {
		instance = null;
		taskMap_r.clear();
		taskMap_r = null;
	}

	/**
	 * Removes the task.
	 * 
	 * @param task
	 *            the task
	 */
	public static void removeTask(final L0lTask task) {
		taskMap_r.remove(task);
	}

	/**
	 * Start timer.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	public static void startTimer(final Plugin plugin) {
		if (taskMap_r == null) {
			taskMap_r = new HashMap<L0lTaskWrapper, Plugin>();
		}
		if (taskMap_o == null) {
			taskMap_o = new HashMap<L0lTaskWrapper, Plugin>();
		}
		if (instance == null) {
			instance = new L0lTaskManager(plugin).runTaskTimer(plugin, 1, 1);
			// Repeat every tick
		}
	}

	/** The instance. */
	private static BukkitTask instance;

	/** The task map_r. */
	private static HashMap<L0lTaskWrapper, Plugin> taskMap_r;

	/** The task map_o. */
	private static HashMap<L0lTaskWrapper, Plugin> taskMap_o;

	/** The plugin. */
	private final Plugin plugin;

	/**
	 * Instantiates a new l0l task manager.
	 * 
	 * @param plugin
	 *            the plugin
	 */
	private L0lTaskManager(final Plugin plugin) {
		this.plugin = plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		final long ticks = Bukkit.getServer().getWorlds().get(0).getFullTime();
		for (final Entry<L0lTaskWrapper, Plugin> taskWrapperEntry : taskMap_r
				.entrySet()) {
			if (taskWrapperEntry.getValue() != plugin) {
				continue;
			}
			final L0lTaskWrapper taskWrapper = taskWrapperEntry.getKey();
			if ((ticks % taskWrapper.getDelay()) == 0) {
				if (taskWrapper.isDone()) {
					taskWrapper.nullAll();
					taskMap_o.put(taskWrapper, plugin);
					continue;
				}
				taskWrapper.executeTask();
			}
		}
		for (final Entry<L0lTaskWrapper, Plugin> taskWrapperEntry : taskMap_o
				.entrySet()) {
			taskMap_r.remove(taskWrapperEntry.getKey());
		}
		taskMap_o = new HashMap<L0lTaskWrapper, Plugin>();
	}
}
