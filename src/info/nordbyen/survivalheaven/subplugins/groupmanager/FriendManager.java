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

package info.nordbyen.survivalheaven.subplugins.groupmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The Class FriendManagerPlugin.
 */
public class FriendManager {

	public FriendManager() {
		SH.getPlugin().getCommand("venn").setExecutor(new FriendManagerCommand());
		try {
			createTables();
			updateFriendsList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private HashMap< String, ArrayList< String > > friendRequests = new HashMap< String, ArrayList< String > >(); 
	private ArrayList< String[] > friends = new ArrayList< String[] >();

	public void createTables() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query( "CREATE TABLE IF NOT EXISTS friend ( "
				+ "`id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, "
				+ "`player1` VARCHAR(45) NOT NULL, "
				+ "`player2` VARCHAR(45) NOT NULL "
				+ ")" );
	}

	public void updateFriendsList() throws SQLException {
		ArrayList< String[] > friends = new ArrayList< String[] >();
		IMysqlManager sql = SH.getManager().getMysqlManager();
		ResultSet rs = sql.query( "SELECT * FROM friend" );
		while( rs != null && rs.next() ) {
			String[] friendship = { rs.getString( "player1" ), rs.getString( "player2" ) };
			friends.add( friendship );
		}
		this.friends = friends;
	}

	public void saveFriendsList() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		for( String[] friendship : friends ) {
			String p1 = friendship[0];
			String p2 = friendship[1];
			sql.query( "INSERT INTO friend( player1, player2 ) VALUES ( '" + p1 + "', '" + p2 + "' )" );
		}
	}

	public ArrayList< IPlayerData > getFriendsWith( String uuid ){
		ArrayList< IPlayerData > ret = new ArrayList< IPlayerData >();
		for( String[] friendship : friends ) {
			String p1 = friendship[0];
			String p2 = friendship[1];
			if( p1.equalsIgnoreCase( uuid ) ) {
				ret.add( SH.getManager().getPlayerDataManager().getPlayerData( p2 ) );
				continue;
			} else if( p2.equalsIgnoreCase( uuid ) ) {
				ret.add( SH.getManager().getPlayerDataManager().getPlayerData( p1 ) );
				continue;
			}
		}
		return ret;
	}

	public ArrayList< IPlayerData > getFriendsWith( IPlayerData pd ) {
		return getFriendsWith( pd.getUUID() );
	}


	public void setFriends( String uuid1, String uuid2 ) throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query( "INSERT INTO friend( player1, player2 ) VALUES ( '" + uuid1 + "', '" + uuid2 + "' )" );
		String[] friendship = { uuid1, uuid2 };
		friends.add( friendship );
	}

	public void setFriends( IPlayerData one, IPlayerData two ) throws SQLException {
		setFriends( one.getUUID(), two.getUUID() );
	}

	public void removeFriendship( String uuid1, String uuid2 ) throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query( "DELETE FROM friend WHERE player1 = '" + uuid1 + "' AND player2 = '" + uuid2 + "' OR player1 = '" + uuid2 + "' AND player2 = '" + uuid1 + "'" );
		String[] rem = null;
		for( String[] friendship : friends ) {
			String p1 = friendship[0];
			String p2 = friendship[1];

			if( p1.equalsIgnoreCase( uuid1 ) && p2.equalsIgnoreCase( uuid2 ) 
					|| p2.equalsIgnoreCase( uuid1 ) && p1.equalsIgnoreCase( uuid2 ) ) {
				rem = friendship;
			}
		}
		friends.remove( rem );
	}

	public boolean isFriends( String uuid1, String uuid2 ) {
		if( uuid1.equals( uuid2 ) ) return true;
		ArrayList< IPlayerData> friends = getFriendsWith( uuid1 );
		for( IPlayerData friend : friends ) {
			if( friend.getUUID().equalsIgnoreCase( uuid2 ) ) {
				return true;
			}
		}
		return false;
	}

	public boolean isFriends( IPlayerData pd1, IPlayerData pd2 ) {
		return isFriends( pd1.getUUID(), pd2.getUUID() );
	}
	
	public ArrayList<String> getFriendrequestsTo( String name ) {
		return friendRequests.get( name );
	}
	
	public void setFriendrequestsTo( String name, ArrayList< String > list ) {
		friendRequests.put( name, list );
	}
	
	public void addFriendrequest( String from, String to ) {
		if( !friendRequests.containsKey( to ) || friendRequests.get( to ) == null ) {
			ArrayList<String> list = new ArrayList<String>();
			list.add( from );
			friendRequests.put( to, list );
			return;
		}
		friendRequests.get( to ).add( from );
	}
	
	public void removeFriendRequest( String from, String to ) {
		if( !friendRequests.containsKey( to ) || friendRequests.get( to ) == null ) {
			return;
		}
		friendRequests.get( to ).remove( from );
	}
}
