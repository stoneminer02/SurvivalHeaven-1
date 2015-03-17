/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.blockdata;

/**
 * The Enum BlockPlacedType.
 */
public enum BlockPlacedType {
	
	/** The survival. */
	SURVIVAL("SURVIVAL"),
	
	/** The creative. */
	CREATIVE("CREATIVE"),
	
	/** The worldedit. */
	WORLDEDIT("WORLDEDIT");

	/** The name. */
	public final String name;

	/**
	 * Instantiates a new block placed type.
	 *
	 * @param name
	 *            the name
	 */
	private BlockPlacedType(final String name) {
		this.name = name;
	}
}
