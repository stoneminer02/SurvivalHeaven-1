/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.quest.first_encounter;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.subplugins.quest.Acceptable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The Class AcceptHandler.
 */
public class AcceptHandler implements Acceptable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.subplugins.quest.Acceptable#executedAccept
	 * (java.lang.String )
	 */
	@Override
	public void executedAccept(final String uuid) {
		if (FirstEncounter.getWaitingPlayers().contains(uuid)) {
			final IPlayerData pd = SH.getManager().getPlayerDataManager()
					.getPlayerData(uuid);
			final Player p = Bukkit.getPlayer(pd.getName());
			p.sendMessage(org.bukkit.ChatColor.BLUE + "Yay :D");
		}
	}
}
