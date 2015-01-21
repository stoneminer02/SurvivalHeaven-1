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

package info.nordbyen.survivalheaven.subplugins.preliminary;

import info.nordbyen.survivalheaven.api.wand.Wand;
import info.nordbyen.survivalheaven.api.wand.WandSelection;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Class AdminWand.
 */
public class AdminWand implements Wand {

    /** The instance. */
    private static AdminWand instance = null;

    /**
     * Gets the single instance of AdminWand.
     * 
     * @return single instance of AdminWand
     */
    public static AdminWand getInstance() {
        if (instance == null) {
            instance = new AdminWand();
        }
        return instance;
    }

    /** The selections. */
    public HashMap<String, WandSelection> selections = new HashMap<String, WandSelection>();

    /**
     * Instantiates a new admin wand.
     */
    private AdminWand() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.wand.Wand#canCreate(org.bukkit.inventory
     * .ItemStack, org.bukkit.entity.Player)
     */
    @Override
    public boolean canCreate(final ItemStack itemStack, final Player player) {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.wand.Wand#getName()
     */
    @Override
    public String getName() {
        return ChatColor.GOLD + "Admin Stick";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.wand.Wand#onLeftClick(org.bukkit.inventory
     * .ItemStack, org.bukkit.entity.Player, org.bukkit.block.Block,
     * org.bukkit.block.BlockFace)
     */
    @Override
    public void onLeftClick(final ItemStack itemStack, final Player player, final Block block, final BlockFace face) {
        if (!selections.get(player.getUniqueId().toString()).getBlock2().getLocation().toString().equals(block.getLocation().toString()))
            return;
        player.sendMessage(ChatColor.GRAY + "Du klikket på " + block.toString());
        if (selections.containsKey(player.getUniqueId().toString())) {
            selections.get(player.getUniqueId().toString()).setBlock2(block);
        } else {
            selections.put(player.getUniqueId().toString(), new WandSelection(null, block));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.wand.Wand#onRightClick(org.bukkit.inventory
     * .ItemStack, org.bukkit.entity.Player, org.bukkit.block.Block,
     * org.bukkit.block.BlockFace)
     */
    @Override
    public void onRightClick(final ItemStack itemStack, final Player player, final Block block, final BlockFace face) {
        if (!selections.get(player.getUniqueId().toString()).getBlock1().getLocation().toString().equals(block.getLocation().toString()))
            return;
        player.sendMessage(ChatColor.GRAY + "Du klikket på " + block.toString());
        if (selections.containsKey(player.getUniqueId().toString())) {
            selections.get(player.getUniqueId().toString()).setBlock1(block);
        } else {
            selections.put(player.getUniqueId().toString(), new WandSelection(block, null));
        }
    }
}
