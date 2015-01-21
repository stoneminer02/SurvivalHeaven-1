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

package info.nordbyen.Ziputils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Class ZipEntryOrInfoAdapter.
 */
class ZipEntryOrInfoAdapter implements ZipEntryCallback, ZipInfoCallback {

    /** The entry callback. */
    private final ZipEntryCallback entryCallback;
    /** The info callback. */
    private final ZipInfoCallback infoCallback;

    /**
     * Instantiates a new zip entry or info adapter.
     * 
     * @param entryCallback the entry callback
     * @param infoCallback the info callback
     */
    public ZipEntryOrInfoAdapter(ZipEntryCallback entryCallback, ZipInfoCallback infoCallback) {
        if (entryCallback != null && infoCallback != null || entryCallback == null && infoCallback == null) {
            throw new IllegalArgumentException("Only one of ZipEntryCallback and ZipInfoCallback must be specified together");
        }
        this.entryCallback = entryCallback;
        this.infoCallback = infoCallback;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
     * java.util.zip.ZipEntry)
     */
    @Override
    public void process(InputStream in, ZipEntry zipEntry) throws IOException {
        if (entryCallback != null) {
            entryCallback.process(in, zipEntry);
        } else {
            process(zipEntry);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.Ziputils.ZipInfoCallback#process(java.util.zip.ZipEntry)
     */
    @Override
    public void process(ZipEntry zipEntry) throws IOException {
        infoCallback.process(zipEntry);
    }
}
