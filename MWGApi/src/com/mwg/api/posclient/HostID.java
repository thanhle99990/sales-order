package com.mwg.api.posclient;

public class HostID {

	private static final String unknownAsNull = "unknown";

	private static String osName = null;
	private static String hostName = null;
	private static String computerId = null;
	private static String hardwareSerial = null;
	private static String diskUUID = null;

	public static String getOsName() {
		return osName;
	}

	public static String getHostName() {
		return hostName;
	}

	public static String getComputerId() {
		return computerId;
	}

	public static String getHardwareSerial() {
		return hardwareSerial;
	}

	public static String getDiskUUID() {
		return diskUUID;
	}

	public static void getInfo() throws Exception {
	}

	private static String unknownAsNull(String value) {
		if (value != null && (value.trim().isEmpty() || value.trim().toLowerCase().equals(unknownAsNull))) {
			return null;
		}
		return (value != null) ? value.trim(): null;
	}
	
	
	private static String trimMaxLen(String src,int maxLen) {
		if(src != null && maxLen > 0 && src.length() > maxLen) {
			return src.substring(0, maxLen);
		}
		return src;
	}
	
}
