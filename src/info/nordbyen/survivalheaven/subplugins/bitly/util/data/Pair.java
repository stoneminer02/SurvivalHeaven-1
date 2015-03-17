/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util.data;

/**
 * The Class Pair.
 *
 * @param <A>
 *            the generic type
 * @param <B>
 *            the generic type
 */
public final class Pair<A, B> {

	/**
	 * P.
	 *
	 * @param <A>
	 *            the generic type
	 * @param <B>
	 *            the generic type
	 * @param one
	 *            the one
	 * @param two
	 *            the two
	 * @return the pair
	 */
	public static <A, B> Pair<A, B> p(final A one, final B two) {
		return new Pair<A, B>(one, two);
	}

	/** The one. */
	private final A one;
	
	/** The two. */
	private final B two;

	/**
	 * Instantiates a new pair.
	 *
	 * @param one
	 *            the one
	 * @param two
	 *            the two
	 */
	private Pair(final A one, final B two) {
		this.one = one;
		this.two = two;
	}

	/**
	 * Gets the one.
	 *
	 * @return the one
	 */
	public A getOne() {
		return this.one;
	}

	/**
	 * Gets the two.
	 *
	 * @return the two
	 */
	public B getTwo() {
		return this.two;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair [one=" + this.one + ", two=" + this.two + "]";
	}
}
