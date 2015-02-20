package info.nordbyen.survivalheaven.subplugins.homes;

import org.bukkit.Location;

public class Home {

	private final String name;
	private final String uuid;
	private final Location location;

	public Home(String name, String uuid, Location location) {
		this.name = name;
		this.uuid = uuid;
		this.location = location;
	}

	public Location getLocation() {
		return location.clone();
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}
}
