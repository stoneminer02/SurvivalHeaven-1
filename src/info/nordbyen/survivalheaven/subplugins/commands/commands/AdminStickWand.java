/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.wand.Wand;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Class AdminStickWand.
 */
public class AdminStickWand implements Wand {

	/**
	 * Instantiates a new admin stick wand.
	 */
	public AdminStickWand() {
		SH.getManager().getWandManager().add(this);
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.api.wand.Wand#canCreate(org.bukkit.inventory.ItemStack, org.bukkit.entity.Player)
	 */
	@Override
	public boolean canCreate(ItemStack itemStack, Player player) {
		return true;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.api.wand.Wand#getName()
	 */
	@Override
	public String getName() {
		return ChatColor.GOLD + "AdminStick";
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.api.wand.Wand#onLeftClick(org.bukkit.inventory.ItemStack, org.bukkit.entity.Player, org.bukkit.block.Block, org.bukkit.block.BlockFace)
	 */
	@Override
	public void onLeftClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face) {
		player.sendMessage(ChatColor.GOLD + "Du venstreklikket :D");
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.api.wand.Wand#onRightClick(org.bukkit.inventory.ItemStack, org.bukkit.entity.Player, org.bukkit.block.Block, org.bukkit.block.BlockFace)
	 */
	@Override
	public void onRightClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face) {
		player.sendMessage(ChatColor.GOLD + "Du høyreklikket D:");
	}

}