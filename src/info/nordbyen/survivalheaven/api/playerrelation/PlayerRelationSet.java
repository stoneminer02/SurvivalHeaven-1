/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.playerrelation;

/**
 * The Class PlayerRelationSet.
 */
public class PlayerRelationSet {

	/** The friend relation. */
	private final FriendRelation friendRelation;
	
	/** The group relation. */
	private final GroupRelation groupRelation;

	/**
	 * Instantiates a new player relation set.
	 *
	 * @param friendRelation
	 *            the friend relation
	 * @param groupRelation
	 *            the group relation
	 */
	public PlayerRelationSet(final FriendRelation friendRelation,
			final GroupRelation groupRelation) {
		this.friendRelation = friendRelation;
		this.groupRelation = groupRelation;
	}

	/**
	 * Gets the friend relation.
	 *
	 * @return the friend relation
	 */
	public FriendRelation getFriendRelation() {
		return friendRelation;
	}

	/**
	 * Gets the group relation.
	 *
	 * @return the group relation
	 */
	public GroupRelation getGroupRelation() {
		return groupRelation;
	}
}
