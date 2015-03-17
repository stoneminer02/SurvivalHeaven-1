/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bossbar;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * The Class BossbarAPI.
 */
public class BossbarAPI extends SubPlugin implements Listener {

	/**
	 * Gets the single instance of BossbarAPI.
	 *
	 * @return single instance of BossbarAPI
	 */
	public static Plugin getInstance() {
		return plugin;
	}

	/**
	 * Gets the message.
	 *
	 * @param p
	 *            the p
	 * @return the message
	 */
	public static String getMessage(final Player p) {
		if (playerdragonbartask.containsKey(p))
			return playerdragonbartask.get(p);
		else
			return " ";
	}

	/**
	 * Gets the message dragon.
	 *
	 * @param p
	 *            the p
	 * @return the message dragon
	 */
	public static String getMessageDragon(final Player p) {
		if (playerdragonbartask.containsKey(p))
			return playerdragonbartask.get(p);
		else
			return " ";
	}

	/**
	 * Gets the message wither.
	 *
	 * @param p
	 *            the p
	 * @return the message wither
	 */
	public static String getMessageWither(final Player p) {
		if (playerwitherbartask.containsKey(p))
			return playerwitherbartask.get(p);
		else
			return " ";
	}

	/**
	 * Checks for bar.
	 *
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public static boolean hasBar(final Player p) {
		if (McVersion(p)) {
			if (playerwitherbartask.containsKey(p)
					&& playerdragonbartask.containsKey(p))
				return true;
			else
				return false;
		} else
			return playerdragonbartask.get(p) != null;
	}

	/**
	 * Checks for bar dragon.
	 *
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public static boolean hasBarDragon(final Player p) {
		return playerdragonbartask.get(p) != null;
	}

	/**
	 * Checks for bar wither.
	 *
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public static boolean hasBarWither(final Player p) {
		return playerwitherbartask.get(p) != null;
	}

	// dragon
	/**
	 * Mc version.
	 *
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public static boolean McVersion(final Player p) {
		return true;
	}

	/**
	 * Removes the bar.
	 *
	 * @param p
	 *            the p
	 */
	public static void removeBar(final Player p) {
		if (McVersion(p)) {
			playerwitherbartask.remove(p);
			healthwitherbartask.remove(p);
			cooldownswitherbar.remove(p);
			starttimerwitherbar.remove(p);
			FWither.removeBossBar(p);
		}
		playerdragonbartask.remove(p);
		healthdragonbartask.remove(p);
		cooldownsdragonbar.remove(p);
		starttimerdragonbar.remove(p);
		FDragon.removeBossBar(p);
	}

	/**
	 * Removes the bar dragon.
	 *
	 * @param p
	 *            the p
	 */
	public static void removeBarDragon(final Player p) {
		playerdragonbartask.remove(p);
		healthdragonbartask.remove(p);
		cooldownsdragonbar.remove(p);
		starttimerdragonbar.remove(p);
		FDragon.removeBossBar(p);
	}

	/**
	 * Removes the bar wither.
	 *
	 * @param p
	 *            the p
	 */
	public static void removeBarWither(final Player p) {
		playerwitherbartask.remove(p);
		healthwitherbartask.remove(p);
		cooldownswitherbar.remove(p);
		starttimerwitherbar.remove(p);
		FWither.removeBossBar(p);
	}

	/**
	 * Sets the bar.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 */
	public static void setBar(final Player p, final String text) {
		if (McVersion(p)) {
			playerwitherbartask.put(p, text);
			FWither.setBossBartext(p, text);
		}
		playerdragonbartask.put(p, text);
		FDragon.setBossBartext(p, text);
	}

	/**
	 * Sets the bar dragon.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 */
	public static void setBarDragon(final Player p, final String text) {
		playerdragonbartask.put(p, text);
		FDragon.setBossBartext(p, text);
	}

	// wither
	/**
	 * Sets the bar dragon health.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param health
	 *            the health
	 */
	public static void setBarDragonHealth(final Player p, String text,
			float health) {
		if ((health <= 0) || (health > 100)) {
			health = 100;
			text = "health must be between 1 and 100 it's a %";
		}
		playerdragonbartask.put(p, text);
		healthdragonbartask.put(p, (health / 100) * 200);
		FDragon.setBossBar(p, text, health);
	}

	/**
	 * Sets the bar dragon timer.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param timer
	 *            the timer
	 */
	public static void setBarDragonTimer(final Player p, final String text,
			final int timer) {
		playerdragonbartask.put(p, text);
		cooldownsdragonbar.put(p, timer);
		if (!starttimerdragonbar.containsKey(p)) {
			starttimerdragonbar.put(p, timer);
		}
		final int unite = Math.round(200 / starttimerdragonbar.get(p));
		FDragon.setBossBar(p, text, unite * timer);
	}

	/**
	 * Sets the bar health.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param health
	 *            the health
	 */
	public static void setBarHealth(final Player p, String text, float health) {
		if ((health <= 0) || (health > 100)) {
			health = 100;
			text = "health must be between 1 and 100 it's a %";
		}
		if (McVersion(p)) {
			playerwitherbartask.put(p, text);
			healthwitherbartask.put(p, (health / 100) * 300);
			FWither.setBossBar(p, text, health);
		}
		playerdragonbartask.put(p, text);
		healthdragonbartask.put(p, (health / 100) * 200);
		FDragon.setBossBar(p, text, health);
	}

	/**
	 * Sets the bar timer.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param timer
	 *            the timer
	 */
	public static void setBarTimer(final Player p, final String text,
			final int timer) {
		if (McVersion(p)) {
			playerwitherbartask.put(p, text);
			cooldownswitherbar.put(p, timer);
			if (!starttimerwitherbar.containsKey(p)) {
				starttimerwitherbar.put(p, timer);
			}
			final int unite = Math.round(300 / starttimerwitherbar.get(p));
			FWither.setBossBar(p, text, unite * timer);
		}
		playerdragonbartask.put(p, text);
		cooldownsdragonbar.put(p, timer);
		if (!starttimerdragonbar.containsKey(p)) {
			starttimerdragonbar.put(p, timer);
		}
		final int unite1 = Math.round(200 / starttimerdragonbar.get(p));
		FDragon.setBossBar(p, text, unite1 * timer);
	}

	/**
	 * Sets the bar wither.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 */
	public static void setBarWither(final Player p, final String text) {
		playerwitherbartask.put(p, text);
		FWither.setBossBartext(p, text);
	}

	/**
	 * Sets the bar wither health.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param health
	 *            the health
	 */
	public static void setBarWitherHealth(final Player p, String text,
			float health) {
		if ((health <= 0) || (health > 100)) {
			health = 100;
			text = "health must be between 1 and 100 it's a %";
		}
		playerwitherbartask.put(p, text);
		healthwitherbartask.put(p, (health / 100) * 300);
		FWither.setBossBar(p, text, health);
	}

	// both
	/**
	 * Sets the bar wither timer.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param timer
	 *            the timer
	 */
	public static void setBarWitherTimer(final Player p, final String text,
			final int timer) {
		playerwitherbartask.put(p, text);
		cooldownswitherbar.put(p, timer);
		if (!starttimerwitherbar.containsKey(p)) {
			starttimerwitherbar.put(p, timer);
		}
		final int unite = Math.round(300 / starttimerwitherbar.get(p));
		FWither.setBossBar(p, text, unite * timer);
	}

	/** The plugin. */
	public static Plugin plugin;

	/** The playerdragonbartask. */
	public static Map<Player, String> playerdragonbartask = new HashMap<Player, String>();

	/** The healthdragonbartask. */
	public static Map<Player, Float> healthdragonbartask = new HashMap<Player, Float>();

	/** The cooldownsdragonbar. */
	public static Map<Player, Integer> cooldownsdragonbar = new HashMap<Player, Integer>();

	/** The starttimerdragonbar. */
	public static Map<Player, Integer> starttimerdragonbar = new HashMap<Player, Integer>();

	/** The playerwitherbartask. */
	public static Map<Player, String> playerwitherbartask = new HashMap<Player, String>();

	/** The healthwitherbartask. */
	public static Map<Player, Float> healthwitherbartask = new HashMap<Player, Float>();

	/** The cooldownswitherbar. */
	public static Map<Player, Integer> cooldownswitherbar = new HashMap<Player, Integer>();

	/** The starttimerwitherbar. */
	public static Map<Player, Integer> starttimerwitherbar = new HashMap<Player, Integer>();

	/**
	 * Instantiates a new bossbar api.
	 *
	 * @param name
	 *            the name
	 */
	public BossbarAPI(final String name) {
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

	/**
	 * Dragon bar task.
	 */
	public void DragonBarTask() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (final Player p : plugin.getServer().getOnlinePlayers()) {
					if (!cooldownsdragonbar.containsKey(p)) {
						if (playerdragonbartask.containsKey(p)
								&& !healthdragonbartask.containsKey(p)) {
							setBarDragon(p, playerdragonbartask.get(p));
						} else if (playerdragonbartask.containsKey(p)
								&& healthdragonbartask.containsKey(p)) {
							setBarDragonHealth(p, playerdragonbartask.get(p),
									healthdragonbartask.get(p));
						}
					}
					if (!cooldownswitherbar.containsKey(p)) {
						if (playerwitherbartask.containsKey(p)
								&& !healthwitherbartask.containsKey(p)) {
							setBarWither(p, playerwitherbartask.get(p));
						} else if (playerwitherbartask.containsKey(p)
								&& healthwitherbartask.containsKey(p)) {
							setBarWitherHealth(p, playerwitherbartask.get(p),
									healthwitherbartask.get(p));
						}
					}
				}
			}
		}.runTaskTimer(getPlugin(), 0, 40);
		new BukkitRunnable() {

			@Override
			public void run() {
				for (final Player p : plugin.getServer().getOnlinePlayers()) {
					if (cooldownsdragonbar.containsKey(p)) {
						if (cooldownsdragonbar.get(p) > 0) {
							cooldownsdragonbar.put(p,
									cooldownsdragonbar.get(p) - 1);
							setBarDragonTimer(p, playerdragonbartask.get(p),
									cooldownsdragonbar.get(p));
						} else {
							removeBarDragon(p);
						}
					}
					if (cooldownswitherbar.containsKey(p)) {
						if (cooldownswitherbar.get(p) > 0) {
							cooldownswitherbar.put(p,
									cooldownswitherbar.get(p) - 1);
							setBarWitherTimer(p, playerwitherbartask.get(p),
									cooldownswitherbar.get(p));
						} else {
							removeBarWither(p);
						}
					}
				}
			}
		}.runTaskTimer(getPlugin(), 0, 20);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		getPlugin().getServer().getPluginManager()
				.registerEvents(this, getPlugin());
		plugin = getPlugin();
		DragonBarTask();
	}

	/**
	 * Player kick.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerKick(final PlayerKickEvent event) {
		final Player p = event.getPlayer();
		removeBar(p);
		FDragon.removehorligneD(p);
		FWither.removehorligneW(p);
	}

	/**
	 * Player quit.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerQuit(final PlayerQuitEvent event) {
		final Player p = event.getPlayer();
		removeBar(p);
		FDragon.removehorligneD(p);
		FWither.removehorligneW(p);
	}
}
