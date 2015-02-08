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

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The Class PrepareRecipeEvent.
 */
public class PrepareRecipeEvent extends Event {

    /** The result. */
    private ItemStack result;
    /** The inv. */
    private final ItemStack[] inv;
    /** The name. */
    private final String name;
    /** The handlers. */
    private static HandlerList handlers = new HandlerList();

    /**
     * Gets the handler list.
     * 
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Instantiates a new prepare recipe event.
     * 
     * @param inventory the inventory
     * @param result the result
     * @param name the name
     */
    public PrepareRecipeEvent(final ItemStack[] inventory, final ItemStack result, final String name) {
        super();
        inv = inventory;
        setResult(result);
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.event.Event#getHandlers()
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the inventory.
     * 
     * @return the inventory
     */
    public ItemStack[] getInventory() {
        return inv;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the result.
     * 
     * @return the result
     */
    public ItemStack getResult() {
        return result;
    }

    /**
     * Sets the result.
     * 
     * @param result the new result
     */
    public void setResult(final ItemStack result) {
        this.result = result;
    }
}
