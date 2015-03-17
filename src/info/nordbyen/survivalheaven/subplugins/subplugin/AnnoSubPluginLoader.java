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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;

/**
 * The Class AnnoSubPluginLoader.
 */
public class AnnoSubPluginLoader {

	/**
	 * Adds the file.
	 *
	 * @param f
	 *            the f
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addFile(final File f) throws IOException {
		addURL(f.toURI().toURL());
	}

	/**
	 * Adds the file.
	 *
	 * @param s
	 *            the s
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addFile(final String s) throws IOException {
		final File f = new File(s);
		addFile(f);
	}

	/**
	 * Adds the url.
	 *
	 * @param u
	 *            the u
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addURL(final URL u) throws IOException {
		final URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		final Class<URLClassLoader> sysclass = URLClassLoader.class;
		try {
			final Method method = sysclass.getDeclaredMethod("addURL",
					parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		}
	}

	/**
	 * Gets the classes in jar.
	 *
	 * @param jarPath
	 *            the jar path
	 * @return the classes in jar
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static List<String> getClassesInJar(final String jarPath)
			throws IOException {
		final List<String> classNames = new ArrayList<String>();
		final ZipInputStream zip = new ZipInputStream(new FileInputStream(
				jarPath));
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip
				.getNextEntry())
			if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
				final StringBuilder className = new StringBuilder();
				for (final String part : entry.getName().split("/")) {
					if (className.length() != 0) {
						className.append(".");
					}
					className.append(part);
					if (part.endsWith(".class")) {
						className.setLength(className.length()
								- ".class".length());
					}
				}
				classNames.add(className.toString());
			}
		zip.close();
		return classNames;
	}

	/**
	 * Load jars.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static void loadJars() throws IOException, ClassNotFoundException {
		folder.mkdirs();
		for (final File file : folder.listFiles()) {
			if (!file.getName().endsWith(".jar")) {
				continue;
			}
			Bukkit.broadcastMessage("Fant en jar fil " + file.getAbsolutePath());
			jars.add(file);
			addFile(file);
			List<String> classes;
			classes = getClassesInJar(file.getAbsolutePath());
			for (final String klassName : classes) {
				SH.getManager().getAnnoSubPluginManager()
						.addClass(Class.forName(klassName));
			}
		}
	}

	/**
	 * Test load jar.
	 *
	 * @param f
	 *            the f
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static void testLoadJar(final File f) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		final URLClassLoader urlCl = new URLClassLoader(new URL[] { f.toURI()
				.toURL() }, System.class.getClassLoader());
		for (final String cls : getClassesInJar(f.getAbsolutePath())) {
			final Class<?> main = urlCl.loadClass(cls);
			SH.getManager().getAnnoSubPluginManager().addClass(main);
		}
		urlCl.close();
	}

	/**
	 * Test load jars.
	 */
	public static void testLoadJars() {
		for (final File file : folder.listFiles()) {
			if (file.getName().endsWith(".jar")) {
				try {
					testLoadJar(file.getAbsoluteFile());
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** The Constant jars. */
	private static final ArrayList<File> jars = new ArrayList<File>();

	/** The Constant folder. */
	private static final File folder = new File(
			"./plugins/SurvivalHeaven-CORE/SubPlugins/");

	/** The Constant parameters. */
	private static final Class<?>[] parameters = new Class[] { URL.class };
}
