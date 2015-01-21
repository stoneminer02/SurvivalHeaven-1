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
/**
 * The Class UrlInfo.
 */
public class UrlInfo {

    /** The url. */
    private final Url url;
    /** The created by. */
    private final String createdBy;
    /** The title. */
    private final String title;

    /**
     * Instantiates a new url info.
     * 
     * @param url the url
     * @param createdBy the created by
     * @param title the title
     */
    UrlInfo(final Url url, final String createdBy, final String title) {
        this.url = url;
        this.createdBy = createdBy;
        this.title = title;
    }

    /**
     * Gets the created by.
     * 
     * @return the created by
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public Url getUrl() {
        return this.url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Info [createdBy=" + this.createdBy + ", title=" + this.title + ", url=" + this.url + "]";
    }
}
