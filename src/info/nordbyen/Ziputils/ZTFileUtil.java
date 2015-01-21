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

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The Class ZTFileUtil.
 */
public final class ZTFileUtil {

    /**
     * Inner list files.
     * 
     * @param dir the dir
     * @param rtrn the rtrn
     * @param filter the filter
     */
    private static void innerListFiles(File dir, Collection<File> rtrn, FileFilter filter) {
        String[] filenames = dir.list();
        if (filenames != null) {
            for (int i = 0; i < filenames.length; i++) {
                File file = new File(dir, filenames[i]);
                if (file.isDirectory()) {
                    innerListFiles(file, rtrn, filter);
                } else {
                    if (filter != null && filter.accept(file)) {
                        rtrn.add(file);
                    }
                }
            }
        }
    }

    /**
     * List files.
     * 
     * @param dir the dir
     * @return the collection
     */
    public static Collection<File> listFiles(File dir) {
        return listFiles(dir, null);
    }

    /**
     * List files.
     * 
     * @param dir the dir
     * @param filter the filter
     * @return the collection
     */
    public static Collection<File> listFiles(File dir, FileFilter filter) {
        Collection<File> rtrn = new ArrayList<File>();
        if (dir.isFile()) {
            return rtrn;
        }
        if (filter == null) {
            // Set default filter to accept any file
            filter = new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return true;
                }
            };
        }
        innerListFiles(dir, rtrn, filter);
        return rtrn;
    }

    /**
     * Instantiates a new ZT file util.
     */
    private ZTFileUtil() {
    }
}
