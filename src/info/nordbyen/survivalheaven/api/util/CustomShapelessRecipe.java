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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.CraftingManager;
import net.minecraft.server.v1_8_R1.IRecipe;
import net.minecraft.server.v1_8_R1.InventoryCrafting;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.ShapelessRecipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomShapelessRecipe.
 */
public class CustomShapelessRecipe extends ShapelessRecipes implements IRecipe {

    /**
     * Adds the recipe.
     * 
     * @param name the name
     * @param item1 the item1
     * @param args the args
     * @return the custom shapeless recipe
     */
    @SuppressWarnings("unchecked")
    public static CustomShapelessRecipe addRecipe(final String name, final org.bukkit.inventory.ItemStack item1, final Object... args) {
        ItemStack item = null;
        item = CraftItemStack.asNMSCopy(item1);
        final ArrayList<ItemStack> var3 = new ArrayList<ItemStack>();
        final Object[] var4 = args;
        final int var5 = args.length;
        for (int var6 = 0; var6 < var5; ++var6) {
            final Object var7 = var4[var6];
            if (var7 instanceof org.bukkit.inventory.ItemStack) {
                var3.add(CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack) var7).cloneItemStack());
            } else if (var7 instanceof Material) {
                var3.add(CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack((Material) var7)).cloneItemStack());
            } else
                throw new RuntimeException("Invalid shapeless recipy!");
        }
        final CustomShapelessRecipe result = new CustomShapelessRecipe(name, item, var3);
        CraftingManager.getInstance().recipes.add(result);
        CraftingManager.getInstance().sort();
        return result;
    }

    /** The result. */
    private final ItemStack result;
    /** The name. */
    private final String name;

    /**
     * Instantiates a new custom shapeless recipe.
     * 
     * @param name the name
     * @param itemstack the itemstack
     * @param list the list
     */
    public CustomShapelessRecipe(final String name, final ItemStack itemstack, final List<ItemStack> list) {
        super(itemstack, list);
        result = itemstack;
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.minecraft.server.v1_8_R1.ShapelessRecipes#a(net.minecraft.server.
     * v1_8_R1.InventoryCrafting)
     */
    @Override
    public ItemStack a(final InventoryCrafting arg0) {
        ItemStack item = result.cloneItemStack();
        final org.bukkit.inventory.ItemStack[] inventory = new org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack[arg0.getSize()];
        for (int i = 0; i < arg0.getContents().length; i++) {
            inventory[i] = CraftItemStack.asBukkitCopy(arg0.getContents()[i]);
        }
        if (result.getTag() != null) {
            item.setTag((NBTTagCompound) result.getTag().clone());
        }
        final PrepareRecipeEvent event = new PrepareRecipeEvent(inventory, CraftItemStack.asBukkitCopy(item), name);
        Bukkit.getPluginManager().callEvent(event);
        item = CraftItemStack.asNMSCopy(event.getResult());
        return item;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
}
