/**
 * This file is part of survivalheaven.org, licensed under the MIT License (MIT).
 *
 * Copyright (c) SurvivalHeaven.org <http://www.survivalheaven.org>
 * Copyright (c) NordByen.info <http://www.nordbyen.info>
 * Copyright (c) l0lkj.info <http://www.l0lkj.info>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.nordbyen.survivalheaven.subplugins.rankmanager;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerData;
import info.nordbyen.survivalheaven.api.rankmanager.BadgeType;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.rankmanager.RankType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The Class RankManager.
 */
public class RankManager implements IRankManager {

    /**
     * Instantiates a new rank manager.
     */
    public RankManager() {
        Bukkit.getPluginManager().registerEvents(new RankManagerListener(), SH.getPlugin());
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
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(uuid);
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
        final IPlayerData pd = SH.getManager().getPlayerDataManager().getPlayerData(uuid);
        // if ( pd.isBanned() ) return RankType.getRankFromId( 0 ); TODO
        final RankType rank = RankType.getRankFromId(pd.getRank());
        return rank;
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
            o.setPlayerListName(getRank(o.getUniqueId().toString()).getPrefix() + o.getName());
            o.setDisplayName(getRank(o.getUniqueId().toString()).getPrefix() + o.getName());
        }
    }
}
