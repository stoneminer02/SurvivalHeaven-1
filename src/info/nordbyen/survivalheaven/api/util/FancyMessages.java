/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
