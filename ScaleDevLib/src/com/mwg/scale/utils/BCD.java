package com.mwg.scale.utils;

public class BCD {
	public static byte[] Number2BCD(long num) {
		int digits = 0;
		long temp = num;
		while (temp != 0) {
			digits++;
			temp /= 10;
		}
		int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;
		byte bcd[] = new byte[byteLen];
		for (int i = 0; i < digits; i++) {
			byte tmp = (byte) (num % 10);
			if (i % 2 == 0) {
				bcd[i / 2] = tmp;
			} else {
				bcd[i / 2] |= (byte) (tmp << 4);
			}
			num /= 10;
		}
		for (int i = 0; i < byteLen / 2; i++) {
			byte tmp = bcd[i];
			bcd[i] = bcd[byteLen - i - 1];
			bcd[byteLen - i - 1] = tmp;
		}
		return bcd;
	}

	public static long BCD2Number(byte[] bcd) {
		return Long.valueOf(BCD.BCD2String(bcd));
	}

	public static String BCD2String(byte bcd) {
		StringBuffer sb = new StringBuffer();
		byte high = (byte) (bcd & 0xf0);
		high >>>= (byte) 4;
		high = (byte) (high & 0x0f);
		byte low = (byte) (bcd & 0x0f);
		sb.append(high);
		sb.append(low);
		return sb.toString();
	}

	public static String BCD2String(byte[] bcd) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bcd.length; i++) {
			sb.append(BCD2String(bcd[i]));
		}
		return sb.toString();
	}

	public static long BCDHexString2Number(String hexStr) {
		return BCD2Number(Utils.hexString2ByteArray(hexStr));
	}

	public static String Number2BCDHexString(long number) {
		String result = BCD2String(Number2BCD(number));
		while (result.startsWith("0")) {
			result = result.substring(1);
		}
		return result;
	}

}