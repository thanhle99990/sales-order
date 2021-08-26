package com.mwg.api.commons;

import java.util.Random;

public class EncryptNumber {

	private static Integer constPlus = 1;

	public static String decode(String codefirst, String codeSecond) {
		String rs = "";

		codefirst = codefirst.replaceFirst("^0+(?!$)", "");
		Integer lenCodefirst = codefirst.length() + constPlus;

		Integer numberRandom = Integer.parseInt(codeSecond.substring(5, 6) + "");

		char[] charCodeSecond = codeSecond.substring(0, 5).toCharArray();
		for (char ch : charCodeSecond) {
			Integer numberCh = Integer.parseInt(ch + "");
			rs = rs + Math.floorMod(numberCh - lenCodefirst + numberRandom, 10);
		}

		return rs + "00";
	}

	public static boolean checkValid(String codefirst, String codeSecond) {
		boolean isCheckValid = false;
		codefirst = codefirst.replaceFirst("^0+(?!$)", "");

		Integer numberRandom = Integer.parseInt(codeSecond.substring(5, 6) + "");
		Integer checkSumInCode = Integer.parseInt(codeSecond.substring(6, 7) + "");

		Integer sum = Integer.parseInt(codefirst);
		char[] charCodeSecond = codeSecond.substring(0, 5).toCharArray();
		for (char ch : charCodeSecond) {
			Integer numberCh = Integer.parseInt(ch + "");
			sum = sum + numberCh;
		}

		Integer checkSum = Math.floorMod(sum + numberRandom, 10);
		if (checkSum.equals(checkSumInCode))
			isCheckValid = true;

		return isCheckValid;
	}
}
