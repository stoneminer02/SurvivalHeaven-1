/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
