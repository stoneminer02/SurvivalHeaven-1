/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Class CustomConfiguration.
 */
public class CustomConfiguration extends YamlConfiguration {

	/** The config file. */
	private final File configFile;

	/**
	 * Instantiates a new custom configuration.
	 *
	 * @param file
	 *            the file
	 */
	public CustomConfiguration(final File file) {
		this.configFile = file;
		load();
	}

	/**
	 * Load.
	 */
	public void load() {
		try {
			super.load(this.configFile);
		} catch (final FileNotFoundException e) {
			Logger.getLogger(JavaPlugin.class.getName()).log(
					Level.SEVERE,
					"Could not find " + this.configFile.getName()
							+ ", creating new one...");
			reload();
		} catch (final IOException e) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
					"Could not load " + this.configFile.getName(), e);
		} catch (final InvalidConfigurationException e) {
			Logger.getLogger(JavaPlugin.class.getName()).log(
					Level.SEVERE,
					this.configFile.getName()
							+ " is no valid configuration file", e);
		}
	}

	/**
	 * Load ressource.
	 *
	 * @param file
	 *            the file
	 * @return true, if successful
	 */
	public boolean loadRessource(final File file) {
		boolean out = true;
		if (!file.exists()) {
			final InputStream fis = getClass().getResourceAsStream(
					"/" + file.getName());
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				final byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					fos.write(buf, 0, i);
				}
			} catch (final Exception e) {
				Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
						"Failed to load config from JAR");
				out = false;
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (final Exception localException1) {
				}
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (final Exception localException2) {
				}
			}
		}
		return out;
	}

	/**
	 * Reload.
	 *
	 * @return true, if successful
	 */
	public boolean reload() {
		boolean out = true;
		if (!this.configFile.exists()) {
			out = loadRessource(this.configFile);
		}
		if (out) {
			load();
		}
		return out;
	}

	/**
	 * Save.
	 */
	public void save() {
		try {
			super.save(this.configFile);
		} catch (final IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName())
					.log(Level.SEVERE,
							"Could not save config to "
									+ this.configFile.getName(), ex);
		}
	}
}
