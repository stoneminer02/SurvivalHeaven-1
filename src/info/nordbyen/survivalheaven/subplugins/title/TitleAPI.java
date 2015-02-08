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
