/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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

	/** The Constant MISSING_METHOD_PLEASE_UPGRADE. */
	private static final String MISSING_METHOD_PLEASE_UPGRADE = "Your JRE doesn't support the ZipFile Charset constructor. Please upgrade JRE to 1.7 use this feature. Tried constructor ZipFile(File, Charset).";

	/** The Constant CONSTRUCTOR_MESSAGE_FOR_ZIPFILE. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_ZIPFILE = "Using constructor ZipFile(File, Charset) has failed: ";

	/** The Constant CONSTRUCTOR_MESSAGE_FOR_OUTPUT. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_OUTPUT = "Using constructor ZipOutputStream(OutputStream, Charset) has failed: ";

	/** The Constant CONSTRUCTOR_MESSAGE_FOR_INPUT. */
	private static final String CONSTRUCTOR_MESSAGE_FOR_INPUT = "Using constructor ZipInputStream(InputStream, Charset) has failed: ";

	// Private constructor for the utility class
	/**
	 * Instantiates a new zip file util.
	 */
	private ZipFileUtil() {
	}
}
