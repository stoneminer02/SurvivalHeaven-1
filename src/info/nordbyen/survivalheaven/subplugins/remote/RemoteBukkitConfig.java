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

package info.nordbyen.survivalheaven.subplugins.remote;

import info.nordbyen.survivalheaven.api.config.CustomConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoteBukkitConfig.
 */
public class RemoteBukkitConfig extends CustomConfiguration {

    /** The cfg. */
    private static RemoteBukkitConfig cfg;

    /**
     * Gets the single instance of RemoteBukkitConfig.
     * 
     * @return single instance of RemoteBukkitConfig
     */
    public static RemoteBukkitConfig getInstance() {
        if (cfg == null) {
            cfg = new RemoteBukkitConfig();
        }
        return cfg;
    }

    /**
     * Instantiates a new remote bukkit config.
     */
    public RemoteBukkitConfig() {
        super(new File("./plugins/SurvivalHeaven/remoteconsole.yml"));
        cfg = this;
        load();
        save();
        saveDefault();
    }

    /**
     * Save default.
     */
    private void saveDefault() {
        if (!contains("port")) {
            set("port", "25564");
        }
        if (!contains("verbose")) {
            set("verbose", true);
        }
        if (!contains("logsize")) {
            set("logsize", 500);
        }
        final ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
        if (!contains("users")) {
            set("user", users);
        }
        save();
    }
}
