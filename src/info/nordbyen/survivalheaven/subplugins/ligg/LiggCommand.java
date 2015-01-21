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

package info.nordbyen.survivalheaven.subplugins.ligg;

import info.nordbyen.survivalheaven.api.command.AbstractCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Class LiggCommand.
 */
public class LiggCommand extends AbstractCommand {

    /** The instance. */
    private static LiggCommand instance;

    /**
     * Clear command.
     */
    public static void clearCommand() {
        instance = null;
    }

    /**
     * Inits the command.
     */
    public static void initCommand() {
        if (instance == null) {
            instance = new LiggCommand("ligg");
        }
    }

    /**
     * Instantiates a new ligg command.
     * 
     * @param command the command
     */
    public LiggCommand(final String command) {
        super(command);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * info.nordbyen.survivalheaven.api.command.AbstractCommand#onCommand(org.
     * bukkit.command .CommandSender, org.bukkit.command.Command,
     * java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length > 0) {
            sender.sendMessage("This command doesn't accept any arguments.");
            return true;
        }
        if (sender instanceof Player) {
            Ligg.getInstance().performAction((Player) sender);
        } else {
            Ligg.getInstance().performAction(sender);
        }
        return true;
    }
}
