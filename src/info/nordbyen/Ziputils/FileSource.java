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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

// TODO: Auto-generated Javadoc
/**
 * The Class FileSource.
 */
public class FileSource implements ZipEntrySource {

    /**
     * Pair.
     * 
     * @param files the files
     * @param names the names
     * @return the file source[]
     */
    public static FileSource[] pair(File[] files, String[] names) {
        if (files.length > names.length) {
            throw new IllegalArgumentException("names array must contain " + "at least the same amount of items as files array or more");
        }
        FileSource[] result = new FileSource[files.length];
        for (int i = 0; i < files.length; i++) {
            result[i] = new FileSource(names[i], files[i]);
        }
        return result;
    }

    /** The path. */
    private final String path;
    /** The file. */
    private final File file;

    /**
     * Instantiates a new file source.
     * 
     * @param path the path
     * @param file the file
     */
    public FileSource(String path, File file) {
        this.path = path;
        this.file = file;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.Ziputils.ZipEntrySource#getEntry()
     */
    @Override
    public ZipEntry getEntry() {
        ZipEntry entry = new ZipEntry(path);
        if (!file.isDirectory()) {
            entry.setSize(file.length());
        }
        entry.setTime(file.lastModified());
        return entry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.Ziputils.ZipEntrySource#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (file.isDirectory()) {
            return null;
        } else {
            return new BufferedInputStream(new FileInputStream(file));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see info.nordbyen.Ziputils.ZipEntrySource#getPath()
     */
    @Override
    public String getPath() {
        return path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FileSource[" + path + ", " + file + "]";
    }
}
