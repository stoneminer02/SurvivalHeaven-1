/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.ligg;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.comphenix.protocol.reflect.StructureModifier;

/**
 * The Class Ligg.
 */
@SuppressWarnings("deprecation")
public class Ligg extends SubPlugin implements Listener {

	/**
	 * Gets the single instance of Ligg.
	 *
	 * @return single instance of Ligg
	 */
	public static Ligg getInstance() {
		return instance;
	}

	/** The instance. */
	private static Ligg instance;

	/** The use bed constructor. */
	private PacketConstructor useBedConstructor;
	
	/** The relative move constructor. */
	private PacketConstructor relativeMoveConstructor;
	
	/** The manager. */
	private ProtocolManager manager;
	
	/** The offset y. */
	private final Map<String, Integer> offsetY = new HashMap<String, Integer>();

	/**
	 * Instantiates a new ligg.
	 *
	 * @param name
	 *            the name
	 */
	public Ligg(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	public void disable() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	public void enable() {
		instance = this;
		LiggCommand.initCommand();
		manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(getPlugin(),
				ConnectionSide.SERVER_SIDE, Packets.Server.ENTITY_TELEPORT) {

			@Override
			public void onPacketSending(final PacketEvent event) {
				final PacketContainer packet = event.getPacket();
				final Player receiver = event.getPlayer();
				try {
					final Entity target = packet.getEntityModifier(
							receiver.getWorld()).read(0);
					final int offset = getOffsetY(target);
					// Only modify the value if we need to
					if (offset != 0) {
						final StructureModifier<Integer> ints = packet
								.getSpecificModifier(int.class);
						ints.write(2, ints.read(2) + offset);
					}
				} catch (final FieldAccessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Gets the offset y.
	 *
	 * @param target
	 *            the target
	 * @return the offset y
	 */
	private int getOffsetY(final Entity target) {
		if (target instanceof Player) {
			final String name = ((Player) target).getName();
			final Integer result = offsetY.get(name);
			if (result != null)
				return result;
		}
		// Default value
		return 0;
	}

	/**
	 * Perform action.
	 *
	 * @param sender
	 *            the sender
	 */
	public void performAction(final CommandSender sender) {
		sender.sendMessage("Cannot perform command for console!");
	}

	/**
	 * Perform action.
	 *
	 * @param player
	 *            the player
	 */
	public void performAction(final Player player) {
		final Location loc = player.getLocation();
		if (useBedConstructor == null) {
			useBedConstructor = manager.createPacketConstructor(
					Packets.Server.ENTITY_LOCATION_ACTION, player, 0, 0, 0, 0);
		}
		if (relativeMoveConstructor == null) {
			relativeMoveConstructor = manager.createPacketConstructor(
					Packets.Server.REL_ENTITY_MOVE, 0, (byte) 0, (byte) 0,
					(byte) 0);
		}
		// Make the player appear asleep
		try {
			final int OFFSET_Y = 2;
			final PacketContainer sleepPacket = useBedConstructor.createPacket(
					player, 0, loc.getBlockX(), loc.getBlockY() + 2,
					loc.getBlockZ());
			final PacketContainer movePacket = relativeMoveConstructor
					.createPacket(player.getEntityId(), (byte) 0,
							(byte) (OFFSET_Y * 32), (byte) 0);
			// Broadcast this to every nearby player
			for (final Player other : getPlugin().getServer()
					.getOnlinePlayers()) {
				if (!other.equals(player)) {
					manager.sendServerPacket(other, sleepPacket);
					manager.sendServerPacket(other, movePacket);
				}
			}
			offsetY.put(player.getName(), OFFSET_Y);
		} catch (final FieldAccessException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
