/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
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
 * The Class Commands.
 */
public class Commands extends SubPlugin {

	/**
	 * Instantiates a new commands.
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
