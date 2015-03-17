/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils.transform;

/**
 * The Class ZipEntryTransformerEntry.
 */
public class ZipEntryTransformerEntry {

	/** The path. */
	private final String path;
	
	/** The transformer. */
	private final ZipEntryTransformer transformer;

	/**
	 * Instantiates a new zip entry transformer entry.
	 *
	 * @param path
	 *            the path
	 * @param transformer
	 *            the transformer
	 */
	public ZipEntryTransformerEntry(String path, ZipEntryTransformer transformer) {
		this.path = path;
		this.transformer = transformer;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the transformer.
	 *
	 * @return the transformer
	 */
	public ZipEntryTransformer getTransformer() {
		return transformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return path + "=" + transformer;
	}
}
