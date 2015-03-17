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
 * The Interface Sexable.
 */
public interface Sexable {

	/**
	 * The Enum Sex.
	 */
	public enum Sex {
		
		/** The male. */
		MALE,
		
		/** The female. */
		FEMALE,
		
		/** The unknown. */
		UNKNOWN;
	}

	/**
	 * Gets the sex.
	 *
	 * @return the sex
	 */
	public Sex getSex();

	/**
	 * Sets the sex.
	 *
	 * @param sex
	 *            the new sex
	 */
	public void setSex(Sex sex);
}
