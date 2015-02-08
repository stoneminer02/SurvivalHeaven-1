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

package info.nordbyen.survivalheaven.subplugins.bitly.util;
// TODO: Auto-generated Javadoc
/**
 * The Class UrlClicks.
 */
public class UrlClicks {

    /** The user clicks. */
    private final long userClicks;
    /** The global clicks. */
    private final long globalClicks;
    /** The url. */
    private final Url url;

    /**
     * Instantiates a new url clicks.
     * 
     * @param url the url
     * @param userClicks the user clicks
     * @param globalClicks the global clicks
     */
    UrlClicks(final Url url, final long userClicks, final long globalClicks) {
        this.url = url;
        this.userClicks = userClicks;
        this.globalClicks = globalClicks;
    }

    /**
     * Gets the global clicks.
     * 
     * @return the global clicks
     */
    public long getGlobalClicks() {
        return this.globalClicks;
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public Url getUrl() {
        return this.url;
    }

    /**
     * Gets the user clicks.
     * 
     * @return the user clicks
     */
    public long getUserClicks() {
        return this.userClicks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UrlClicks [globalClicks=" + this.globalClicks + ", userClicks=" + this.userClicks + ", url=" + this.url + "]";
    }
}
