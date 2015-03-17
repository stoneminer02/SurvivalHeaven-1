/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.commons;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * The Class IOUtils.
 */
public class IOUtils {

	/**
	 * Close quietly.
	 *
	 * @param input
	 *            the input
	 */
	public static void closeQuietly(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	/**
	 * Close quietly.
	 *
	 * @param output
	 *            the output
	 */
	public static void closeQuietly(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	// content equals
	// -----------------------------------------------------------------------
	/**
	 * Content equals.
	 *
	 * @param input1
	 *            the input1
	 * @param input2
	 *            the input2
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean contentEquals(InputStream input1, InputStream input2)
			throws IOException {
		if (!(input1 instanceof BufferedInputStream)) {
			input1 = new BufferedInputStream(input1);
		}
		if (!(input2 instanceof BufferedInputStream)) {
			input2 = new BufferedInputStream(input2);
		}
		int ch = input1.read();
		while (-1 != ch) {
			int ch2 = input2.read();
			if (ch != ch2) {
				return false;
			}
			ch = input1.read();
		}
		int ch2 = input2.read();
		return (ch2 == -1);
	}

	// copy from InputStream
	// -----------------------------------------------------------------------
	/**
	 * Copy.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * Copy.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @param encoding
	 *            the encoding
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}

	// copy from Reader
	// -----------------------------------------------------------------------
	/**
	 * Copy.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy large.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Copy large.
	 *
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @return the long
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static long copyLarge(Reader input, Writer output)
			throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	// read toByteArray
	// -----------------------------------------------------------------------
	/**
	 * To byte array.
	 *
	 * @param input
	 *            the input
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	/**
	 * To string.
	 *
	 * @param input
	 *            the input
	 * @param encoding
	 *            the encoding
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	// NOTE: This class is focused on InputStream, OutputStream, Reader and
	// Writer. Each method should take at least one of these as a parameter,
	// or return one of them.
	/** The Constant DIR_SEPARATOR_UNIX. */
	public static final char DIR_SEPARATOR_UNIX = '/';

	/** The Constant DIR_SEPARATOR_WINDOWS. */
	public static final char DIR_SEPARATOR_WINDOWS = '\\';

	/** The Constant DIR_SEPARATOR. */
	public static final char DIR_SEPARATOR = File.separatorChar;

	/** The Constant LINE_SEPARATOR_UNIX. */
	public static final String LINE_SEPARATOR_UNIX = "\n";

	/** The Constant LINE_SEPARATOR_WINDOWS. */
	public static final String LINE_SEPARATOR_WINDOWS = "\r\n";

	/** The Constant LINE_SEPARATOR. */
	public static final String LINE_SEPARATOR;

	static {
		// avoid security issues
		StringWriter buf = new StringWriter(4);
		PrintWriter out = new PrintWriter(buf);
		out.println();
		LINE_SEPARATOR = buf.toString();
	}

	/** The Constant DEFAULT_BUFFER_SIZE. */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * Instantiates a new IO utils.
	 */
	public IOUtils() {
		super();
	}
}
