/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import net.minecraft.server.v1_8_R1.NBTBase;
import net.minecraft.server.v1_8_R1.NBTTagCompound;

import org.bukkit.enchantments.Enchantment;

/**
 * The Class StoredEnchantment.
 */
public class StoredEnchantment {

	/** The ench. */
	private Enchantment ench;
	
	/** The lvl. */
	private short lvl;

	/**
	 * Instantiates a new stored enchantment.
	 *
	 * @param ench
	 *            the ench
	 * @param lvl
	 *            the lvl
	 */
	public StoredEnchantment(final Enchantment ench, final int lvl) {
		this.setEnchantment(ench);
		setLevel((short) lvl);
	}

	/**
	 * Instantiates a new stored enchantment.
	 *
	 * @param tag
	 *            the tag
	 */
	public StoredEnchantment(final NBTTagCompound tag) {
		setEnchantment(tag.getShort("id"));
		setLevel(tag.getShort("lvl"));
	}

	/**
	 * Gets the enchantment.
	 *
	 * @return the enchantment
	 */
	public Enchantment getEnchantment() {
		return ench;
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public short getLevel() {
		return lvl;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	@SuppressWarnings("deprecation")
	public NBTBase getTag() {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("id", (short) getEnchantment().getId());
		tag.setShort("lvl", getLevel());
		return tag;
	}

	/**
	 * Sets the enchantment.
	 *
	 * @param ench
	 *            the new enchantment
	 */
	public void setEnchantment(final Enchantment ench) {
		this.ench = ench;
	}

	/**
	 * Sets the enchantment.
	 *
	 * @param ench
	 *            the new enchantment
	 */
	@SuppressWarnings("deprecation")
	public void setEnchantment(final int ench) {
		this.ench = Enchantment.getById(ench);
	}

	/**
	 * Sets the level.
	 *
	 * @param lvl
	 *            the new level
	 */
	public void setLevel(final short lvl) {
		this.lvl = lvl;
	}
}
