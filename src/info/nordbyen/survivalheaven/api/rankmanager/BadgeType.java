/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.rankmanager;

import org.bukkit.ChatColor;

/**
 * The Enum BadgeType.
 */
public enum BadgeType {
	
	/** The bygger. */
	BYGGER(1, "BYGGER", "+", ChatColor.YELLOW),
	
	/** The pensjonist. */
	PENSJONIST(2, "PENSJONIST", "+", ChatColor.DARK_PURPLE);

	/**
	 * Gets the badge from id.
	 *
	 * @param id
	 *            the id
	 * @return the badge from id
	 */
	public static BadgeType getBadgeFromId(final int id) {
		if (id == 1)
			return BYGGER;
		if (id == 2)
			return PENSJONIST;
		return null;
	}

	/** The badge. */
	private final int badge;
	
	/** The name. */
	private final String name;
	
	/** The prefix. */
	private final String prefix;
	
	/** The color. */
	private final ChatColor color;

	/**
	 * Instantiates a new badge type.
	 *
	 * @param badge
	 *            the badge
	 * @param name
	 *            the name
	 * @param prefix
	 *            the prefix
	 * @param color
	 *            the color
	 */
	BadgeType(final int badge, final String name, final String prefix,
			final ChatColor color) {
		this.badge = badge;
		this.name = name;
		this.prefix = prefix;
		this.color = color;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public ChatColor getColor() {
		return color;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return badge;
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
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return color + prefix;
	}
}
