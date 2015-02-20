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

package info.nordbyen.survivalheaven.subplugins.commands;

import info.nordbyen.survivalheaven.api.subplugin.SubPlugin;
import info.nordbyen.survivalheaven.subplugins.commands.commands.AFK;
import info.nordbyen.survivalheaven.subplugins.commands.commands.AdminStick;
import info.nordbyen.survivalheaven.subplugins.commands.commands.BR;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Blokker;
import info.nordbyen.survivalheaven.subplugins.commands.commands.EC;
import info.nordbyen.survivalheaven.subplugins.commands.commands.FSpeed;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Fix;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Fly;
import info.nordbyen.survivalheaven.subplugins.commands.commands.H;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Hatt;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Inv;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Jobb;
import info.nordbyen.survivalheaven.subplugins.commands.commands.K;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Kick;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Killall;
import info.nordbyen.survivalheaven.subplugins.commands.commands.S;
import info.nordbyen.survivalheaven.subplugins.commands.commands.SS;
import info.nordbyen.survivalheaven.subplugins.commands.commands.ServerCommand;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Sitt;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Smelt;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Sudo;
import info.nordbyen.survivalheaven.subplugins.commands.commands.TP;
import info.nordbyen.survivalheaven.subplugins.commands.commands.TPH;
import info.nordbyen.survivalheaven.subplugins.commands.commands.WB;
import info.nordbyen.survivalheaven.subplugins.commands.commands.Who;

/**
 * The Class OldStuff.
 * 
 * @author l0lkj
 */
public class Commands extends SubPlugin {

	/**
	 * Instantiates a new old stuff.
	 * 
	 * @param name
	 *            the name
	 */
	public Commands(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#disable()
	 */
	@Override
	protected void disable() {
		ServerCommand.clearCommand();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.nordbyen.survivalheaven.api.subplugin.SubPlugin#enable()
	 */
	@Override
	protected void enable() {
		getPlugin().getCommand("jobb").setExecutor(new Jobb());
		getPlugin().getCommand("fix").setExecutor(new Fix());
		getPlugin().getCommand("wb").setExecutor(new WB());
		getPlugin().getCommand("blokker").setExecutor(new Blokker());
		getPlugin().getCommand("inv").setExecutor(new Inv());
		getPlugin().getCommand("kick").setExecutor(new Kick());
		getPlugin().getCommand("afk").setExecutor(new AFK());
		getPlugin().getCommand("ss").setExecutor(new SS());
		getPlugin().getCommand("tp").setExecutor(new TP());
		getPlugin().getCommand("tph").setExecutor(new TPH());
		getPlugin().getCommand("k").setExecutor(new K());
		getPlugin().getCommand("h").setExecutor(new H());
		getPlugin().getCommand("s").setExecutor(new S());
		getPlugin().getCommand("smelt").setExecutor(new Smelt());
		getPlugin().getCommand("hatt").setExecutor(new Hatt());
		getPlugin().getCommand("ec").setExecutor(new EC());
		getPlugin().getCommand("who").setExecutor(new Who());
		getPlugin().getCommand("fspeed").setExecutor(new FSpeed());
		getPlugin().getCommand("killall").setExecutor(new Killall());
		getPlugin().getCommand("bug").setExecutor(new BR());
		getPlugin().getCommand("fly").setExecutor(new Fly());
		getPlugin().getCommand("sitt").setExecutor(new Sitt());
		getPlugin().getCommand("sudo").setExecutor(new Sudo());
		getPlugin().getCommand("adminstick").setExecutor(new AdminStick());

		ServerCommand.initCommand();
	}
}
