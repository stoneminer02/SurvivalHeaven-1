package info.nordbyen.survivalheaven.subplugins.commands.commands;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.wand.Wand;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminStickWand implements Wand {

	public AdminStickWand() {
		SH.getManager().getWandManager().add(this);
	}

	@Override
	public boolean canCreate(ItemStack itemStack, Player player) {
		return true;
	}

	@Override
	public String getName() {
		return ChatColor.GOLD + "AdminStick";
	}

	@Override
	public void onLeftClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face) {
		player.sendMessage(ChatColor.GOLD + "Du venstreklikket :D");
	}

	@Override
	public void onRightClick(ItemStack itemStack, Player player,
			Block bockedClick, BlockFace face) {
		player.sendMessage(ChatColor.GOLD + "Du høyreklikket D:");
	}

}