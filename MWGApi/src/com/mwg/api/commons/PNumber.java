package com.mwg.api.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PNumber {

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytes2Hex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexString2ByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static long hexString2Number(String s) {
		return Long.valueOf(s, 16);
	}

	public static long binString2Number(String s) {
		return Long.valueOf(s, 2);
	}

	public static Double roundHalfDown(Double number, int scale) {
		return (number != null) ? new BigDecimal(number).setScale(scale, RoundingMode.HALF_DOWN).doubleValue() : null;
	}

	public static Float roundHalfDown(Float number, int scale) {
		return (number != null) ? new BigDecimal(number.doubleValue()).setScale(scale, RoundingMode.HALF_DOWN).floatValue() : null;
	}

	public static Double roundHalfUp(Double number, int scale) {
		return (number != null) ? new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP).doubleValue() : null;
	}

	public static Float roundHalfUp(Float number, int scale) {
		return (number != null) ? new BigDecimal(number.doubleValue()).setScale(scale, RoundingMode.HALF_UP).floatValue() : null;
	}

	public static Double getFractional(Double number) {
		if (number != null) {
			return number - number.longValue();
		}
		return null;
	}

	public static boolean isMultipleOf(long a, long b) {
		return a % b == 0;
	}

	public static boolean isMultipleOf(double a, double b) {
		return isMultipleOf(a, b, 1000);
	}

	public static boolean isMultipleOf(double a, double b, int scale) {
		return isMultipleOf(Double.valueOf(a * scale).intValue(), Double.valueOf(b * scale).intValue());
	}
	
}
