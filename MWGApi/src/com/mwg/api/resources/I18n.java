package com.mwg.api.resources;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class I18n {

	private static Properties prop = new Properties();

	private I18n() {
	}

	public static void load(String locale) throws Exception {
		InputStream in = I18n.class.getResourceAsStream("langs/i18n-" + locale + ".properties");
		prop.load(new InputStreamReader(in, Charset.forName("UTF-8")));
	}

	public static String get(String key) {
		return prop.getOrDefault(key, "{" + key + "}").toString();
	}
	
}
