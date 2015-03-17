/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.homes;

import org.bukkit.Location;

/**
 * The Class Home.
 */
public class Home {

	/** The name. */
	private final String name;
	
	/** The uuid. */
	private final String uuid;
	
	/** The location. */
	private final Location location;

	/**
	 * Instantiates a new home.
	 *
	 * @param name
	 *            the name
	 * @param uuid
	 *            the uuid
	 * @param location
	 *            the location
	 */
	public Home(String name, String uuid, Location location) {
		this.name = name;
		this.uuid = uuid;
		this.location = location;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		return location.clone();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
}
