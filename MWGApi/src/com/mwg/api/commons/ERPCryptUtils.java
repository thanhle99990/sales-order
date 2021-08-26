package com.mwg.api.commons;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ERPCryptUtils {
	
	private static final String charset = "UTF-8";

	private static final byte[] IV = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	public static String Encrypt(String strText) throws Exception {
		final String strEncrKey = getKey();
		int intLength = Math.min(strEncrKey.length(), 8);
		int bIdx = strEncrKey.length() - intLength - 1;
		int eIdx = bIdx + intLength;
		byte[] bKey = strEncrKey.substring(bIdx, eIdx).getBytes(charset);
		SecretKeySpec secretKey = new SecretKeySpec(bKey, "DES");
		byte[] inputByteArray = strText.getBytes("UTF-8");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV));
		byte[] cipherText = cipher.doFinal(inputByteArray);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String Decrypt(String strText) throws Exception {
		if (strText.length() == 0) {
			return "";
		}
		final String strEncrKey = getKey();
		int intLength = Math.min(strEncrKey.length(), 8);
		int bIdx = strEncrKey.length() - intLength - 1;
		int eIdx = bIdx + intLength;
		byte[] bKey = strEncrKey.substring(bIdx, eIdx).getBytes(charset);
		SecretKeySpec secretKey = new SecretKeySpec(bKey, "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV));
		byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(strText));
		return new String(cipherText, charset);
	}

	private static String getKey() {
		final String strEncrKey = "kt389tg#167ngt8967utg";
		return strEncrKey.trim().length() < 9 ? "68686782349fs@#$%f23" + strEncrKey : strEncrKey;
	}
}
