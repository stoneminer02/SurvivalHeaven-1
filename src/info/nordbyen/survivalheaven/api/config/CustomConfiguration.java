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
     * @param file the file
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
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not find " + this.configFile.getName() + ", creating new one...");
            reload();
        } catch (final IOException e) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not load " + this.configFile.getName(), e);
        } catch (final InvalidConfigurationException e) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, this.configFile.getName() + " is no valid configuration file", e);
        }
    }

    /**
     * Load ressource.
     * 
     * @param file the file
     * @return true, if successful
     */
    public boolean loadRessource(final File file) {
        boolean out = true;
        if (!file.exists()) {
            final InputStream fis = getClass().getResourceAsStream("/" + file.getName());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                final byte[] buf = new byte[1024];
                int i = 0;
                while ((i = fis.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            } catch (final Exception e) {
                Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Failed to load config from JAR");
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
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + this.configFile.getName(), ex);
        }
    }
}
