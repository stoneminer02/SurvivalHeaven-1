/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.rankmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.rankmanager.BadgeType;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;
import info.nordbyen.survivalheaven.api.regions.IRegionData;
import info.nordbyen.survivalheaven.api.util.FancyMessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * The Class RankManager.
 */
public class RankManager implements IRankManager {

	/**
	 * Instantiates a new rank manager.
	 */
	public RankManager() {
		Bukkit.getPluginManager().registerEvents(new RankManagerListener(),
				SH.getPlugin());
		SH.getPlugin().getCommand("rank").setExecutor(new RankManagerCommand());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.rankmanager.IRankManager#getBadges(java
	 * .lang.String)
	 */
	@Override
	public BadgeType[] getBadges(final String uuid) {
		final IPlayerData pd = SH.getManager().getPlayerDataManager()
				.getPlayerData(uuid);
		final BadgeType[] badges = new BadgeType[pd.getBadges().size()];
		int i = 0;
		for (final int badge : pd.getBadges()) {
			badges[i] = BadgeType.getBadgeFromId(badge);
			i++;
		}
		return badges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.rankmanager.IRankManager#getChatBadgePrefix
	 * (java.lang .String)
	 */
	@Override
	public String getChatBadgePrefix(final String uuid) {
		final BadgeType[] badges = getBadges(uuid);
		String prefix = "";
		for (final BadgeType badge : badges) {
			prefix += badge.getPrefix();
		}
		return prefix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.rankmanager.IRankManager#getChatRankPrefix
	 * (java.lang .String)
	 */
	@Override
	public String getChatRankPrefix(final String uuid) {
		final RankType rank = getRank(uuid);
		return rank.getPrefix();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.rankmanager.IRankManager#getRank(java.
	 * lang.String)
	 */
	@Override
	public RankType getRank(final String uuid) {
		final IPlayerData pd = SH.getManager().getPlayerDataManager()
				.getPlayerData(uuid);
		// if ( pd.isBanned() ) return RankType.getRankFromId( 0 ); TODO
		final RankType rank = RankType.getRankFromId(pd.getRank());
		return rank;
	}
	
	private static RankManager instance = new RankManager();
	public static RankManager getInstance()
	{
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.rankmanager.IRankManager#updateNames()
	 */
	@Override
	public void updateNames() {
		for (final Player o : Bukkit.getOnlinePlayers()) {
			o.setPlayerListName(getRank(o.getUniqueId().toString()).getColor()
					+ o.getName());
			o.setDisplayName(getRank(o.getUniqueId().toString()).getPrefix()
					+ o.getName());
			IRegionData rd = SH.getManager().getRegionManager()
					.getRegionAt(o.getLocation());
			if (rd == null)
				FancyMessages.sendTabTitle(o, SH.NAME, SH.MOTTO);
			else
				FancyMessages.sendTabTitle(o, SH.NAME, ChatColor.BLUE
						+ "Område: "
						+ ChatColor.GREEN
						+ rd.getName()
						+ ChatColor.BLUE
						+ "\nPvP: "
						+ (rd.isPvp() ? ChatColor.RED + "Ja" : ChatColor.GREEN
								+ "Nei"));
		}
	}
}
