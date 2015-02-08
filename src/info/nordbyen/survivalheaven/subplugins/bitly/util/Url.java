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
 * The Class Url.
 */
public class Url {

    /** The short base. */
    private String shortBase;
    /** The global hash. */
    private String globalHash;
    /** The user hash. */
    private String userHash;
    /** The short url. */
    private String shortUrl;
    /** The long url. */
    private String longUrl;

    /**
     * Instantiates a new url.
     */
    Url() {
    }

    /**
     * Instantiates a new url.
     * 
     * @param shortBase the short base
     * @param globalHash the global hash
     * @param userHash the user hash
     * @param shortUrl the short url
     * @param longUrl the long url
     */
    Url(final String shortBase, final String globalHash, final String userHash, final String shortUrl, final String longUrl) {
        this.shortBase = shortBase;
        this.globalHash = globalHash;
        this.userHash = userHash;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        if (this.shortUrl.length() == 0) {
            this.shortUrl = (shortBase + userHash);
        }
    }

    /**
     * Gets the global hash.
     * 
     * @return the global hash
     */
    public String getGlobalHash() {
        return this.globalHash;
    }

    /**
     * Gets the long url.
     * 
     * @return the long url
     */
    public String getLongUrl() {
        return this.longUrl;
    }

    /**
     * Gets the short url.
     * 
     * @return the short url
     */
    public String getShortUrl() {
        return this.shortUrl;
    }

    /**
     * Gets the user hash.
     * 
     * @return the user hash
     */
    public String getUserHash() {
        return this.userHash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Url [shortBase=" + this.shortBase + ", globalHash=" + this.globalHash + ", longUrl=" + this.longUrl + ", shortUrl=" + this.shortUrl + ", userHash=" + this.userHash + "]";
    }
}
