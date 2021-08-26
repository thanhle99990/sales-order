package com.mwg.api.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class StringReadline {

	private final Listener listener;
	private final BufferedReader reader;
	private final boolean autoTrim;

	public StringReadline(String src, Listener listener) {
		this(src, true, listener);
	}

	public StringReadline(String src, boolean autoTrim, Listener listener) {
		this.reader = new BufferedReader(new StringReader(src));
		this.listener = listener;
		this.autoTrim = autoTrim;
		this.run();
	}

	private void run() {
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				if (autoTrim) {
					if (!line.trim().isEmpty()) {
						listener.onLine(line.trim());
					}
				}
				else {
					listener.onLine(line);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public interface Listener {
		public void onLine(String line);
	}
	
}
