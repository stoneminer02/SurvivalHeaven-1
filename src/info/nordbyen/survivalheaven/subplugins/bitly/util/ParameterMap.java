/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Class ParameterMap.
 */
class ParameterMap extends AbstractCollection<Map.Entry<String, List<String>>> {

	/** The parameters. */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Map<String, List<String>> parameters = new HashMap();

	/**
	 * Adds the.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void add(final String name, final String value) {
		List values = this.parameters.get(name);
		if (values == null) {
			values = new ArrayList();
		}
		values.add(value);
		this.parameters.put(name, values);
	}

	/**
	 * Gets the.
	 *
	 * @param name
	 *            the name
	 * @return the list
	 */
	public List<String> get(final String name) {
		return this.parameters.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<Map.Entry<String, List<String>>> iterator() {
		return this.parameters.entrySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return this.parameters.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		return "ParameterMap [parameters=" + this.parameters + "]";
	}
}
