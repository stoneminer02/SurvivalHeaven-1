/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * The Class FirstEncounterConfig.
 */
public class FirstEncounterConfig extends CustomConfiguration {

	/**
	 * Gets the door1_1.
	 *
	 * @return the door1_1
	 */
	public static Location getDoor1_1() {
		return new Location(Bukkit.getWorld(getInstance().getString(
				"quest1.door1.world")), getInstance().getDouble(
				"quest1.door1.1.x"), getInstance()
				.getDouble("quest1.door1.1.y"), getInstance().getDouble(
				"quest1.door1.1.z"));
	}

	/**
	 * Gets the door1_2.
	 *
	 * @return the door1_2
	 */
	public static Location getDoor1_2() {
		return new Location(Bukkit.getWorld(getInstance().getString(
				"quest1.door1.world")), getInstance().getDouble(
				"quest1.door1.2.x"), getInstance()
				.getDouble("quest1.door1.2.y"), getInstance().getDouble(
				"quest1.door1.2.z"));
	}

	/**
	 * Gets the single instance of FirstEncounterConfig.
	 *
	 * @return single instance of FirstEncounterConfig
	 */
	public static FirstEncounterConfig getInstance() {
		if (cfg == null) {
			cfg = new FirstEncounterConfig();
		}
		return cfg;
	}

	/** The cfg. */
	private static FirstEncounterConfig cfg;

	/**
	 * Instantiates a new first encounter config.
	 */
	private FirstEncounterConfig() {
		super(new File("./plugins/SurvivalHeaven/questconfig/quest1.yml"));
		cfg = this;
		load();
		save();
		saveDefault();
	}

	/**
	 * Save default.
	 */
	private void saveDefault() {
		if (!contains("quest1.door1.world")) {
			set("quest1.door1.world", "NyVerden");
		}
		if (!contains("quest1.door1.1.x")) {
			set("quest1.door1.1.x", 87);
		}
		if (!contains("quest1.door1.1.y")) {
			set("quest1.door1.1.y", 62);
		}
		if (!contains("quest1.door1.1.z")) {
			set("quest1.door1.1.z", 209);
		}
		if (!contains("quest1.door1.2.x")) {
			set("quest1.door1.2.x", 80);
		}
		if (!contains("quest1.door1.2.y")) {
			set("quest1.door1.2.y", 65);
		}
		if (!contains("quest1.door1.2.z")) {
			set("quest1.door1.2.z", 209);
		}
		save();
	}
}
