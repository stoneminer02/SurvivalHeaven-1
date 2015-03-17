/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.ban;

import java.sql.Date;

/**
 * The Class Ban.
 */
public class Ban {

	/** The uuid. */
	private final String uuid;
	
	/** The by_uuid. */
	private final String by_uuid;
	
	/** The reason. */
	private final String reason;
	
	/** The from. */
	private final Date from;
	
	/** The to. */
	private final Date to;

	/**
	 * Instantiates a new ban.
	 *
	 * @param uuid
	 *            the uuid
	 * @param by_uuid
	 *            the by_uuid
	 * @param reason
	 *            the reason
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	public Ban( String uuid, String by_uuid, String reason, Date from, Date to ) {
		this.uuid = uuid;
		this.by_uuid = by_uuid;
		this.reason = reason;
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public String getUUID() {
		return uuid;
	}
	
	/**
	 * Gets the giver uuid.
	 *
	 * @return the giver uuid
	 */
	public String getGiverUUID() {
		return by_uuid;
	}
	
	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public Date getFrom() {
		return from;
	}
	
	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public Date getTo() {
		return to;
	}
}
