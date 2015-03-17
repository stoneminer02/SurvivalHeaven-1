/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util;

import info.nordbyen.survivalheaven.subplugins.bitly.util.data.Pair;

import org.w3c.dom.Document;

/**
 * The Interface BitlyMethod.
 *
 * @param <A>
 *            the generic type
 */
public abstract interface BitlyMethod<A> {

	/**
	 * Apply.
	 *
	 * @param paramProvider
	 *            the param provider
	 * @param paramDocument
	 *            the param document
	 * @return the a
	 */
	public abstract A apply(Bitly.Provider paramProvider, Document paramDocument);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public abstract Iterable<Pair<String, String>> getParameters();
}
