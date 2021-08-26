package com.bss.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date getBeginDate() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.parse(df.format(new Date()));
	}

	public static Date getEndDate() throws ParseException {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd HHmmss");
		return df2.parse(df1.format(new Date()) + " 235959");
	}

	public static Date getCurDateTimePattern(String timePattern) throws ParseException {
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return df2.parse(df1.format(new Date()) + " " + timePattern);
	}

}
