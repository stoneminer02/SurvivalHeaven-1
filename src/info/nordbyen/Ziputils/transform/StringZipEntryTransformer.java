/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

import info.nordbyen.Ziputils.ByteSource;
import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class StringZipEntryTransformer.
 */
public abstract class StringZipEntryTransformer implements ZipEntryTransformer {

	/** The encoding. */
	private final String encoding;

	/**
	 * Instantiates a new string zip entry transformer.
	 */
	public StringZipEntryTransformer() {
		this(null);
	}

	/**
	 * Instantiates a new string zip entry transformer.
	 *
	 * @param encoding
	 *            the encoding
	 */
	public StringZipEntryTransformer(String encoding) {
		this.encoding = encoding;
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
		String data = IOUtils.toString(in, encoding);
		data = transform(zipEntry, data);
		byte[] bytes = encoding == null ? data.getBytes() : data
				.getBytes(encoding);
		ByteSource source = new ByteSource(zipEntry.getName(), bytes);
		ZipEntrySourceZipEntryTransformer.addEntry(source, out);
	}

	/**
	 * Transform.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param input
	 *            the input
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected abstract String transform(ZipEntry zipEntry, String input)
			throws IOException;
}
