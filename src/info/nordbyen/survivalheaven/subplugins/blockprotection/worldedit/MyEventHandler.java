/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.blockprotection.worldedit;

import com.sk89q.worldedit.EditSession.Stage;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.util.eventbus.Subscribe;

/**
 * The Class MyEventHandler.
 */
public class MyEventHandler {

	/**
	 * Wrap for logging.
	 *
	 * @param event
	 *            the event
	 */
	@Subscribe
	public void wrapForLogging(final EditSessionEvent event) {
		final Actor actor = event.getActor();
		if (event.getStage() != Stage.BEFORE_CHANGE)
			if ((actor != null) && actor.isPlayer()) {
				event.setExtent(new MyLogger(actor, event.getExtent(), event
						.getWorld()));
			}
	}
}
