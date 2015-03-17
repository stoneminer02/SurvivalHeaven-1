/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

import info.nordbyen.Ziputils.FileSource;
import info.nordbyen.Ziputils.commons.FileUtils;
import info.nordbyen.Ziputils.commons.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class FileZipEntryTransformer.
 */
public abstract class FileZipEntryTransformer implements ZipEntryTransformer {

	/**
	 * Copy.
	 *
	 * @param in
	 *            the in
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void copy(InputStream in, File file) throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		try {
			IOUtils.copy(in, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
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
		File inFile = null;
		File outFile = null;
		try {
			inFile = File.createTempFile("zip", null);
			outFile = File.createTempFile("zip", null);
			copy(in, inFile);
			transform(zipEntry, inFile, outFile);
			FileSource source = new FileSource(zipEntry.getName(), outFile);
			ZipEntrySourceZipEntryTransformer.addEntry(source, out);
		} finally {
			FileUtils.deleteQuietly(inFile);
			FileUtils.deleteQuietly(outFile);
		}
	}

	/**
	 * Transform.
	 *
	 * @param zipEntry
	 *            the zip entry
	 * @param in
	 *            the in
	 * @param out
	 *            the out
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected abstract void transform(ZipEntry zipEntry, File in, File out)
			throws IOException;
}
