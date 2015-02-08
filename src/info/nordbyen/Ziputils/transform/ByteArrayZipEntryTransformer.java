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

package info.nordbyen.Ziputils.transform;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import info.nordbyen.Ziputils.ByteSource;
import info.nordbyen.Ziputils.commons.IOUtils;

/**
 * The Class ByteArrayZipEntryTransformer.
 */
public abstract class ByteArrayZipEntryTransformer implements
		ZipEntryTransformer {

	/**
	 * Preserve timestamps.
	 * 
	 * @return true, if successful
	 */
	protected boolean preserveTimestamps() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.Ziputils.transform.ZipEntryTransformer#transform(java.io
	 * .InputStream, java.util.zip.ZipEntry, java.util.zip.ZipOutputStream)
	 */
	@Override
	public void transform(InputStream in, ZipEntry zipEntry, ZipOutputStream out)
			throws IOException {
		byte[] bytes = IOUtils.toByteArray(in);
		bytes = transform(zipEntry, bytes);
		ByteSource source;
		if (preserveTimestamps()) {
			source = new ByteSource(zipEntry.getName(), bytes,
					zipEntry.getTime());
		} else {
			source = new ByteSource(zipEntry.getName(), bytes);
		}
		ZipEntrySourceZipEntryTransformer.addEntry(source, out);
	}

	/**
	 * Transform.
	 * 
	 * @param zipEntry
	 *            the zip entry
	 * @param input
	 *            the input
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected abstract byte[] transform(ZipEntry zipEntry, byte[] input)
			throws IOException;
}
