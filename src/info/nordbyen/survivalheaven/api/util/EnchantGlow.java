/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;

import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * The Class EnchantGlow.
 */
public class EnchantGlow {

	/**
	 * Adds the glow.
	 *
	 * @param item
	 *            the item
	 * @return the item stack
	 */
	public static ItemStack addGlow(final ItemStack item) {
		final net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack
				.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
			nmsStack.setTag(tag);
		}
		if (tag == null) {
			tag = nmsStack.getTag();
		}
		final NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}

	/**
	 * Removes the glow.
	 *
	 * @param item
	 *            the item
	 * @return the item stack
	 */
	public static ItemStack removeGlow(final ItemStack item) {
		final net.minecraft.server.v1_8_R1.ItemStack nmsStack = CraftItemStack
				.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag())
			return item;
		tag = nmsStack.getTag();
		tag.set("ench", null);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}
}
