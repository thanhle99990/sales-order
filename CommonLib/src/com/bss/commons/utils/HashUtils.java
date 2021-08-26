package com.bss.commons.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtils {
	public static String encodeMD5(String value) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String encodeSHA1(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			return new BigInteger(1, md.digest(value.getBytes("UTF-8"))).toString(16);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
