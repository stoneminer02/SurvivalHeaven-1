package info.nordbyen.survivalheaven.subplugins.playerheads;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

public class PlayerHeads extends SubPlugin implements Listener
{

	public PlayerHeads(String name)
	{
		super(name);
	}

	@Override
	protected void disable()
	{

	}

	@Override
	protected void enable()
	{
		Bukkit.getPluginManager().registerEvents(this, SH.getPlugin());
	}

	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if ((e.getEntity().getKiller() instanceof Player))
		{
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta hd = (SkullMeta) head.getItemMeta();
			hd.setDisplayName(ChatColor.YELLOW + p.getName() + "s hode");
			hd.setOwner(p.getName());
			head.setItemMeta(hd);
			e.getEntity().getWorld()
					.dropItem(e.getEntity().getLocation(), head);
		}
	}
}