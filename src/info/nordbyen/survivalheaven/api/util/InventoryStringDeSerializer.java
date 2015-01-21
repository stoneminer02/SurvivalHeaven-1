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

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The Class InventoryStringDeSerializer.
 */
public class InventoryStringDeSerializer {

    /**
     * Inventory to string.
     * 
     * @param invInventory the inv inventory
     * @return the string
     */
    @SuppressWarnings("deprecation")
    public static String InventoryToString(final Inventory invInventory) {
        String serialization = invInventory.getSize() + ";";
        for (int i = 0; i < invInventory.getSize(); i++) {
            final ItemStack is = invInventory.getItem(i);
            if (is != null) {
                String serializedItemStack = new String();
                final String isType = String.valueOf(is.getType().getId());
                serializedItemStack += "t@" + isType;
                if (is.getDurability() != 0) {
                    final String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack += ":d@" + isDurability;
                }
                if (is.getAmount() != 1) {
                    final String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack += ":a@" + isAmount;
                }
                final Map<Enchantment, Integer> isEnch = is.getEnchantments();
                if (isEnch.size() > 0) {
                    for (final Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                        serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                    }
                }
                serialization += i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }

    /**
     * String to inventory.
     * 
     * @param invString the inv string
     * @param title the title
     * @return the inventory
     */
    @SuppressWarnings("deprecation")
    public static Inventory StringToInventory(final String invString, final String title) {
        final String[] serializedBlocks = invString.split(";");
        final String invInfo = serializedBlocks[0];
        final Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo), title);
        for (int i = 1; i < serializedBlocks.length; i++) {
            final String[] serializedBlock = serializedBlocks[i].split("#");
            final int stackPosition = Integer.valueOf(serializedBlock[0]);
            if (stackPosition >= deserializedInventory.getSize()) {
                continue;
            }
            ItemStack is = null;
            Boolean createdItemStack = false;
            final String[] serializedItemStack = serializedBlock[1].split(":");
            for (final String itemInfo : serializedItemStack) {
                final String[] itemAttribute = itemInfo.split("@");
                if (itemAttribute[0].equals("t")) {
                    is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
                    createdItemStack = true;
                } else if (itemAttribute[0].equals("d") && createdItemStack) {
                    is.setDurability(Short.valueOf(itemAttribute[1]));
                } else if (itemAttribute[0].equals("a") && createdItemStack) {
                    is.setAmount(Integer.valueOf(itemAttribute[1]));
                } else if (itemAttribute[0].equals("e") && createdItemStack) {
                    is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
                }
            }
            deserializedInventory.setItem(stackPosition, is);
        }
        return deserializedInventory;
    }
}
