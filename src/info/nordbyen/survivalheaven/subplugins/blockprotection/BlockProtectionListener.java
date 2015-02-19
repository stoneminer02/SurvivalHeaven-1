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

package info.nordbyen.survivalheaven.subplugins.blockprotection;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.blockdata.BlockPlacedType;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockProtectionListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBreak(final BlockBreakEvent e) {
		final Block b = e.getBlock();
		final Player p = e.getPlayer();
		if ((b.getType() != Material.AIR) && (b.getType() != Material.WATER)
				&& (b.getType() != Material.LAVA))
		{
			final IPlayerData pd = SH.getManager().getBlockManager()
					.getOwner(b);
			if (pd != null) // Har en eier
			{
				final String uuid = pd.getUUID();
				if (!uuid.equals(p.getUniqueId().toString()))
				{
					final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
					if (!canBreak) // ikke ( venn eller workmode )
					{
						FancyMessages.sendActionBar(p, ChatColor.RED
								+ "Denne blokken er eid av " + pd.getName());
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlace(final BlockPlaceEvent e) {
		final Block b = e.getBlock();
		final Player p = e.getPlayer();

		if( b.getType() == Material.HOPPER ) {
			Location aboveL = b.getLocation();
			aboveL.add( 0, 1, 0 );
			Block above = aboveL.getWorld().getBlockAt( aboveL );

			final IPlayerData pd = SH.getManager().getBlockManager()
					.getOwner(above);
			if (pd != null) // Har en eier
			{
				final String uuid = pd.getUUID();
				if (!uuid.equals(p.getUniqueId().toString()))
				{
					final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
					if (!canBreak) // ikke ( venn eller workmode )
					{
						FancyMessages.sendActionBar(p, ChatColor.RED
								+ "Du kan ikke plassere hoppere under " + pd.getName() + " sine blokker");
						e.setCancelled(true);
						return;
					}
				}
			}
		} else if( b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.CHEST ) {
			BlockFace[] blockfaces = { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
			for( BlockFace bf : blockfaces ) {
				Block bs = b.getRelative( bf );
				if( b.getType() == bs.getType() ) {
					final IPlayerData pd = SH.getManager().getBlockManager().getOwner(bs);
					if (pd != null) // Har en eier
					{
						final String uuid = pd.getUUID();
						if (!uuid.equals(p.getUniqueId().toString()))
						{
							final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
							if (!canBreak) // ikke ( venn eller workmode )
							{
								FancyMessages.sendActionBar(p, ChatColor.RED
										+ "Du kan ikke plassere kister ved siden av " + pd.getName() + " sine kister");
								e.setCancelled(true);
								return;
							}
						}
					}
				}
			}
		}

		Location belL = b.getLocation();
		belL.add( 0, -1, 0 );
		Block bel = belL.getWorld().getBlockAt( belL );
		if( bel.getType() == Material.CHEST || bel.getType() == Material.TRAPPED_CHEST ) {
			final IPlayerData pd = SH.getManager().getBlockManager().getOwner(bel);
			if (pd != null) // Har en eier
			{
				final String uuid = pd.getUUID();
				if (!uuid.equals(p.getUniqueId().toString()))
				{
					final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
					if (!canBreak) // ikke ( venn eller workmode )
					{
						FancyMessages.sendActionBar(p, ChatColor.RED
								+ "Du kan ikke plassere blokker over " + pd.getName() + " sine kister");
						e.setCancelled(true);
						return; 
					}
				}
			}
		}


		BlockPlacedType type = BlockPlacedType.SURVIVAL;
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			type = BlockPlacedType.CREATIVE;
		}
		SH.getManager().getBlockManager().setBlockOwner(b, p, type);
	}

	@EventHandler
	public void onInteract( PlayerInteractEvent e ) {
		final Block b = e.getClickedBlock();
		final Player p = e.getPlayer();
		if( b == null ) return;

		if( e.getAction() == Action.PHYSICAL ) {
			if( e.getClickedBlock().getType() == Material.CROPS || 
					e.getClickedBlock().getType() == Material.POTATO ||
					e.getClickedBlock().getType() == Material.SOIL ) {
				e.setCancelled( true );
				return;
			}
		}

		if( b.getType() == Material.CHEST ||
				b.getType() == Material.FURNACE ||
				b.getType() == Material.TRAPPED_CHEST ||
				b.getType() == Material.DARK_OAK_DOOR ||
				b.getType() == Material.DARK_OAK_DOOR_ITEM ||
				b.getType() == Material.ACACIA_DOOR ||
				b.getType() == Material.ACACIA_DOOR_ITEM ||
				b.getType() == Material.ACACIA_FENCE_GATE ||
				b.getType() == Material.DARK_OAK_FENCE_GATE ||
				b.getType() == Material.ANVIL ||
				b.getType() == Material.ARMOR_STAND ||
				b.getType() == Material.BEACON ||
				b.getType() == Material.BIRCH_DOOR ||
				b.getType() == Material.BIRCH_DOOR_ITEM ||
				b.getType() == Material.BIRCH_FENCE_GATE ||
				b.getType() == Material.WOODEN_DOOR ||
				b.getType() == Material.WOOD_DOOR ||
				b.getType() == Material.WOOD_BUTTON ||
				b.getType() == Material.TRAPPED_CHEST ||
				b.getType() == Material.TRAP_DOOR ||
				b.getType() == Material.TNT ||
				b.getType() == Material.STORAGE_MINECART ||
				b.getType() == Material.STONE_BUTTON ||
				b.getType() == Material.SPRUCE_FENCE_GATE ||
				b.getType() == Material.SPRUCE_DOOR ||
				b.getType() == Material.SPRUCE_DOOR_ITEM ||
				b.getType() == Material.SNOW ||
				b.getType() == Material.LEVER ||
				b.getType() == Material.JUNGLE_DOOR ||
				b.getType() == Material.JUNGLE_DOOR_ITEM ||
				b.getType() == Material.JUNGLE_FENCE_GATE ||
				b.getType() == Material.BREWING_STAND ||
				b.getType() == Material.BURNING_FURNACE ||
				b.getType() == Material.DIODE_BLOCK_OFF ||
				b.getType() == Material.DIODE_BLOCK_ON ||
				b.getType() == Material.DISPENSER ||
				b.getType() == Material.DROPPER ||
				b.getType() == Material.ENCHANTMENT_TABLE ||
				b.getType() == Material.ENDER_CHEST ||
				b.getType() == Material.FENCE_GATE ||
				b.getType() == Material.HOPPER ||
				b.getType() == Material.IRON_DOOR ||
				b.getType() == Material.IRON_DOOR_BLOCK ||
				b.getType() == Material.IRON_TRAPDOOR ||
				b.getType() == Material.WOOD_PLATE ||
				b.getType() == Material.STONE_PLATE ||
				b.getType() == Material.ITEM_FRAME ) {
			final IPlayerData pd = SH.getManager().getBlockManager().getOwner(b);
			if (pd != null) // Har en eier
			{
				final String uuid = pd.getUUID();
				if (!uuid.equals(p.getUniqueId().toString()))
				{
					final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
					if (!canBreak) // ikke ( venn eller workmode )
					{
						FancyMessages.sendActionBar(p, ChatColor.RED
								+ "Denne blokken er eid av " + pd.getName());
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onItemFrameInteract( PlayerInteractEntityEvent e ) {
		if( e.getRightClicked().getType() != EntityType.ITEM_FRAME ) return;
		if( !( e.getRightClicked() instanceof ItemFrame ) ) return;

		Player p = e.getPlayer();

		ItemFrame frame = ( ItemFrame ) e.getRightClicked();
		Block block = frame.getLocation().getWorld().getBlockAt( frame.getLocation() );
		Block back = block.getRelative( frame.getAttachedFace() );

		final IPlayerData pd = SH.getManager().getBlockManager().getOwner( back );
		if (pd != null) // Har en eier
		{
			final String uuid = pd.getUUID();
			if (!uuid.equals(p.getUniqueId().toString()))
			{
				final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
				if (!canBreak) // ikke ( venn eller workmode )
				{
					FancyMessages.sendActionBar(p, ChatColor.RED
							+ "Denne blokken er eid av " + pd.getName());
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onItemBreak(final HangingBreakByEntityEvent e) {
		if( e.getEntity().getType() != EntityType.ITEM_FRAME ) return;
		if( !( e.getEntity() instanceof ItemFrame ) ) return;
		if( !( e.getRemover() instanceof Player ) )  return;

		Player p = ( Player ) e.getRemover();

		ItemFrame frame = ( ItemFrame ) e.getEntity();
		Block block = frame.getLocation().getWorld().getBlockAt( frame.getLocation() );
		Block back = block.getRelative( frame.getAttachedFace() );

		final IPlayerData pd = SH.getManager().getBlockManager().getOwner( back );
		if (pd != null) // Har en eier
		{
			final String uuid = pd.getUUID();
			if (!uuid.equals(p.getUniqueId().toString()))
			{
				final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
				if (!canBreak) // ikke ( venn eller workmode )
				{
					FancyMessages.sendActionBar(p, ChatColor.RED
							+ "Denne blokken er eid av " + pd.getName());
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onItemRemove( EntityDamageByEntityEvent e ) {
		if( e.getEntity().getType() != EntityType.ITEM_FRAME ) return;
		if( !( e.getEntity() instanceof ItemFrame ) ) return;
		if( !( e.getDamager() instanceof Player ) )  return;

		Player p = ( Player ) e.getDamager();

		ItemFrame frame = ( ItemFrame ) e.getEntity();
		Block block = frame.getLocation().getWorld().getBlockAt( frame.getLocation() );
		Block back = block.getRelative( frame.getAttachedFace() );

		final IPlayerData pd = SH.getManager().getBlockManager().getOwner( back );
		if (pd != null) // Har en eier
		{
			final String uuid = pd.getUUID();
			if (!uuid.equals(p.getUniqueId().toString()))
			{
				final boolean canBreak = SH.getManager().getFriendManager().isFriends( pd, SH.getManager().getPlayerDataManager().getPlayerData( p.getUniqueId().toString() ));
				if (!canBreak) // ikke ( venn eller workmode )
				{
					FancyMessages.sendActionBar(p, ChatColor.RED
							+ "Denne blokken er eid av " + pd.getName());
					e.setCancelled(true);
				}
			}
		}
	}
}
