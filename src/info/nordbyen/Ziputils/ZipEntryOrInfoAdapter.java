/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
 * The Class ZipEntryOrInfoAdapter.
 */
class ZipEntryOrInfoAdapter implements ZipEntryCallback, ZipInfoCallback {

	/** The entry callback. */
	private final ZipEntryCallback entryCallback;
	
	/** The info callback. */
	private final ZipInfoCallback infoCallback;

	/**
	 * Instantiates a new zip entry or info adapter.
	 *
	 * @param entryCallback
	 *            the entry callback
	 * @param infoCallback
	 *            the info callback
	 */
	public ZipEntryOrInfoAdapter(ZipEntryCallback entryCallback,
			ZipInfoCallback infoCallback) {
		if (entryCallback != null && infoCallback != null
				|| entryCallback == null && infoCallback == null) {
			throw new IllegalArgumentException(
					"Only one of ZipEntryCallback and ZipInfoCallback must be specified together");
		}
		this.entryCallback = entryCallback;
		this.infoCallback = infoCallback;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.ZipEntryCallback#process(java.io.InputStream,
	 * java.util.zip.ZipEntry)
	 */
	@Override
	public void process(InputStream in, ZipEntry zipEntry) throws IOException {
		if (entryCallback != null) {
			entryCallback.process(in, zipEntry);
		} else {
			process(zipEntry);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.Ziputils.ZipInfoCallback#process(java.util.zip.ZipEntry)
	 */
	@Override
	public void process(ZipEntry zipEntry) throws IOException {
		infoCallback.process(zipEntry);
	}
}
