/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.rankmanager;

import info.nordbyen.survivalheaven.SH;

import org.bukkit.ChatColor;

/**
 * The Enum RankType.
 */
public enum RankType {
	
	/** The banned. */
	BANNED(0, "BANNED", "", ChatColor.GRAY), // spillere som er bannet her
	/** The bruker. */
	BRUKER(1, "BRUKER", "", ChatColor.RESET),
	
	/** The moderator. */
	MODERATOR(2, "MODERATOR", "[Mod] ", ChatColor.BLUE),
	
	/** The administrator. */
	ADMINISTRATOR(3, "ADMINISTRATOR", "[Admin] ", ChatColor.GOLD);

	/* Fjernet inntil vi trenger dem */
	// SUPERMODERATOR( 4, "SUPERMODERATOR", "Mod+", ChatColor.BLUE ),
	// SUPERADMINISTRATOR( 5, "SUPERADMINISTRATOR", "Admin+",
	// ChatColor.YELLOW );
	/**
	 * Gets the rank from id.
	 *
	 * @param id
	 *            the id
	 * @return the rank from id
	 */
	public static RankType getRankFromId(final int id) {
		if (id == 0)
			return BANNED;
		if (id == 1)
			return BRUKER;
		if (id == 2)
			return MODERATOR;
		if (id == 3)
			return ADMINISTRATOR;
		SH.debug("Fant ikke rank med id: " + id,
				"Printer stackstrace under...");
		SH.debug((Object[]) (new Throwable()).getStackTrace());
		return BRUKER;
	}

	/**
	 * Gets the rank from name.
	 *
	 * @param name
	 *            the name
	 * @return the rank from name
	 */
	public static RankType getRankFromName(String name) {
		if (name.equalsIgnoreCase("banned") || name.equalsIgnoreCase("ban"))
			return BANNED;
		if (name.equalsIgnoreCase("bruker"))
			return BRUKER;
		if (name.equalsIgnoreCase("mod") || name.equalsIgnoreCase("moderator"))
			return MODERATOR;
		if (name.equalsIgnoreCase("admin")
				|| name.equalsIgnoreCase("administrator"))
			return ADMINISTRATOR;
		return null;
	}

	/** The rank. */
	private final int rank;
	
	/** The name. */
	private final String name;
	
	/** The prefix. */
	private final String prefix;
	
	/** The color. */
	private final ChatColor color;

	/**
	 * Instantiates a new rank type.
	 *
	 * @param rank
	 *            the rank
	 * @param name
	 *            the name
	 * @param prefix
	 *            the prefix
	 * @param color
	 *            the color
	 */
	RankType(final int rank, final String name, final String prefix,
			final ChatColor color) {
		this.rank = rank;
		this.name = name;
		this.prefix = prefix;
		this.color = color;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color + prefix;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return rank;
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
		return color + "";
	}
}
