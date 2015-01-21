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

import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipEntryUtil.
 */
class ZipEntryUtil {

    /**
     * Adds the entry.
     * 
     * @param zipEntry the zip entry
     * @param in the in
     * @param out the out
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static void addEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out) throws IOException {
        out.putNextEntry(zipEntry);
        if (in != null) {
            IOUtils.copy(in, out);
        }
        out.closeEntry();
    }

    /**
     * Copy.
     * 
     * @param original the original
     * @return the zip entry
     */
    static ZipEntry copy(final ZipEntry original) {
        return copy(original, null);
    }

    /**
     * Copy.
     * 
     * @param original the original
     * @param newName the new name
     * @return the zip entry
     */
    static ZipEntry copy(final ZipEntry original, final String newName) {
        final ZipEntry copy = new ZipEntry(newName == null ? original.getName() : newName);
        if (original.getCrc() != -1) {
            copy.setCrc(original.getCrc());
        }
        if (original.getMethod() != -1) {
            copy.setMethod(original.getMethod());
        }
        if (original.getSize() >= 0) {
            copy.setSize(original.getSize());
        }
        if (original.getExtra() != null) {
            copy.setExtra(original.getExtra());
        }
        copy.setComment(original.getComment());
        copy.setTime(original.getTime());
        return copy;
    }

    /**
     * Copy entry.
     * 
     * @param zipEntry the zip entry
     * @param in the in
     * @param out the out
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static void copyEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out) throws IOException {
        copyEntry(zipEntry, in, out, true);
    }

    /**
     * Copy entry.
     * 
     * @param zipEntry the zip entry
     * @param in the in
     * @param out the out
     * @param preserveTimestamps the preserve timestamps
     * @throws IOException Signals that an I/O exception has occurred.
     */
    static void copyEntry(final ZipEntry zipEntry, final InputStream in, final ZipOutputStream out, final boolean preserveTimestamps) throws IOException {
        final ZipEntry copy = copy(zipEntry);
        copy.setTime(preserveTimestamps ? zipEntry.getTime() : System.currentTimeMillis());
        addEntry(copy, new BufferedInputStream(in), out);
    }

    /**
     * Instantiates a new zip entry util.
     */
    private ZipEntryUtil() {
    }
}
