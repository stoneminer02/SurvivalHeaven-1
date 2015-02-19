/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
		SH.getManager().debug("Fant ikke rank med id: " + id,
				"Printer stackstrace under...");
		SH.getManager().debug((Object[]) (new Throwable()).getStackTrace());
		return BRUKER;
	}
	
	public static RankType getRankFromName( String name ) {
		if ( name.equalsIgnoreCase("banned") || name.equalsIgnoreCase("ban") )
			return BANNED;
		if ( name.equalsIgnoreCase("bruker") )
			return BRUKER;
		if ( name.equalsIgnoreCase("mod") || name.equalsIgnoreCase("moderator") )
			return MODERATOR;
		if ( name.equalsIgnoreCase("admin") || name.equalsIgnoreCase("administrator") )
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
	public ChatColor getColor() {
		return color;
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
		return color + prefix;
	}
}
