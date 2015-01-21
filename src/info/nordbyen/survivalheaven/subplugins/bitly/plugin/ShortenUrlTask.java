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

import info.nordbyen.survivalheaven.subplugins.bitly.util.Bitly;
import info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyException;
import info.nordbyen.survivalheaven.subplugins.bitly.util.Url;

import java.io.IOException;
import java.net.URL;
import java.util.TimerTask;

import org.bukkit.Bukkit;

/**
 * The Class ShortenUrlTask.
 */
public class ShortenUrlTask extends TimerTask {

    /** The username. */
    private final String username;
    /** The key. */
    private final String key;
    /** The message. */
    private final String message;

    /**
     * Instantiates a new shorten url task.
     * 
     * @param username the username
     * @param key the key
     * @param message the message
     */
    public ShortenUrlTask(final String username, final String key, final String message) {
        this.username = username;
        this.key = key;
        this.message = message;
    }

    /**
     * Parses the string for url.
     * 
     * @return the string
     */
    @SuppressWarnings("unused")
    public String parseStringForUrl() {
        String finalMessage = "";
        final String[] words = this.message.split(" ");
        for (int i = 0; i < words.length; i++) {
            URL url_;
            try {
                url_ = new URL(words[i]);
            } catch (final Exception e) {
                continue;
            }
            try {
                url_.openConnection();
            } catch (final IOException e1) {
                continue;
            }
            if (true || words[i].matches("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,3})(/\\S*)?$")) {
                try {
                    final Url url = Bitly.as(this.username, this.key).call(Bitly.shorten(words[i]));
                    words[i] = url.getShortUrl();
                } catch (final BitlyException e) {
                    final String http = "http://" + words[i];
                    try {
                        final Url url = Bitly.as(this.username, this.key).call(Bitly.shorten(http));
                        words[i] = url.getShortUrl();
                    } catch (final BitlyException e2) {
                        Bukkit.getLogger().warning("Malformed url: " + words[i]);
                    }
                }
            }
        }
        for (final String string : words) {
            finalMessage += string + " ";
        }
        return finalMessage.trim();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        parseStringForUrl();
    }
}
