/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.uendeligdropper.files;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

/**
 * The Class Dispensers.
 */
public class Dispensers extends CustomConfiguration {

	/**
	 * Gets the single instance of Dispensers.
	 *
	 * @return single instance of Dispensers
	 */
	public static Dispensers getInstance() {
		if (dispensers == null) {
			dispensers = new Dispensers();
		}
		return dispensers;
	}

	/**
	 * Checks if is dispenser.
	 *
	 * @param location
	 *            the location
	 * @return true, if is dispenser
	 */
	public static boolean isDispenser(final Location location) {
		getInstance().reload();
		if (getInstance().getList("dispensers").contains(location.toString()))
			return true;
		return false;
	}

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	@SuppressWarnings("unchecked")
	public static void setLocation(final Location location) {
		((ArrayList<String>) getInstance().getList("dispensers")).add(location
				.toString());
		getInstance().save();
	}

	/** The dispensers. */
	private static Dispensers dispensers;

	/** The empty list. */
	private static List<String> emptyList = new ArrayList<String>();

	/**
	 * Instantiates a new dispensers.
	 */
	public Dispensers() {
		super(new File("./plugins/SurvivalHeaven/dispensers.yml"));
		dispensers = this;
		load();
		save();
		saveDefault();
	}

	/**
	 * Save default.
	 */
	private void saveDefault() {
		if (!contains("dispensers")) {
			set("dispensers", emptyList);
			save();
		}
	}
}
