package de.duester.statistica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;

// предоставление локализованных строк
public class Resource {
	private static PropertyResourceBundle bundle;

	public static void init(String id) {
		try {
			bundle = new PropertyResourceBundle(new InputStreamReader(
					new FileInputStream("src/resources_" + id + ".properties"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// bundle = (PropertyResourceBundle) PropertyResourceBundle.getBundle("resources",
		// new Locale(id));
	}

	public static String getString(String s) {
		return bundle.getString(s);
	}

	public static String getString(String s, Object... args) {
		return MessageFormat.format(getString(s), args);
	}
}
