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
 * The Enum Directive.
 */
public enum Directive {
	
	/** The interactive. */
	INTERACTIVE(""),
	
	/** The nolog. */
	NOLOG("NOLOG");

	/**
	 * To directive.
	 *
	 * @param raw
	 *            the raw
	 * @return the directive
	 */
	public static Directive toDirective(final String raw) {
		for (final Directive d : values()) {
			if (d.toString().equalsIgnoreCase(raw))
				return d;
		}
		return null;
	}

	/** The qualifier. */
	public final String qualifier;

	/**
	 * Instantiates a new directive.
	 *
	 * @param qualifier
	 *            the qualifier
	 */
	private Directive(final String qualifier) {
		this.qualifier = qualifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.qualifier;
	}
}
