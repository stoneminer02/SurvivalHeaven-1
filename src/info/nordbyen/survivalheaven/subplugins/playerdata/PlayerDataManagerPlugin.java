/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.playerdata;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * The Class PlayerDataManagerPlugin.
 */
public class PlayerDataManagerPlugin extends SubPlugin {

	/**
	 * The Class PlayerDataTask.
	 */
	private class PlayerDataTask extends BukkitRunnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			SH.getManager().getPlayerDataManager().saveDataToDatabase();
		}
	}

	/** The scheduler_running. */
	private boolean scheduler_running = false;

	/**
	 * Instantiates a new player data manager plugin.
	 *
	 * @param name
	 *            the name
	 */
	public PlayerDataManagerPlugin(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
		SH.getManager().getPlayerDataManager().saveDataToDatabase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		((PlayerDataManager) SH.getManager().getPlayerDataManager())
				.createTable();
		((PlayerDataManager) SH.getManager().getPlayerDataManager())
				.updateDataFromDatabase();
		startScheduler();
		Bukkit.getPluginManager().registerEvents(new PlayerDatalistener(),
				getPlugin());
	}

	/**
	 * Start scheduler.
	 */
	@SuppressWarnings("deprecation")
	private void startScheduler() {
		if (scheduler_running)
			return;
		scheduler_running = true;
		Bukkit.getServer()
				.getScheduler()
				.scheduleSyncRepeatingTask(getPlugin(), new PlayerDataTask(),
						20 * 60 * 5L, 20 * 60 * 5L);
	}
}
