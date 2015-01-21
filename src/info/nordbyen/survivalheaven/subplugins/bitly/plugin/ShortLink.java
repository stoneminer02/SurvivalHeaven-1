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

package info.nordbyen.survivalheaven.subplugins.bitly.plugin;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * The Class ShortLink.
 */
public class ShortLink extends SubPlugin implements Listener {

    /** The log. */
    static Logger log = Bukkit.getLogger();
    /** The instance. */
    static ShortLink instance;
    /** The key. */
    private String key;
    /** The username. */
    private String username;

    /**
     * Instantiates a new short link.
     * 
     * @param name the name
     */
    public ShortLink(final String name) {
        super(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
     */
    @Override
    public void disable() {
        Bukkit.getScheduler().cancelTasks(getPlugin());
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
     */
    @Override
    public void enable() {
        instance = this;
        key = "R_402ff904a22447148961043124fab70c";
        username = "l0lkj";
        Bukkit.getPluginManager().registerEvents(this, getPlugin());
    }

    /**
     * On chat.
     * 
     * @param event the event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;
        if (event.getMessage() == null)
            return;
        final ShortenUrlTask sut = new ShortenUrlTask(username, key, event.getMessage());
        event.setMessage(sut.parseStringForUrl());
        return;
    }
}
