package com.mwg.api.posclient.webservice;

import java.io.InputStream;
import java.net.URL;

public class ResHelper {

	public static InputStream getAsStream(String path) {
		return ResHelper.class.getResourceAsStream(path);
	}

	public static URL getAsURL(String path) {
		return ResHelper.class.getResource(path);
	}
	
}
