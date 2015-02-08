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

// TODO: Auto-generated Javadoc
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
        final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
        if (subPluginAnno == null)
            return;
        if (classes.containsKey(klass))
            return;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Registrerer " + ChatColor.GOLD + subPluginAnno.name());
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
     * @param klass the klass
     */
    private void disableClass(final Class<?> klass) {
        try {
            final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
            if (subPluginAnno == null)
                return;
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Starter disabling av " + ChatColor.GOLD + subPluginAnno.name());
            final HashMap<Method, Annotation> methodAnnotTypes = getMethodsAnnotatedWith(klass, SurvivalHeavenDisable.class);
            for (final Entry<Method, Annotation> entry : methodAnnotTypes.entrySet()) {
                final Method method = entry.getKey();
                method.setAccessible(true);
                try {
                    method.invoke(null, new Object[] { SH.getPlugin() });
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
     * @param klass the klass
     */
    private void enableClass(final Class<?> klass) {
        try {
            final SurvivalHeavenSubPlugin subPluginAnno = klass.getAnnotation(SurvivalHeavenSubPlugin.class);
            if (subPluginAnno == null)
                return;
            Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "Starter enabling av " + ChatColor.GOLD + subPluginAnno.name());
            final HashMap<Method, Annotation> methodAnnotTypes = getMethodsAnnotatedWith(klass, SurvivalHeavenEnable.class);
            for (final Entry<Method, Annotation> entry : methodAnnotTypes.entrySet()) {
                final Method method = entry.getKey();
                method.setAccessible(true);
                try {
                    method.invoke(null, new Object[] { SH.getPlugin() });
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
     * @param type the type
     * @param annotation the annotation
     * @return the methods annotated with
     */
    private HashMap<Method, Annotation> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final HashMap<Method, Annotation> methods = new HashMap<Method, Annotation>();
        Class<?> klass = type;
        while (klass != Object.class) {
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if ((annotation == null) || method.isAnnotationPresent(annotation)) {
                    final Annotation annotInstance = method.getAnnotation(annotation);
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
