package info.nordbyen.survivalheaven.subplugins.homes;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class HomeManager {

	private HashMap<String, ArrayList<Home>> homesOfPlayer = new HashMap<String, ArrayList<Home>>();

	public HomeManager() {
		try {
			HomeManagerCommand hcm = new HomeManagerCommand();
			SH.getPlugin().getCommand("sethome").setExecutor(hcm);
			SH.getPlugin().getCommand("delhome").setExecutor(hcm);
			SH.getPlugin().getCommand("home").setExecutor(hcm);
			SH.getPlugin().getCommand("homes").setExecutor(hcm);
			createTable();
			updateFromDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addHome(Home home) throws SQLException {
		String uuid = home.getUuid();
		if (getHomeFromPlayer(home.getName(), uuid) != null) {
			return false;
		}
		if (homesOfPlayer.get(uuid) == null) {
			homesOfPlayer.put(uuid, new ArrayList<Home>());
		}
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("INSERT INTO home ( name, uuid, world, x, y, z ) VALUES ( '"
				+ home.getName() + "', '" + uuid + "', '"
				+ home.getLocation().getWorld().getName() + "', "
				+ home.getLocation().getBlockX() + ", "
				+ home.getLocation().getBlockY() + ", "
				+ home.getLocation().getBlockZ() + " )");
		homesOfPlayer.get(uuid).add(home);
		return true;
	}

	public void createTable() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("CREATE TABLE IF NOT EXISTS home ( "
				+ "`id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, "
				+ "`name` VARCHAR(45) NOT NULL, "
				+ "`uuid` VARCHAR(45) NOT NULL, "
				+ "`world` VARCHAR(45) NOT NULL, " + "`x` INT(11) NOT NULL,"
				+ "`y` INT(11) NOT NULL," + "`z` INT(11) NOT NULL );");
	}

	public void deleteHomeFromPlayer(String home, String uuid)
			throws SQLException {
		Home toBeDel = null;
		ArrayList<Home> homes = homesOfPlayer.get(uuid);
		for (Home h : homes) {
			if (h.getName().equalsIgnoreCase(home)) {
				toBeDel = h;
			}
		}
		if (toBeDel == null)
			return;

		IMysqlManager sql = SH.getManager().getMysqlManager();
		sql.query("DELETE FROM home WHERE name = '" + toBeDel.getName()
				+ "' AND uuid = '" + toBeDel.getUuid() + "';");

		homes.remove(toBeDel);
	}

	public Home getHomeFromPlayer(String home, PlayerData pd) {
		return getHomeFromPlayer(home, pd.getUUID());
	}

	public Home getHomeFromPlayer(String home, String uuid) {
		if (getHomesFromPlayer(uuid) == null)
			homesOfPlayer.put(uuid, new ArrayList<Home>());
		ArrayList<Home> homes = getHomesFromPlayer(uuid);
		for (Home h : homes) {
			if (h.getName().equalsIgnoreCase(home)) {
				return h;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Home> getHomesFromPlayer(PlayerData pd) {
		return (ArrayList<Home>) getHomesFromPlayer(pd.getUUID()).clone();
	}

	public ArrayList<Home> getHomesFromPlayer(String uuid) {
		return homesOfPlayer.get(uuid);
	}

	public void updateFromDatabase() throws SQLException {
		IMysqlManager sql = SH.getManager().getMysqlManager();
		ResultSet rs = sql
				.query("SELECT name, uuid, world, x, y, z FROM home;");
		while (rs != null && rs.next()) {
			String name = rs.getString("name");
			String uuid = rs.getString("uuid");
			World w = Bukkit.getWorld(rs.getString("world"));
			int x = rs.getInt("x");
			int y = rs.getInt("y");
			int z = rs.getInt("z");
			Location loc = new Location(w, x, y, z);
			Home home = new Home(name, uuid, loc);
			if (homesOfPlayer.get(uuid) == null) {
				homesOfPlayer.put(uuid, new ArrayList<Home>());
			}
			homesOfPlayer.get(uuid).add(home);
		}
	}
}
