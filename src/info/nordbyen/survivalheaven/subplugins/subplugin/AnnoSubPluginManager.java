/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.subplugins.subplugin;

import info.nordbyen.survivalheaven.SH;
import info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenDisable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenEnable;
import info.nordbyen.survivalheaven.api.subplugin.annotations.SurvivalHeavenSubPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * The Class AnnoSubPluginManager.
 */
public class AnnoSubPluginManager implements IAnnoSubPluginManager {

	/** The Constant classes. */
	private static final HashMap<Class<?>, Boolean> classes = new HashMap<Class<?>, Boolean>();
	
	/** The enabled. */
	private static boolean enabled = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager#addClass
	 * (java.lang. Class)
	 */
	@Override
	public void addClass(final Class<?> klass) {
		final SurvivalHeavenSubPlugin subPluginAnno = klass
				.getAnnotation(SurvivalHeavenSubPlugin.class);
		if (subPluginAnno == null)
			return;
		if (classes.containsKey(klass))
			return;
		Bukkit.getConsoleSender().sendMessage(
				ChatColor.GRAY + "Registrerer " + ChatColor.GOLD
						+ subPluginAnno.name());
		classes.put(klass, false);
		if (enabled) {
			enableClass(klass);
			classes.put(klass, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager#disableAll
	 * ()
	 */
	@Override
	public void disableAll() {
		for (final Class<?> klass : classes.keySet())
			if (classes.get(klass)) {
				disableClass(klass);
			}
		enabled = false;
	}

	/**
	 * Disable class.
	 *
	 * @param klass
	 *            the klass
	 */
	private void disableClass(final Class<?> klass) {
		try {
			final SurvivalHeavenSubPlugin subPluginAnno = klass
					.getAnnotation(SurvivalHeavenSubPlugin.class);
			if (subPluginAnno == null)
				return;
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.GRAY + "Starter disabling av " + ChatColor.GOLD
							+ subPluginAnno.name());
			final HashMap<Method, Annotation> methodAnnotTypes = getMethodsAnnotatedWith(
					klass, SurvivalHeavenDisable.class);
			for (final Entry<Method, Annotation> entry : methodAnnotTypes
					.entrySet()) {
				final Method method = entry.getKey();
				method.setAccessible(true);
				try {
					method.invoke(null, new Object[] { SH.getPlugin() });
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager#enableAll
	 * ()
	 */
	@Override
	public void enableAll() {
		for (final Class<?> klass : classes.keySet())
			if (!classes.get(klass)) {
				enableClass(klass);
			}
		enabled = true;
	}

	/**
	 * Enable class.
	 *
	 * @param klass
	 *            the klass
	 */
	private void enableClass(final Class<?> klass) {
		try {
			final SurvivalHeavenSubPlugin subPluginAnno = klass
					.getAnnotation(SurvivalHeavenSubPlugin.class);
			if (subPluginAnno == null)
				return;
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.GRAY + "Starter enabling av " + ChatColor.GOLD
							+ subPluginAnno.name());
			final HashMap<Method, Annotation> methodAnnotTypes = getMethodsAnnotatedWith(
					klass, SurvivalHeavenEnable.class);
			for (final Entry<Method, Annotation> entry : methodAnnotTypes
					.entrySet()) {
				final Method method = entry.getKey();
				method.setAccessible(true);
				try {
					method.invoke(null, new Object[] { SH.getPlugin() });
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the methods annotated with.
	 *
	 * @param type
	 *            the type
	 * @param annotation
	 *            the annotation
	 * @return the methods annotated with
	 */
	private HashMap<Method, Annotation> getMethodsAnnotatedWith(
			final Class<?> type, final Class<? extends Annotation> annotation) {
		final HashMap<Method, Annotation> methods = new HashMap<Method, Annotation>();
		Class<?> klass = type;
		while (klass != Object.class) {
			final List<Method> allMethods = new ArrayList<Method>(
					Arrays.asList(klass.getDeclaredMethods()));
			for (final Method method : allMethods) {
				if ((annotation == null)
						|| method.isAnnotationPresent(annotation)) {
					final Annotation annotInstance = method
							.getAnnotation(annotation);
					methods.put(method, annotInstance);
				}
			}
			klass = klass.getSuperclass();
		}
		return methods;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.nordbyen.survivalheaven.api.subplugin.IAnnoSubPluginManager#removeClass
	 * (java.lang .Class)
	 */
	@Override
	public void removeClass(final Class<?> klass) {
		if (!classes.containsKey(klass))
			return;
		if (classes.get(klass)) {
			disableClass(klass);
		}
		classes.remove(klass);
	}
}
