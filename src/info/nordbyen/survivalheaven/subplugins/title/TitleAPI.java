/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.title;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * The Class TitleAPI.
 */
public class TitleAPI extends SubPlugin implements Listener {

	/**
	 * Send tab title.
	 *
	 * @param player
	 *            the player
	 * @param header
	 *            the header
	 * @param footer
	 *            the footer
	 */
	public static void sendTabTitle(final Player player, String header,
			String footer) {
		if (header == null) {
			header = "";
		}
		header = ChatColor.translateAlternateColorCodes('&', header);
		if (footer == null) {
			footer = "";
		}
		footer = ChatColor.translateAlternateColorCodes('&', footer);
		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());
		final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		final IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \""
				+ header + "\"}");
		final IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \""
				+ footer + "\"}");
		final PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(
				tabTitle);
		try {
			final Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			connection.sendPacket(headerPacket);
		}
	}

	/**
	 * Send title.
	 *
	 * @param player
	 *            the player
	 * @param fadeIn
	 *            the fade in
	 * @param stay
	 *            the stay
	 * @param fadeOut
	 *            the fade out
	 * @param title
	 *            the title
	 * @param subtitle
	 *            the subtitle
	 */
	public static void sendTitle(final Player player, final Integer fadeIn,
			final Integer stay, final Integer fadeOut, String title,
			String subtitle) {
		final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		final PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(
				EnumTitleAction.TIMES, null, fadeIn.intValue(),
				stay.intValue(), fadeOut.intValue());
		connection.sendPacket(packetPlayOutTimes);
		if (subtitle != null) {
			subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
			final IChatBaseComponent titleSub = ChatSerializer
					.a("{\"text\": \"" + subtitle + "\"}");
			final PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(
					EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket(packetPlayOutSubTitle);
		}
		if (title != null) {
			title = title.replaceAll("%player%", player.getDisplayName());
			title = ChatColor.translateAlternateColorCodes('&', title);
			final IChatBaseComponent titleMain = ChatSerializer
					.a("{\"text\": \"" + title + "\"}");
			final PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(
					EnumTitleAction.TITLE, titleMain);
			connection.sendPacket(packetPlayOutTitle);
		}
	}

	/**
	 * Instantiates a new title api.
	 *
	 * @param name
	 *            the name
	 */
	public TitleAPI(final String name) {
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
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
	}

	/**
	 * Checks if is integer.
	 *
	 * @param s
	 *            the s
	 * @return true, if is integer
	 */
	boolean isInteger(final String s) {
		try {
			Integer.parseInt(s);
		} catch (final NumberFormatException e) {
			return false;
		}
		return true;
	}
}
