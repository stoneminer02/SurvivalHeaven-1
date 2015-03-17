/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

/**
 * The Class Smelt.
 */
public class Smelt implements CommandExecutor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(final CommandSender Sender, final Command command,
			final String CommandLabel, final String[] args) {
		if (Sender instanceof Player) {
			final Player p = (Player) Sender;
			if (Sender.hasPermission("sh.smelt")) {
				if (command.getName().equalsIgnoreCase("smelt")) {
					if (args.length == 0) {
						if (p.getItemInHand().getType() != null) {
							final int g = p.getItemInHand().getAmount() / 8;
							final ItemStack i = new ItemStack(Material.COAL);
							p.updateInventory();
							switch (p.getItemInHand().getType()) {
							case COBBLESTONE:
								for (final ItemStack h : p.getInventory()
										.getContents()) {
									if ((h.getType() == Material.COAL)
											&& (h.getAmount() > g)) {
										p.getInventory().remove(i);
										p.setItemInHand(new ItemStack(
												Material.STONE, p
														.getItemInHand()
														.getAmount()));
									} else {
										p.sendMessage(ChatColor.RED
												+ "Du har ikke nok kull");
									}
								}
								break;
							case IRON_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.IRON_INGOT, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case COAL_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.COAL, p.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case SAND:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.GLASS, p.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case GOLD_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.GOLD_INGOT, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case RAW_BEEF:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.COOKED_BEEF, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case RAW_FISH:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.COOKED_FISH, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case PORK:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.GRILLED_PORK, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case POTATO:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.BAKED_POTATO, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case RAW_CHICKEN:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.COOKED_CHICKEN, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case CLAY_BALL:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.CLAY_BRICK, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case CLAY:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.STAINED_CLAY, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case NETHERRACK:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.NETHER_BRICK, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case DIAMOND_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.DIAMOND, p.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case REDSTONE_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.REDSTONE, p
													.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case EMERALD_ORE:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.EMERALD, p.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case QUARTZ_BLOCK:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.QUARTZ, p.getItemInHand()
													.getAmount()));
								} else {
									p.sendMessage(ChatColor.RED
											+ "Du har ikke nok kull");
								}
								break;
							case LAPIS_ORE:
								if (p.getItemInHand().getType()
										.equals(Material.LAPIS_ORE)) {
									final Dye d = new Dye();
									d.setColor(DyeColor.BLUE);
									p.setItemInHand(d.toItemStack(p
											.getItemInHand().getAmount()));
								}
								break;
							case CACTUS:
								final Dye d = new Dye();
								d.setColor(DyeColor.GREEN);
								p.setItemInHand(d.toItemStack(p.getItemInHand()
										.getAmount()));
								break;
							case LOG:
								if (p.getInventory().contains(i)
										&& (i.getAmount() > g)) {
									p.getInventory().remove(i);
									p.setItemInHand(new ItemStack(
											Material.COAL, p.getItemInHand()
													.getAmount()));
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		} else {
			Sender.sendMessage("Du er ikke en in-game spiller");
		}
		return true;
	}
}
