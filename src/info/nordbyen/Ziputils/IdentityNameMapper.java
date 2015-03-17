/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.Ziputils;

/**
 * The Class IdentityNameMapper.
 */
final class IdentityNameMapper implements NameMapper {

	/** The Constant INSTANCE. */
	public static final NameMapper INSTANCE = new IdentityNameMapper();

	/**
	 * Instantiates a new identity name mapper.
	 */
	private IdentityNameMapper() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.Ziputils.NameMapper#map(java.lang.String)
	 */
	@Override
	public String map(final String name) {
		return name;
	}
}
