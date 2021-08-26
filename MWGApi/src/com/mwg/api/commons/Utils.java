package com.mwg.api.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public class Utils {

	public static String replaceParams(String src, String name, String value, String openTag, String closeTag) {
		String pattern = openTag + name + closeTag;
		return src.replaceAll(pattern, value);
	}

	public static String replaceParams(String src, String name, String value) {
		return replaceParams(src, name, value, "\\{\\{", "\\}\\}");
	}

	public static String getHomeDir() {
		return System.getProperty("user.home");
	}
	
	public static Properties getPropertieFromFile(String fileName) throws Exception {
		File file = new File(fileName);
		Properties prop = new Properties();
		if (file.exists()) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");) {
				prop.load(reader);
			}
		}
		return prop;
	}
	
	public static void setPropertieToFile(String fileName, String key, String value) throws Exception {
		Properties prop = new Properties();
		prop.setProperty(key, value);
		setPropertieToFile(fileName, prop);
	}

	public static void setPropertieToFile(String fileName, Properties props) throws Exception {
		File file = new File(fileName);
		Properties prop = new Properties();
		if (file.exists()) {
			try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");) {
				prop.load(reader);
			}
		}
		props.keySet().forEach(key -> {
			prop.put(key, props.get(key));
		});
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8")) {
			prop.store(writer, "###################################################################");
		}
	}

}
