/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.bitly.util.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class Dom.
 */
public final class Dom {

	/**
	 * Gets the text content.
	 *
	 * @param n
	 *            the n
	 * @return the text content
	 */
	public static String getTextContent(final Node n) {
		final StringBuffer sb = new StringBuffer();
		final NodeList nl = n.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			final Node child = nl.item(i);
			if (child.getNodeType() == 3) {
				sb.append(child.getNodeValue());
			}
		}
		return sb.toString();
	}
}
