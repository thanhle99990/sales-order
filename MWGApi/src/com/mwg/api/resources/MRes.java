package com.mwg.api.resources;

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

public class MRes {

	public static URL getResourceURL(String path) {
		return MRes.class.getResource(path);
	}

	public static InputStream getResourceStream(String path) {
		return MRes.class.getResourceAsStream(path);
	}

	public static ImageIcon getImageIcon(String name) {
		URL url = getResourceURL("images/" + name);
		return (url != null) ? new ImageIcon(url) : null;
	}

	public static Image getImage(String name) {
		ImageIcon icon = getImageIcon(name);
		return icon != null ? icon.getImage() : null;
	}
	
}
