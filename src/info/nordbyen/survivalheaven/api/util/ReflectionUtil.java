/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <alexmsagen@gmail.com> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Alexander Sagen
 * ----------------------------------------------------------------------------
 */
package info.nordbyen.survivalheaven.api.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The Class ReflectionUtil.
 */
public class ReflectionUtil {

	/**
	 * Gets the new instance of.
	 *
	 * @param className
	 *            the class name
	 * @return the new instance of
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Object getNewInstanceOf(final String className)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException {
		Object instance = null;
		final Class<?> cls = Class.forName(className);
		@SuppressWarnings("rawtypes")
		final Constructor[] constructors = cls.getDeclaredConstructors();
		constructors[0].setAccessible(true);
		instance = constructors[0].newInstance();
		return instance;
	}

	/* Private constructor */
	/**
	 * Instantiates a new reflection util.
	 */
	private ReflectionUtil() {
	}
}
