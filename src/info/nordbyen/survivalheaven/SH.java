/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
import info.nordbyen.survivalheaven.api.wand.IWandManager;
import info.nordbyen.survivalheaven.subplugins.DenyPlayerMode.DenyPlayerMode;
import info.nordbyen.survivalheaven.subplugins.bitly.plugin.ShortLink;
import info.nordbyen.survivalheaven.subplugins.blockdata.IBlockManager;
import info.nordbyen.survivalheaven.subplugins.blockprotection.BlockManager;
import info.nordbyen.survivalheaven.subplugins.blockprotection.BlockProtection;
import info.nordbyen.survivalheaven.subplugins.bossbar.BossbarAPI;
import info.nordbyen.survivalheaven.subplugins.commands.Commands;
import info.nordbyen.survivalheaven.subplugins.groupmanager.FriendManager;
import info.nordbyen.survivalheaven.subplugins.homes.HomeManager;
import info.nordbyen.survivalheaven.subplugins.merchant.Merchant;
import info.nordbyen.survivalheaven.subplugins.mysql.MysqlManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.NoteManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerDataManager;
import info.nordbyen.survivalheaven.subplugins.playerdata.PlayerDataManagerPlugin;
import info.nordbyen.survivalheaven.subplugins.playerdata.WarningManager;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class SH.
 */
public class SH extends JavaPlugin implements ISH {

	/** The debug. */
	private final static boolean DEBUG = false; // TODO

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

	/** The commands. */
	public HashMap<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();

	/** The Constant MOTTO. */
	public static final String MOTTO = ChatColor.LIGHT_PURPLE
			+ "Skapt for spillerne";

	/** The Constant PREFIX. */
	public static final String PREFIX = ChatColor.RED + "S" + ChatColor.GRAY
			+ "H ";

	/** The Constant PATH_TO_CONFIG_FOLDER. */
	public static final String PATH_TO_CONFIG_FOLDER = "./plugins/SurvivalHeaven/";

	/** The Constant NAME. */
	public static final String NAME = ChatColor.RED + "Survival"
			+ ChatColor.GRAY + "Heaven" + ChatColor.RESET;

	/** The i survival heaven. */
	private static ISH iSurvivalHeaven;

	/** The plugin. */
	private static JavaPlugin plugin;

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

	/** The friend manager. */
	private FriendManager friendManager;

	/** The home manager. */
	private HomeManager homeManager;

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

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getAnnoSubPluginManager()
	 */
	@Override
	public IAnnoSubPluginManager getAnnoSubPluginManager() {
		if (annoSubPluginManager == null) {
			annoSubPluginManager = new AnnoSubPluginManager();
		}
		return annoSubPluginManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getBlockManager()
	 */
	@Override
	public IBlockManager getBlockManager() {
		if (blockManager == null) {
			blockManager = new BlockManager();
		}
		return blockManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getFriendManager()
	 */
	@Override
	public FriendManager getFriendManager() {
		if (friendManager == null) {
			friendManager = new FriendManager();
		}
		return friendManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getHomeManager()
	 */
	@Override
	public HomeManager getHomeManager() {
		if (homeManager == null) {
			homeManager = new HomeManager();
		}
		return homeManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getMysqlManager()
	 */
	@Override
	public IMysqlManager getMysqlManager() {
		if (mysqlManager == null) {
			mysqlManager = new MysqlManager();
		}
		return mysqlManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getNoteManager()
	 */
	@Override
	public INoteManager getNoteManager() {
		if (noteManager == null) {
			noteManager = new NoteManager();
		}
		return noteManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getPlayerDataManager()
	 */
	@Override
	public IPlayerDataManager getPlayerDataManager() {
		if (playerDataManager == null) {
			playerDataManager = new PlayerDataManager();
		}
		return playerDataManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getRankManager()
	 */
	@Override
	public IRankManager getRankManager() {
		if (rankManager == null) {
			rankManager = new RankManager();
		}
		return rankManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getRegionManager()
	 */
	@Override
	public IRegionManager getRegionManager() {
		if (regionManager == null) {
			regionManager = new RegionManager();
		}
		return regionManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getSubPluginManager()
	 */
	@Override
	public ISubPluginManager getSubPluginManager() {
		if (subpluginManager == null) {
			subpluginManager = new SubPluginManager();
		}
		return subpluginManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getVersion()
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getWandManager()
	 */
	@Override
	public IWandManager getWandManager() {
		if (wandManager == null) {
			wandManager = new WandManager();
		}
		return wandManager;
	}

	/* (non-Javadoc)
	 * @see info.nordbyen.survivalheaven.ISH#getWarningManager()
	 */
	@Override
	public IWarningManager getWarningManager() {
		if (warningManager == null) {
			warningManager = new WarningManager();
		}
		return warningManager;
	}
	
	/**
	 * Download plugin.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public static boolean downloadPlugin(String id) {
		InputStreamReader in = null;
		try {
			URL url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
			URLConnection urlConnection = url.openConnection();
			in = new InputStreamReader(urlConnection.getInputStream());
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuilder sb = new StringBuilder();
			while ((numCharsRead = in.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			result = result.replace("\\/", "/")
					.replaceAll(".*\"downloadUrl\":\"", "").split("\",\"")[0];
			String[] split = result.split("/");
			url = new URL(result);
			final String path = plugin.getDataFolder().getParentFile()
					.getAbsoluteFile()
					+ "/" + split[split.length - 1];
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(path);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			log("Finished downloading " + split[split.length - 1] + ". Loading dependecy");
			Bukkit.getServer().getPluginManager().loadPlugin(new File(path));
			fos.close();
			return true;
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InvalidPluginException ex) {
			ex.printStackTrace();
		} catch (InvalidDescriptionException ex) {
			ex.printStackTrace();
		} catch (UnknownDependencyException ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Load dependencies.
	 */
	public void loadDependencies() {

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
	 * Register sub plugins.
	 */
	private void registerSubPlugins() {
		getSubPluginManager()
		.addSubPlugin(new DenyPlayerMode("DenyPlayerMode"));
		getSubPluginManager().addSubPlugin(new Merchant("Merchant"));
		getSubPluginManager().addSubPlugin(new ShopHandler("Shop"));
		getSubPluginManager().addSubPlugin(new BossbarAPI("BossbarAPI"));
		getSubPluginManager().addSubPlugin(new TitleAPI("TitleAPI"));
		getSubPluginManager().addSubPlugin(new RegionUpdater("RegionUpdater"));
		getSubPluginManager().addSubPlugin(new ShortLink("BitLy_UrlShortener"));
		getSubPluginManager().addSubPlugin(
				new BlockProtection("BlockProtection"));
		getSubPluginManager().addSubPlugin(
				new PlayerDataManagerPlugin("PlayerDataManager"));
		getSubPluginManager().addSubPlugin(new Commands("Kommandoer"));
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
	 * Unregister sub plugins.
	 */
	private void unregisterSubPlugins() {
		getSubPluginManager().unregisterAll();
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
		log(ChatColor.YELLOW + "STARTER PLUGIN " + this.toString());
		log(ChatColor.GREEN + "******************************************************************");
		log(ChatColor.RESET + "Starter " + NAME + ChatColor.RESET + " v. " + ChatColor.YELLOW + version);
		log(ChatColor.GREEN + "------------------------------------------------------------------");
		getAnnoSubPluginManager();
		getBlockManager();
		getMysqlManager();
		getNoteManager();
		getPlayerDataManager();
		getWandManager();
		getWarningManager();
		getFriendManager();
		getHomeManager();
		// loadJars(); TODO Fikse error her
		registerSubPlugins();
		enableSubPlugins();
		log(ChatColor.GOLD + "Sjekker om alle nødvendige plugins er her...");
		Plugin pex = Bukkit.getPluginManager().getPlugin("PermissionsEx");
		if (pex == null) {
			log(ChatColor.GOLD + "PermissionsEx mangler. Starter nedlasting....");
			try {
				downloadPlugin("31279");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log(ChatColor.GREEN + "******************************************************************");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		log(ChatColor.YELLOW + "STOPPER PLUGIN " + this.toString());
		disableSubPlugins();
		unregisterSubPlugins();
	}

	public static void log(final Object... strings) {
		for (final Object s : strings) {
			Bukkit.getConsoleSender().sendMessage(PREFIX + ChatColor.WHITE + s);
		}
	}
	
	public static void debug(final Object... strings) {
		if (!DEBUG)
			return;
		for (final Object s : strings) {
			log(ChatColor.LIGHT_PURPLE + "[DEBUG] " + ChatColor.GRAY + s);
		}
	}
}
