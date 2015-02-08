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

package info.nordbyen.survivalheaven;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;
import info.nordbyen.survivalheaven.api.mysql.IMysqlManager;
import info.nordbyen.survivalheaven.api.playerdata.IPlayerDataManager;
import info.nordbyen.survivalheaven.api.playerdata.note.INoteManager;
import info.nordbyen.survivalheaven.api.playerdata.warning.IWarningManager;
import info.nordbyen.survivalheaven.api.rankmanager.IRankManager;
import info.nordbyen.survivalheaven.api.regions.IRegionManager;
import info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager;
import info.nordbyen.survivalheaven.api.subplugin.ISubPluginManager;
import info.nordbyen.survivalheaven.api.util.Translator;
import info.nordbyen.survivalheaven.api.wand.IWandManager;
import info.nordbyen.survivalheaven.subplugins.DenyPlayerMode.DenyPlayerMode;
import info.nordbyen.survivalheaven.subplugins.bitly.plugin.ShortLink;
import info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager;
import info.nordbyen.survivalheaven.subplugins.blockprotection.BlockManager;
import info.nordbyen.survivalheaven.subplugins.blockprotection.BlockProtection;
import info.nordbyen.survivalheaven.subplugins.bossbar.BossbarAPI;
import info.nordbyen.survivalheaven.subplugins.loginmanager.LoginMessage;
import info.nordbyen.survivalheaven.subplugins.merchant.Merchant;
import info.nordbyen.survivalheaven.subplugins.mysql.MysqlManager;
import info.nordbyen.survivalheaven.subplugins.old_stuff.OldStuff;
import info.nordbyen.survivalheaven.subplugins.playerdata.NoteManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerDataManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerDataManagerPlugin;
import info.nordbyen.survivalheaven.subplugins.playerdata.WarningManager;
import info.nordbyen.survivalheaven.subplugins.preliminary.Preliminary;
import info.nordbyen.survivalheaven.subplugins.rankmanager.RankManager;
import info.nordbyen.survivalheaven.subplugins.regions.RegionManager;
import info.nordbyen.survivalheaven.subplugins.regions.RegionUpdater;
import info.nordbyen.survivalheaven.subplugins.serverutil.ServerUtils;
import info.nordbyen.survivalheaven.subplugins.shop.ShopHandler;
import info.nordbyen.survivalheaven.subplugins.subplugin.AnnoSubPluginLoader;
import info.nordbyen.survivalheaven.subplugins.subplugin.AnnoSubPluginManager;
import info.nordbyen.survivalheaven.subplugins.subplugin.SubPluginManager;
import info.nordbyen.survivalheaven.subplugins.title.TitleAPI;
import info.nordbyen.survivalheaven.subplugins.uendeligdropper.InfinityDispenser;
import info.nordbyen.survivalheaven.subplugins.wand.WandManager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class SurvivalHeaven.
 * 
 * Ask l0lkj about this plugin or if you plan to use it in any way! Contact
 * l0lkj on: Phone: 0047 48073448 Email: alexmsagen@gmail.com Skype: alex.l0lkj
 * 
 * @author l0lkj
 */
public class SH extends JavaPlugin implements ISH {

    /** The commands. */
    public HashMap<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();
    /** The Constant MOTTO. */
    public static final String MOTTO = ChatColor.LIGHT_PURPLE + "Skapt for spillerne";
    /** The Constant PREFIX. */
    public static final String PREFIX = ChatColor.RED + "S" + ChatColor.GRAY + "H ";
    /** The Constant PATH_TO_CONFIG_FOLDER. */
    public static final String PATH_TO_CONFIG_FOLDER = "./plugins/SurvivalHeaven/";
    /** The Constant NAME. */
    public static final String NAME = ChatColor.RED + "Survival" + ChatColor.GRAY + "Heaven" + ChatColor.RESET;
    /** The i survival heaven. */
    private static ISH iSurvivalHeaven;
    /** The plugin. */
    private static JavaPlugin plugin;
    /** The senter. */
    private Location senter = null;
    /** The Constant debug. */
    private final boolean debug = false; // TODO
    /** The Constant spam. */
    private final boolean spam = false; // TODO
    /** The version. */
    private String version = null;
    /** The name. */
    private String name = null;
    /** The note manager. */
    private INoteManager noteManager;
    /** The warning manager. */
    private IWarningManager warningManager;
    /** The block manager. */
    private IBlockManager blockManager;
    /** The mysql manager. */
    private IMysqlManager mysqlManager;
    /** The wand manager. */
    private IWandManager wandManager;
    /** The player data manager. */
    private IPlayerDataManager playerDataManager;
    /** The rank manager. */
    private IRankManager rankManager;
    /** The subplugin manager. */
    private ISubPluginManager subpluginManager;
    /** The anno sub plugin manager. */
    private IAnnoSubPluginManager annoSubPluginManager;
    /** The region manager. */
    private IRegionManager regionManager;

    /**
     * Gets the manager.
     * 
     * @return the manager
     */
    public static ISH getManager() {
        return iSurvivalHeaven;
    }

    /**
     * Gets the plugin.
     * 
     * @return the plugin
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Debug.
     * 
     * @param strings the strings
     */
    @Override
    public void debug(final Object... strings) {
        if (!debug)
            return;
        for (final Object s : strings) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[DEBUG] " + ChatColor.GRAY + s);
        }
    }

    /**
     * Gets the senter.
     * 
     * @return the senter
     */
    @Override
    public final Location getSenter() {
        if (senter == null) {
            senter = new Location(Bukkit.getWorlds().get(0), 140, 0, 89);
        }
        return senter;
    }

    /**
     * Gets the anno sub plugin manager.
     * 
     * @return the anno sub plugin manager
     */
    @Override
    public IAnnoSubPluginManager getAnnoSubPluginManager() {
        if (annoSubPluginManager == null) {
            annoSubPluginManager = new AnnoSubPluginManager();
        }
        return annoSubPluginManager;
    }

    /**
     * Gets the block manager.
     * 
     * @return the block manager
     */
    @Override
    public IBlockManager getBlockManager() {
        if (blockManager == null) {
            blockManager = new BlockManager();
        }
        return blockManager;
    }

    /**
     * Gets the mysql manager.
     * 
     * @return the mysql manager
     */
    @Override
    public IMysqlManager getMysqlManager() {
        if (mysqlManager == null) {
            mysqlManager = new MysqlManager();
        }
        return mysqlManager;
    }

    /**
     * Gets the note manager.
     * 
     * @return the note manager
     */
    @Override
    public INoteManager getNoteManager() {
        if (noteManager == null) {
            noteManager = new NoteManager();
        }
        return noteManager;
    }

    /**
     * Gets the player data manager.
     * 
     * @return the player data manager
     */
    @Override
    public IPlayerDataManager getPlayerDataManager() {
        if (playerDataManager == null) {
            playerDataManager = new PlayerDataManager();
        }
        return playerDataManager;
    }

    /**
     * Gets the rank manager.
     * 
     * @return the rank manager
     */
    @Override
    public IRankManager getRankManager() {
        if (rankManager == null) {
            rankManager = new RankManager();
        }
        return rankManager;
    }

    /**
     * Gets the region manager.
     * 
     * @return the region manager
     */
    @Override
    public IRegionManager getRegionManager() {
        if (regionManager == null) {
            regionManager = new RegionManager();
        }
        return regionManager;
    }

    /**
     * Gets the sub plugin manager.
     * 
     * @return the sub plugin manager
     */
    @Override
    public ISubPluginManager getSubPluginManager() {
        if (subpluginManager == null) {
            subpluginManager = new SubPluginManager();
        }
        return subpluginManager;
    }

    /**
     * Gets the wand manager.
     * 
     * @return the wand manager
     */
    @Override
    public IWandManager getWandManager() {
        if (wandManager == null) {
            wandManager = new WandManager();
        }
        return wandManager;
    }

    /**
     * Gets the warning manager.
     * 
     * @return the warning manager
     */
    @Override
    public IWarningManager getWarningManager() {
        if (warningManager == null) {
            warningManager = new WarningManager();
        }
        return warningManager;
    }

    /**
     * Spam.
     * 
     * @param strings the strings
     */
    public void spam(final Object... strings) {
        if (!spam)
            return;
        for (final Object s : strings) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[SPAM] " + ChatColor.GRAY + s);
        }
    }

    /**
     * Gets the plugin name.
     * 
     * @return the plugin name
     */
    @Override
    public String getPluginName() {
        return name;
    }

    /**
     * Gets the version.
     * 
     * @return the version
     */
    @Override
    public String getVersion() {
        return version;
    }

    /**
     * Disable sub plugins.
     */
    private void disableSubPlugins() {
        getSubPluginManager().disableAll();
        getAnnoSubPluginManager().disableAll();
    }

    /**
     * Enable sub plugins.
     */
    private void enableSubPlugins() {
        getSubPluginManager().enableAll();
        getAnnoSubPluginManager().enableAll();
    }

    /**
     * Load jars.
     */
    /*
     * Functions for enabling the plugin
     */
    @SuppressWarnings("unused")
    private void loadJars() {
        AnnoSubPluginLoader.testLoadJars();
    }

    /**
     * Load translations.
     */
    private void loadTranslations() {
        Translator.loadTrans("translation");
    }

    /*
     * Functions for disabling the plugin
     */
    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "STOPPER PLUGIN " + this.toString());
        disableSubPlugins();
        unregisterSubPlugins();
        saveTranslations();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        plugin = this;
        iSurvivalHeaven = this;
        version = this.getDescription().getVersion();
        name = this.getDescription().getName();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "STARTER PLUGIN " + this.toString());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "******************************************************************");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RESET + "Starter " + NAME + ChatColor.RESET + " v. " + ChatColor.YELLOW + version);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "------------------------------------------------------------------");
        loadTranslations();
        // loadJars(); TODO Fikse error her
        registerSubPlugins();
        enableSubPlugins();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "******************************************************************");
    }

    /**
     * Register sub plugins.
     */
    private void registerSubPlugins() {
        getSubPluginManager().addSubPlugin(new DenyPlayerMode("DenyPlayerMode"));
        getSubPluginManager().addSubPlugin(new LoginMessage("LoginMessages"));
        getSubPluginManager().addSubPlugin(new Preliminary("Prelimitary"));
        getSubPluginManager().addSubPlugin(new Merchant("Merchant"));
        getSubPluginManager().addSubPlugin(new ShopHandler("Shop"));
        getSubPluginManager().addSubPlugin(new BossbarAPI("BossbarAPI"));
        getSubPluginManager().addSubPlugin(new TitleAPI("TitleAPI"));
        getSubPluginManager().addSubPlugin(new RegionUpdater("RegionUpdater"));
        getSubPluginManager().addSubPlugin(new ShortLink("BitLy_UrlShortener"));
        getSubPluginManager().addSubPlugin(new BlockProtection("BlockProtection"));
        getSubPluginManager().addSubPlugin(new PlayerDataManagerPlugin("PlayerDataManager"));
        getSubPluginManager().addSubPlugin(new OldStuff("Gamle-Kommandoer"));
        // spm.addSubPlugin(new RemoteBukkitPlugin("RemoteConsole"));
        // spm.addSubPlugin( new Ligg( "LiggTester" ) );
        getAnnoSubPluginManager().addClass(InfinityDispenser.class);
        getAnnoSubPluginManager().addClass(ServerUtils.class);
        getAnnoSubPluginManager().addClass(NoteManager.class);
        getAnnoSubPluginManager().addClass(WarningManager.class);
        // AnnoSubPluginManager.addClass(EmployeeTest.class);
        // AnnoSubPluginManager.addClass( QuestHandler.class );
    }

    /**
     * Save translations.
     */
    private void saveTranslations() {
        Translator.saveTrans("translation");
    }

    /**
     * Unregister sub plugins.
     */
    private void unregisterSubPlugins() {
        getSubPluginManager().unregisterAll();
    }
}
