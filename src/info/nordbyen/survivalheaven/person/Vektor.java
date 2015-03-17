/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.person;

/**
 * The Class Vektor.
 */
public class Vektor {

	/** The x. */
	private final int x;
	
	/** The y. */
	private final int y;
	
	/** The z. */
	private final int z;

	/**
	 * Instantiates a new vektor.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 */
	public Vektor(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
}
