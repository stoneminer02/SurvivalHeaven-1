/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.remote;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Class RemoteBukkitConfig.
 */
public class RemoteBukkitConfig extends CustomConfiguration {

	/**
	 * Gets the single instance of RemoteBukkitConfig.
	 *
	 * @return single instance of RemoteBukkitConfig
	 */
	public static RemoteBukkitConfig getInstance() {
		if (cfg == null) {
			cfg = new RemoteBukkitConfig();
		}
		return cfg;
	}

	/** The cfg. */
	private static RemoteBukkitConfig cfg;

	/**
	 * Instantiates a new remote bukkit config.
	 */
	public RemoteBukkitConfig() {
		super(new File("./plugins/SurvivalHeaven/remoteconsole.yml"));
		cfg = this;
		load();
		save();
		saveDefault();
	}

	/**
	 * Save default.
	 */
	private void saveDefault() {
		if (!contains("port")) {
			set("port", "25564");
		}
		if (!contains("verbose")) {
			set("verbose", true);
		}
		if (!contains("logsize")) {
			set("logsize", 500);
		}
		final ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
		if (!contains("users")) {
			set("user", users);
		}
		save();
	}
}
