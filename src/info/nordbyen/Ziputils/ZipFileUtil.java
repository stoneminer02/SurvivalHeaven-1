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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipFileUtil.
 */
class ZipFileUtil {

	/** The Constant MISSING_METHOD_PLEASE_UPGRADE. */
	private static final String MISSING_METHOD_PLEASE_UPGRADE = "Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).";
	/** The Constant CONSTRUCTOR_MESSAGE_FOR_ZIPFILE. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_ZIPFILE = "Using constructor ZipFile(File, Charset) has failed: ";
	/** The Constant CONSTRUCTOR_MESSAGE_FOR_OUTPUT. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_OUTPUT = "Using constructor ZipOutputStream(OutputStream, Charset) has failed: ";
	/** The Constant CONSTRUCTOR_MESSAGE_FOR_INPUT. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_INPUT = "Using constructor ZipInputStream(InputStream, Charset) has failed: ";

	/**
	 * Creates the zip input stream.
	 * 
	 * @param inStream
	 *            the in stream
	 * @param charset
	 *            the charset
	 * @return the zip input stream
	 */
	static ZipInputStream createZipInputStream(final InputStream inStream,
			final Charset charset) {
		if (charset == null)
			return new ZipInputStream(inStream);
		try {
			final Constructor<ZipInputStream> constructor = ZipInputStream.class
					.getConstructor(new Class[] { InputStream.class,
							Charset.class });
			return constructor.newInstance(new Object[] { inStream, charset });
		} catch (final NoSuchMethodException e) {
			throw new IllegalStateException(MISSING_METHOD_PLEASE_UPGRADE, e);
		} catch (final InstantiationException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_INPUT
					+ e.getMessage(), e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_INPUT
					+ e.getMessage(), e);
		} catch (final IllegalArgumentException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_INPUT
					+ e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_INPUT
					+ e.getMessage(), e);
		}
	}

	/**
	 * Creates the zip output stream.
	 * 
	 * @param outStream
	 *            the out stream
	 * @param charset
	 *            the charset
	 * @return the zip output stream
	 */
	static ZipOutputStream createZipOutputStream(
			final BufferedOutputStream outStream, final Charset charset) {
		if (charset == null)
			return new ZipOutputStream(outStream);
		try {
			final Constructor<ZipOutputStream> constructor = ZipOutputStream.class
					.getConstructor(new Class[] { OutputStream.class,
							Charset.class });
			return constructor.newInstance(new Object[] { outStream, charset });
		} catch (final NoSuchMethodException e) {
			throw new IllegalStateException(MISSING_METHOD_PLEASE_UPGRADE, e);
		} catch (final InstantiationException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_OUTPUT
					+ e.getMessage(), e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_OUTPUT
					+ e.getMessage(), e);
		} catch (final IllegalArgumentException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_OUTPUT
					+ e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_OUTPUT
					+ e.getMessage(), e);
		}
	}

	/**
	 * Gets the zip file.
	 * 
	 * @param src
	 *            the src
	 * @param charset
	 *            the charset
	 * @return the zip file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static ZipFile getZipFile(final File src, final Charset charset)
			throws IOException {
		if (charset == null)
			return new ZipFile(src);
		try {
			final Constructor<ZipFile> constructor = ZipFile.class
					.getConstructor(new Class[] { File.class, Charset.class });
			return constructor.newInstance(new Object[] { src, charset });
		} catch (final NoSuchMethodException e) {
			throw new IllegalStateException(MISSING_METHOD_PLEASE_UPGRADE, e);
		} catch (final InstantiationException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_ZIPFILE
					+ e.getMessage(), e);
		} catch (final IllegalAccessException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_ZIPFILE
					+ e.getMessage(), e);
		} catch (final IllegalArgumentException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_ZIPFILE
					+ e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			throw new IllegalStateException(CONSTRUCTOR_MESSAGE_FOR_ZIPFILE
					+ e.getMessage(), e);
		}
	}

	/**
	 * Checks if is charset supported.
	 * 
	 * @return true, if is charset supported
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static boolean isCharsetSupported() throws IOException {
		try {
			ZipFile.class.getConstructor(new Class[] { File.class,
					Charset.class });
			return true;
		} catch (final NoSuchMethodException e) {
			return false;
		}
	}

	// Private constructor for the utility class
	/**
	 * Instantiates a new zip file util.
	 */
	private ZipFileUtil() {
	}
}
