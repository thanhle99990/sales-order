package com.mwg.api.commons;

import java.net.InetAddress;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class PDate {

	public static Date NOW() {
		return new Date();
	}

	public static Date beginOfDate(Date date) {
		if (date != null) {
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			try {
				return df2.parse(df1.format(date) + " 00:00:00.000");
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public static Date endOfDate(Date date) {
		if (date != null) {
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			try {
				return df2.parse(df1.format(date) + " 23:59:59.999");
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	public static String formatDate(Date date, String pattern) {
		if (date != null) {
			return new SimpleDateFormat(pattern).format(date);
		}
		return null;
	}

	public static TimeZone getSystemTimeZone() {
		return TimeZone.getDefault();
	}

	public static boolean systemTimezoneIs(TimeZone timezone) {
		long now = System.currentTimeMillis();
		return timezone.getOffset(now) == getSystemTimeZone().getOffset(now);
	}

	public static Long getTimestampFromNTP(String timeServer) throws Exception {
		NTPUDPClient timeClient = new NTPUDPClient();
		timeClient.setDefaultTimeout(3000);
		TimeInfo timeInfo = timeClient.getTime(InetAddress.getByName(timeServer));
		timeInfo.computeDetails();
		return timeInfo.getMessage().getReceiveTimeStamp().getTime();
	}

	public static Date max(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.getTime() > d2.getTime() ? d1 : d2;
	}

	public static Date min(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.getTime() < d2.getTime() ? d1 : d2;
	}

	public static boolean between(Date from, Date to, Date d) {
		if (from == null && to == null) {
			return false;
		}
		if (from == null) {
			return d.getTime() <= to.getTime();
		}
		if (to == null) {
			return d.getTime() >= from.getTime();
		}
		return d.getTime() <= to.getTime() && d.getTime() >= from.getTime();
	}

	public static boolean between(Time from, Time to, Time t) {
		Time time = timeOnly(t);
		if (from == null && to == null) {
			return false;
		}
		if (from == null) {
			return time.getTime() <= timeOnly(to).getTime();
		}
		if (to == null) {
			return time.getTime() >= timeOnly(from).getTime();
		}
		return time.getTime() <= timeOnly(to).getTime() & time.getTime() >= timeOnly(from).getTime();
	}

	public static Time timeOnly(Time time) {
		return Time.valueOf(PDate.formatDate(time, "HH:mm:ss"));
	}

}
