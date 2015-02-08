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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Class ByteSource.
 */
public class ByteSource implements ZipEntrySource {

	/** The path. */
	private final String path;
	/** The bytes. */
	private final byte[] bytes;
	/** The time. */
	private final long time;

	/**
	 * Instantiates a new byte source.
	 * 
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 */
	public ByteSource(final String path, final byte[] bytes) {
		this(path, bytes, System.currentTimeMillis());
	}

	/**
	 * Instantiates a new byte source.
	 * 
	 * @param path
	 *            the path
	 * @param bytes
	 *            the bytes
	 * @param time
	 *            the time
	 */
	public ByteSource(final String path, final byte[] bytes, final long time) {
		this.path = path;
		this.bytes = bytes.clone();
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getEntry()
	 */
	@Override
	public ZipEntry getEntry() {
		final ZipEntry entry = new ZipEntry(path);
		if (bytes != null) {
			entry.setSize(bytes.length);
		}
		entry.setTime(time);
		return entry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntrySource#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		if (bytes == null)
			return null;
		else
			return new ByteArrayInputStream(bytes);
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
		return "ByteSource[" + path + "]";
	}
}
