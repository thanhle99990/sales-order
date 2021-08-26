package com.bss.commons.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicBoolean;

public class Utils {

	public static String dumpException(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	public static String MD5(String stringToHash) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(stringToHash.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (Exception ex) {
			return null;
		}
	}

	public static boolean isRootUser() throws Exception {
		AtomicBoolean isRoot = new AtomicBoolean(false);
		ShellCmd.create().addCmd("id -u").outputConsumer(output -> isRoot.set(output.equals("0"))).exe(1000);
		return isRoot.get();
	}

	public static boolean fileIsReadyForRead(String path) {
		File ff = new File(path);
		if (ff.exists()) {
			try (RandomAccessFile ran = new RandomAccessFile(ff, "rw")) {
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static <T> T Coalesce(T v, T d) {
		return (v == null) ? d : v;
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) {
			return bytes + " B";
		}
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
