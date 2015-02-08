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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * The Enum Translator.
 */
public enum Translator {
	/** The norsk. */
	NORSK,
	/** The engelsk. */
	ENGELSK;

	/**
	 * The Class TextHolder.
	 */
	public static class TextHolder {

		/** The engelsk. */
		private final String key, norsk, engelsk;

		/**
		 * Instantiates a new text holder.
		 * 
		 * @param key
		 *            the key
		 * @param norsk
		 *            the norsk
		 * @param engelsk
		 *            the engelsk
		 */
		private TextHolder(final String key, final String norsk,
				final String engelsk) {
			this.key = key;
			this.norsk = norsk;
			this.engelsk = engelsk;
		}

		/**
		 * Gets the key.
		 * 
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Gets the translation.
		 * 
		 * @param language
		 *            the language
		 * @return the translation
		 */
		public String getTranslation(final Translator language) {
			if (language == Translator.NORSK)
				return norsk;
			else if (language == Translator.ENGELSK)
				return engelsk;
			return null;
		}
	}

	/** The trans list. */
	private static HashMap<String, TextHolder> transList = new HashMap<String, TextHolder>();

	/**
	 * Adds the text.
	 * 
	 * @param key
	 *            the key
	 * @param norsk
	 *            the norsk
	 * @param engelsk
	 *            the engelsk
	 */
	public static void addText(final String key, final String norsk,
			final String engelsk) {
		final TextHolder holder = new TextHolder(key, norsk, engelsk);
		transList.put(key, holder);
	}

	/**
	 * Gets the text.
	 * 
	 * @param key
	 *            the key
	 * @param language
	 *            the language
	 * @return the text
	 */
	public static String getText(final String key, final Translator language) {
		return transList.get(key).getTranslation(language);
	}

	/**
	 * Gets the translations.
	 * 
	 * @return the translations
	 */
	public static Collection<TextHolder> getTranslations() {
		final Collection<TextHolder> holders = transList.values();
		return holders;
	}

	/**
	 * Load trans.
	 * 
	 * @param name
	 *            the name
	 */
	public static void loadTrans(final String name) {
		final String filename = name + ".OVERSETTELSE";
		final File transfile = new File(
				"./plugins/SurvivalHeaven/translations/" + filename);
		transfile.mkdirs();
		if (!transfile.exists()) {
			try {
				transfile.createNewFile();
			} catch (final IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(transfile))) {
			int num = 0;
			for (String line; (line = br.readLine()) != null;) {
				num++;
				String key = null;
				String norsk = null;
				String engelsk = null;
				try {
					final String[] mid = line.split("<BREAK>");
					final String[] set = mid[1].split("<MID>");
					key = set[0];
					norsk = set[1];
					engelsk = set[2];
				} catch (final Exception e) {
					e.printStackTrace();
				}
				if ((key == null) || (norsk == null) || (engelsk == null)) {
					try {
						throw new Exception("Error under lesing av linje: "
								+ num);
					} catch (final Exception e) {
						e.printStackTrace();
					}
					continue;
				}
				// System.out.println( num + " : " + key + " : " + norsk + " : "
				// + engelsk );
				// ( new Exception( "DEBUG STACKTRACE" ) ).printStackTrace();
				addText(key, norsk, engelsk);
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save trans.
	 * 
	 * @param name
	 *            the name
	 */
	public static void saveTrans(final String name) {
		final String filename = name + ".OVERSETTELSE";
		final File transfile = new File(
				"./plugins/SurvivalHeaven/translations/" + filename);
		transfile.mkdirs();
		try {
			transfile.createNewFile();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}
		/* File Not Found D: */
		if (!transfile.exists()) {
			try {
				throw new FileNotFoundException("Finner ikke filen D:");
			} catch (final FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		/* Tømme filen */
		transfile.delete();
		try {
			transfile.createNewFile();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		/* Open fileWriter */
		FileWriter fw = null;
		try {
			fw = new FileWriter(transfile);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		/* Print all translations */
		int num = getTranslations().size();
		for (final TextHolder holder : getTranslations()) {
			try {
				fw.write("<BREAK>" + holder.getKey() + "<MID>"
						+ holder.getTranslation(Translator.NORSK) + "<MID>"
						+ holder.getTranslation(Translator.ENGELSK) + "<BREAK>"
						+ (num == 0 ? "" : "\n"));
				num--;
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		/* "Slippe" filen */
		try {
			fw.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
