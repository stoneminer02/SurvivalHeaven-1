/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.groupmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class FriendManager.
 */
public class FriendManager {

	/** The friend requests. */
	private HashMap<String, ArrayList<String>> friendRequests = new HashMap<String, ArrayList<String>>();

	/** The friends. */
	private ArrayList<String[]> friends = new ArrayList<String[]>();

	/**
	 * Instantiates a new friend manager.
	 */
	public FriendManager() {
		SH.getPlugin().getCommand("venn")
				.setExecutor(new FriendManagerCommand());
		try {
			createTables();
			updateFriendsList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the friendrequest.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	public void addFriendrequest(String from, String to) {
		if (!friendRequests.containsKey(to) || friendRequests.get(to) == null) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(from);
			friendRequests.put(to, list);
			return;
		}
		friendRequests.get(to).add(from);
	}

	/**
	 * Creates the tables.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void createTables() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("CREATE TABLE IF NOT EXISTS friend ( "
				+ "`id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, "
				+ "`player1` VARCHAR(45) NOT NULL, "
				+ "`player2` VARCHAR(45) NOT NULL " + ")");
	}

	/**
	 * Gets the friendrequests to.
	 *
	 * @param name
	 *            the name
	 * @return the friendrequests to
	 */
	public ArrayList<String> getFriendrequestsTo(String name) {
		return friendRequests.get(name);
	}

	/**
	 * Gets the friends with.
	 *
	 * @param pd
	 *            the pd
	 * @return the friends with
	 */
	public ArrayList<IPlayerData> getFriendsWith(IPlayerData pd) {
		return getFriendsWith(pd.getUUID());
	}

	/**
	 * Gets the friends with.
	 *
	 * @param uuid
	 *            the uuid
	 * @return the friends with
	 */
	public ArrayList<IPlayerData> getFriendsWith(String uuid) {
		ArrayList<IPlayerData> ret = new ArrayList<IPlayerData>();
		for (String[] friendship : friends) {
			String p1 = friendship[0];
			String p2 = friendship[1];
			if (p1.equalsIgnoreCase(uuid)) {
				ret.add(SH.getManager().getPlayerDataManager()
						.getPlayerData(p2));
				continue;
			} else if (p2.equalsIgnoreCase(uuid)) {
				ret.add(SH.getManager().getPlayerDataManager()
						.getPlayerData(p1));
				continue;
			}
		}
		return ret;
	}

	/**
	 * Checks if is friends.
	 *
	 * @param pd1
	 *            the pd1
	 * @param pd2
	 *            the pd2
	 * @return true, if is friends
	 */
	public boolean isFriends(IPlayerData pd1, IPlayerData pd2) {
		return isFriends(pd1.getUUID(), pd2.getUUID());
	}

	/**
	 * Checks if is friends.
	 *
	 * @param uuid1
	 *            the uuid1
	 * @param uuid2
	 *            the uuid2
	 * @return true, if is friends
	 */
	public boolean isFriends(String uuid1, String uuid2) {
		if (uuid1.equals(uuid2))
			return true;
		ArrayList<IPlayerData> friends = getFriendsWith(uuid1);
		for (IPlayerData friend : friends) {
			if (friend.getUUID().equalsIgnoreCase(uuid2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the friend request.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	public void removeFriendRequest(String from, String to) {
		if (!friendRequests.containsKey(to) || friendRequests.get(to) == null) {
			return;
		}
		friendRequests.get(to).remove(from);
	}

	/**
	 * Removes the friendship.
	 *
	 * @param uuid1
	 *            the uuid1
	 * @param uuid2
	 *            the uuid2
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void removeFriendship(String uuid1, String uuid2)
			throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("DELETE FROM friend WHERE player1 = '" + uuid1
				+ "' AND player2 = '" + uuid2 + "' OR player1 = '" + uuid2
				+ "' AND player2 = '" + uuid1 + "'");
		String[] rem = null;
		for (String[] friendship : friends) {
			String p1 = friendship[0];
			String p2 = friendship[1];

			if (p1.equalsIgnoreCase(uuid1) && p2.equalsIgnoreCase(uuid2)
					|| p2.equalsIgnoreCase(uuid1) && p1.equalsIgnoreCase(uuid2)) {
				rem = friendship;
			}
		}
		friends.remove(rem);
	}

	/**
	 * Save friends list.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void saveFriendsList() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		for (String[] friendship : friends) {
			String p1 = friendship[0];
			String p2 = friendship[1];
			sql.query("INSERT INTO friend( player1, player2 ) VALUES ( '" + p1
					+ "', '" + p2 + "' )");
		}
	}

	/**
	 * Sets the friendrequests to.
	 *
	 * @param name
	 *            the name
	 * @param list
	 *            the list
	 */
	public void setFriendrequestsTo(String name, ArrayList<String> list) {
		friendRequests.put(name, list);
	}

	/**
	 * Sets the friends.
	 *
	 * @param one
	 *            the one
	 * @param two
	 *            the two
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void setFriends(IPlayerData one, IPlayerData two)
			throws SQLException {
		setFriends(one.getUUID(), two.getUUID());
	}

	/**
	 * Sets the friends.
	 *
	 * @param uuid1
	 *            the uuid1
	 * @param uuid2
	 *            the uuid2
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void setFriends(String uuid1, String uuid2) throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("INSERT INTO friend( player1, player2 ) VALUES ( '" + uuid1
				+ "', '" + uuid2 + "' )");
		String[] friendship = { uuid1, uuid2 };
		friends.add(friendship);
	}

	/**
	 * Update friends list.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void updateFriendsList() throws SQLException {
		ArrayList<String[]> friends = new ArrayList<String[]>();
		IMysqlManager sql = SH.getManager().getMysqlManager();
		ResultSet rs = sql.query("SELECT * FROM friend");
		while (rs != null && rs.next()) {
			String[] friendship = { rs.getString("player1"),
					rs.getString("player2") };
			friends.add(friendship);
		}
		this.friends = friends;
	}
}
