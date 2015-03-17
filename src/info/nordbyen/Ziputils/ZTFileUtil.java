/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The Class ZTFileUtil.
 */
public final class ZTFileUtil {

	/**
	 * Inner list files.
	 *
	 * @param dir
	 *            the dir
	 * @param rtrn
	 *            the rtrn
	 * @param filter
	 *            the filter
	 */
	private static void innerListFiles(File dir, Collection<File> rtrn,
			FileFilter filter) {
		String[] filenames = dir.list();
		if (filenames != null) {
			for (int i = 0; i < filenames.length; i++) {
				File file = new File(dir, filenames[i]);
				if (file.isDirectory()) {
					innerListFiles(file, rtrn, filter);
				} else {
					if (filter != null && filter.accept(file)) {
						rtrn.add(file);
					}
				}
			}
		}
	}

	/**
	 * List files.
	 *
	 * @param dir
	 *            the dir
	 * @return the collection
	 */
	public static Collection<File> listFiles(File dir) {
		return listFiles(dir, null);
	}

	/**
	 * List files.
	 *
	 * @param dir
	 *            the dir
	 * @param filter
	 *            the filter
	 * @return the collection
	 */
	public static Collection<File> listFiles(File dir, FileFilter filter) {
		Collection<File> rtrn = new ArrayList<File>();
		if (dir.isFile()) {
			return rtrn;
		}
		if (filter == null) {
			// Set default filter to accept any file
			filter = new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return true;
				}
			};
		}
		innerListFiles(dir, rtrn, filter);
		return rtrn;
	}

	/**
	 * Instantiates a new ZT file util.
	 */
	private ZTFileUtil() {
	}
}
