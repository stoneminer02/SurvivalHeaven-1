/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bossbar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The Class FWither.
 */
public class FWither {

	/**
	 * Change watcher.
	 *
	 * @param nms_entity
	 *            the nms_entity
	 * @param text
	 *            the text
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	private static void changeWatcher(final Object nms_entity, final String text)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Object nms_watcher = getDatawatcher.invoke(nms_entity);
		final Map<?, ?> map = (Map<?, ?>) d.get(nms_watcher);
		map.remove(10);
		a.invoke(nms_watcher, 10, text);
	}

	/**
	 * Gets the cardinal direction.
	 *
	 * @param player
	 *            the player
	 * @return the cardinal direction
	 */
	public static String getCardinalDirection(final Player player) {
		double rotation = (player.getLocation().getYaw() - 180) % 360;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if ((0 <= rotation) && (rotation < 22.5))
			return "N";
		else if ((22.5 <= rotation) && (rotation < 67.5))
			return "NE";
		else if ((67.5 <= rotation) && (rotation < 112.5))
			return "E";
		else if ((112.5 <= rotation) && (rotation < 157.5))
			return "SE";
		else if ((157.5 <= rotation) && (rotation < 202.5))
			return "S";
		else if ((202.5 <= rotation) && (rotation < 247.5))
			return "SW";
		else if ((247.5 <= rotation) && (rotation < 292.5))
			return "W";
		else if ((292.5 <= rotation) && (rotation < 337.5))
			return "NW";
		else if ((337.5 <= rotation) && (rotation <= 360.0))
			return "N";
		else
			return "N";
	}

	/**
	 * Gets the craft class.
	 *
	 * @param name
	 *            the name
	 * @return the craft class
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private static Class<?> getCraftClass(final String name)
			throws ClassNotFoundException {
		final String version = Bukkit.getServer().getClass().getPackage()
				.getName().replace(".", ",").split(",")[3]
				+ ".";
		final String className = "org.bukkit.craftbukkit." + version + name;
		return Class.forName(className);
	}

	/**
	 * Gets the MC class.
	 *
	 * @param name
	 *            the name
	 * @return the MC class
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private static Class<?> getMCClass(final String name)
			throws ClassNotFoundException {
		final String version = Bukkit.getServer().getClass().getPackage()
				.getName().replace(".", ",").split(",")[3]
				+ ".";
		final String className = "net.minecraft.server." + version + name;
		return Class.forName(className);
	}

	/**
	 * Gets the player loc.
	 *
	 * @param p
	 *            the p
	 * @return the player loc
	 */
	public static Location getPlayerLoc(final Player p) {
		final Location loc = p.getLocation();
		switch (getCardinalDirection(p)) {
		case ("N"):
			loc.add(0, 0, -50);
			break;
		case ("E"):
			loc.add(50, 0, 0);
			break;
		case ("S"):
			loc.add(0, 0, 50);
			break;
		case ("W"):
			loc.add(-50, 0, 0);
			break;
		case ("NE"):
			loc.add(50, 0, -50);
			break;
		case ("SE"):
			loc.add(50, 0, 50);
			break;
		case ("NW"):
			loc.add(-50, 0, -50);
			break;
		case ("SW"):
			loc.add(-50, 0, 50);
			break;
		}
		return loc;
	}

	/**
	 * Gets the wither.
	 *
	 * @param p
	 *            the p
	 * @return the wither
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	public static Object getWither(final Player p)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		if (playerWithers.containsKey(p.getName()))
			return playerWithers.get(p.getName());
		else {
			final Object nms_world = getWorldHandle.invoke(p.getWorld());
			playerWithers.put(p.getName(),
					entityEntityWither.newInstance(nms_world));
			return getWither(p);
		}
	}

	/**
	 * Gets the wither2.
	 *
	 * @param p
	 *            the p
	 * @return the wither2
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 */
	public static Object getWither2(final Player p)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		if (playerWithers2.containsKey(p.getName()))
			return playerWithers2.get(p.getName());
		else {
			final Object nms_world = getWorldHandle.invoke(p.getWorld());
			playerWithers2.put(p.getName(),
					entityEntityWither.newInstance(nms_world));
			return getWither2(p);
		}
	}

	/**
	 * Removes the boss bar.
	 *
	 * @param p
	 *            the p
	 */
	public static void removeBossBar(final Player p) {
		playerTextWither.remove(p.getName());
		try {
			final Object nms_wither = getWither(p);
			setLocation.invoke(nms_wither, p.getLocation().getX(), -5000, p
					.getLocation().getZ(), 0F, 0F);
			setCustomName.invoke(nms_wither, " ");
			setHealth.invoke(nms_wither, 0);
			setInvisible.invoke(nms_wither, true);
			changeWatcher(nms_wither, " ");
			final Object nms_packet = packetPlayOutSpawnEntityLiving
					.newInstance(nms_wither);
			final Object nms_player = getPlayerHandle.invoke(p);
			final Object nms_connection = playerConnection.get(nms_player);
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removehorligne w.
	 *
	 * @param p
	 *            the p
	 */
	public static void removehorligneW(final Player p) {
		playerWithers.remove(p.getName());
		playerTextWither.remove(p.getName());
	}

	/**
	 * Sets the boss bar.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param vie
	 *            the vie
	 */
	public static void setBossBar(final Player p, final String text,
			final float vie) {
		playerTextWither.put(p.getName(), text);
		final int xr = ThreadLocalRandom.current().nextInt(0, 2);
		final int xr2 = ThreadLocalRandom.current().nextInt(0, 2);
		try {
			final Object nms_wither = getWither(p);
			setLocation.invoke(nms_wither, getPlayerLoc(p).getX() + xr,
					getPlayerLoc(p).getY() - 3, getPlayerLoc(p).getZ() + xr2,
					0F, 0F);
			setCustomName.invoke(nms_wither, text);
			setHealth.invoke(nms_wither, vie);
			setInvisible.invoke(nms_wither, true);
			changeWatcher(nms_wither, text);
			final Object nms_packet = packetPlayOutSpawnEntityLiving
					.newInstance(nms_wither);
			final Object nms_player = getPlayerHandle.invoke(p);
			final Object nms_connection = playerConnection.get(nms_player);
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { Object nms_wither = getWither2(p);
		 * setLocation.invoke(nms_wither, getPlayerLoc(p).getX()+xr2,
		 * p.getLocation().getY()-10, getPlayerLoc(p).getZ()+xr, 0F, 0F);
		 * setCustomName.invoke(nms_wither,text);
		 * setHealth.invoke(nms_wither,vie);
		 * setInvisible.invoke(nms_wither,true); changeWatcher(nms_wither,
		 * text); Object nms_packet =
		 * packetPlayOutSpawnEntityLiving.newInstance(nms_wither); Object
		 * nms_player = getPlayerHandle.invoke(p); Object nms_connection =
		 * playerConnection.get(nms_player); sendPacket.invoke(nms_connection,
		 * nms_packet); } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	/**
	 * Sets the boss bartext.
	 *
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 */
	public static void setBossBartext(final Player p, final String text) {
		playerTextWither.put(p.getName(), text);
		final int xr = ThreadLocalRandom.current().nextInt(-3, 3);
		final int xr2 = ThreadLocalRandom.current().nextInt(-3, 3);
		try {
			final Object nms_wither = getWither(p);
			setLocation.invoke(nms_wither, getPlayerLoc(p).getX() + xr,
					getPlayerLoc(p).getY() - 3, getPlayerLoc(p).getZ() + xr2,
					0F, 0F);
			setCustomName.invoke(nms_wither, text);
			setHealth.invoke(nms_wither, 300);
			setInvisible.invoke(nms_wither, true);
			changeWatcher(nms_wither, text);
			final Object nms_packet = packetPlayOutSpawnEntityLiving
					.newInstance(nms_wither);
			final Object nms_player = getPlayerHandle.invoke(p);
			final Object nms_connection = playerConnection.get(nms_player);
			sendPacket.invoke(nms_connection, nms_packet);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { Object nms_wither = getWither2(p);
		 * setLocation.invoke(nms_wither, getPlayerLoc(p).getX()+xr2,
		 * p.getLocation().getY()-10, getPlayerLoc(p).getZ()+xr, 0F, 0F);
		 * setCustomName.invoke(nms_wither,text);
		 * setHealth.invoke(nms_wither,300);
		 * setInvisible.invoke(nms_wither,true); changeWatcher(nms_wither,
		 * text); Object nms_packet =
		 * packetPlayOutSpawnEntityLiving.newInstance(nms_wither); Object
		 * nms_player = getPlayerHandle.invoke(p); Object nms_connection =
		 * playerConnection.get(nms_player); sendPacket.invoke(nms_connection,
		 * nms_packet); } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	/** The packet play out spawn entity living. */
	private static Constructor<?> packetPlayOutSpawnEntityLiving;
	
	/** The entity entity wither. */
	private static Constructor<?> entityEntityWither;
	
	/** The set location. */
	private static Method setLocation;
	
	/** The set custom name. */
	private static Method setCustomName;
	
	/** The set health. */
	private static Method setHealth;
	
	/** The set invisible. */
	private static Method setInvisible;

	/** The get world handle. */
	private static Method getWorldHandle;

	/** The get player handle. */
	private static Method getPlayerHandle;

	/** The player connection. */
	private static Field playerConnection;

	/** The send packet. */
	private static Method sendPacket;

	/** The get datawatcher. */
	private static Method getDatawatcher;

	/** The a. */
	private static Method a;

	/** The d. */
	private static Field d;

	/** The player withers. */
	private static Map<String, Object> playerWithers = new HashMap<String, Object>();

	/** The player withers2. */
	private static Map<String, Object> playerWithers2 = new HashMap<String, Object>();

	/** The player text wither. */
	private static Map<String, String> playerTextWither = new HashMap<String, String>();

	static {
		try {
			packetPlayOutSpawnEntityLiving = getMCClass(
					"PacketPlayOutSpawnEntityLiving").getConstructor(
					getMCClass("EntityLiving"));
			entityEntityWither = getMCClass("EntityWither").getConstructor(
					getMCClass("World"));
			setLocation = getMCClass("EntityWither").getMethod("setLocation",
					double.class, double.class, double.class, float.class,
					float.class);
			setCustomName = getMCClass("EntityWither").getMethod(
					"setCustomName", new Class<?>[] { String.class });
			setHealth = getMCClass("EntityWither").getMethod("setHealth",
					new Class<?>[] { float.class });
			setInvisible = getMCClass("EntityWither").getMethod("setInvisible",
					new Class<?>[] { boolean.class });
			getWorldHandle = getCraftClass("CraftWorld").getMethod("getHandle");
			getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod(
					"getHandle");
			playerConnection = getMCClass("EntityPlayer").getDeclaredField(
					"playerConnection");
			sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket",
					getMCClass("Packet"));
			getDatawatcher = getMCClass("EntityWither").getMethod(
					"getDataWatcher");
			a = getMCClass("DataWatcher").getMethod("a", int.class,
					Object.class);
			d = getMCClass("DataWatcher").getDeclaredField("d");
			d.setAccessible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
