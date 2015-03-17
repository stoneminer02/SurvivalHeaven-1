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

import java.util.Arrays;

/**
 * The Class MethodBase.
 *
 * @param <A>
 *            the generic type
 */
public abstract class MethodBase<A> implements BitlyMethod<A> {

	/** The name. */
	private final String name;
	
	/** The parameters. */
	private final Iterable<Pair<String, String>> parameters;

	/**
	 * Instantiates a new method base.
	 *
	 * @param name
	 *            the name
	 * @param parameters
	 *            the parameters
	 */
	public MethodBase(final String name,
			final Iterable<Pair<String, String>> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	/**
	 * Instantiates a new method base.
	 *
	 * @param name
	 *            the name
	 * @param parameters
	 *            the parameters
	 */
	public MethodBase(final String name, final Pair<String, String>[] parameters) {
		this(name, Arrays.asList(parameters));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyMethod#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.bitly.util.BitlyMethod#getParameters
	 * ()
	 */
	@Override
	public Iterable<Pair<String, String>> getParameters() {
		return this.parameters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [name=" + this.name
				+ ", parameters=" + this.parameters + "]";
	}
}
