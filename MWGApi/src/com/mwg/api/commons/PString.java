package com.mwg.api.commons;

public class PString {
	public static String padLeft(String str, Character chr, int len) {
		int strLen = str.length();
		String ret = str;
		for (int i = strLen; i < len; i++) {
			ret = chr + ret;
		}
		return ret;
	}

	public static String padRight(String str, Character chr, int len) {
		int strLen = str.length();
		String ret = str;
		for (int i = strLen; i < len; i++) {
			ret = ret + chr;
		}
		return ret;
	}
}
