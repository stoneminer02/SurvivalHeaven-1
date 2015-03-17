/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.remote;

/**
 * The Class User.
 */
public class User {

	/** The username. */
	private final String username;
	
	/** The password. */
	private final String password;

	/**
	 * Instantiates a new user.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	public User(final String username, final String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}
}
