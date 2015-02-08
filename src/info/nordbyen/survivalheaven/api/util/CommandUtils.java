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

package info.nordbyen.survivalheaven.api.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandUtils.
 */
public class CommandUtils {

    /**
     * Gets the help topics.
     * 
     * @return the help topics
     */
    public static List<HelpTopic> getHelpTopics() {
        final List<HelpTopic> helpTopics = new ArrayList<HelpTopic>();
        for (final HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
            helpTopics.add(cmdLabel);
        }
        return helpTopics;
    }

    /**
     * Gets the known commands.
     * 
     * @return the known commands
     * @throws NoSuchFieldException the no such field exception
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    @SuppressWarnings({ "unchecked", "unused" })
    public static Map<String, Command> getKnownCommands() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final PluginManager manager = Bukkit.getServer().getPluginManager();
        final SimplePluginManager spm = (SimplePluginManager) manager;
        List<Plugin> plugins = null;
        Map<String, Plugin> lookupNames = null;
        SimpleCommandMap commandMap = null;
        Map<String, Command> knownCommands = null;
        if (spm != null) {
            final Field pluginsField = spm.getClass().getDeclaredField("plugins");
            final Field lookupNamesField = spm.getClass().getDeclaredField("lookupNames");
            final Field commandMapField = spm.getClass().getDeclaredField("commandMap");
            pluginsField.setAccessible(true);
            lookupNamesField.setAccessible(true);
            commandMapField.setAccessible(true);
            plugins = (List<Plugin>) pluginsField.get(spm);
            lookupNames = (Map<String, Plugin>) lookupNamesField.get(spm);
            commandMap = (SimpleCommandMap) commandMapField.get(spm);
            final Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
        }
        return knownCommands;
        /*
         * if (commandMap != null) { for (Iterator<Map.Entry<String, Command>>
         * it = knownCommands.entrySet().iterator(); it.hasNext()) {
         * Map.Entry<String, Command> entry = it.next(); if (entry.getValue()
         * instanceof PluginCommand) { PluginCommand c = (PluginCommand)
         * entry.getValue(); } } }
         */
    }
}
