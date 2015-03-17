/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.rankmanager;

/**
 * The Interface IRankManager.
 */
public interface IRankManager {

	/**
	 * Gets the badges.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the badges
	 */
	BadgeType[] getBadges(final String uuid);

	/**
	 * Gets the chat badge prefix.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the chat badge prefix
	 */
	String getChatBadgePrefix(final String uuid);

	/**
	 * Gets the chat rank prefix.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the chat rank prefix
	 */
	String getChatRankPrefix(final String uuid);

	/**
	 * Gets the rank.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the rank
	 */
	RankType getRank(final String uuid);

	/**
	 * Update names.
	 */
	void updateNames();
}
