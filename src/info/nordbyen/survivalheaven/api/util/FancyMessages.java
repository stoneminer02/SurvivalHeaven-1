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

package info.nordbyen.survivalheaven.api.util;

import info.nordbyen.survivalheaven.subplugins.bossbar.BossbarAPI;
import info.nordbyen.survivalheaven.subplugins.title.TitleAPI;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * The Class FancyMessages.
 */
public class FancyMessages {

	/**
	 * Checks for bar.
	 * 
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public static boolean hasBar(final Player p) {
		return BossbarAPI.hasBar(p);
	}

	/**
	 * Removes the bar.
	 * 
	 * @param p
	 *            the p
	 */
	public static void removeBar(final Player p) {
		BossbarAPI.removeBar(p);
	}

	/**
	 * Send action bar.
	 * 
	 * @param player
	 *            the player
	 * @param message
	 *            the message
	 */
	public static void sendActionBar(final Player player, final String message) {
		final CraftPlayer p = (CraftPlayer) player;
		final IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \""
				+ message + "\"}");
		final PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		p.getHandle().playerConnection.sendPacket(ppoc);
	}

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
	public static void sendTabTitle(final Player player, final String header,
			final String footer) {
		TitleAPI.sendTabTitle(player, header, footer);
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
			final Integer stay, final Integer fadeOut, final String title,
			final String subtitle) {
		TitleAPI.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
	}

	/**
	 * Sets the bar.
	 * 
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 */
	public static void setBar(final Player p, final String text) {
		BossbarAPI.setBar(p, text);
	}

	/**
	 * Sets the bar health.
	 * 
	 * @param p
	 *            the p
	 * @param text
	 *            the text
	 * @param health
	 *            the health
	 */
	public static void setBarHealth(final Player p, final String text,
			final float health) {
		BossbarAPI.setBarDragonHealth(p, text, health);
	}
}
