/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.person;

import javax.annotation.Nullable;

/**
 * The Interface Nameable.
 */
public interface Nameable {

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName();

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName();

	/**
	 * Gets the middle names.
	 *
	 * @return the middle names
	 */
	@Nullable
	public String[] getMiddleNames();
}
