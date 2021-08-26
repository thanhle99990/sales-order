package com.mwg.api.posclient.timechecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.mwg.api.commons.PDate;

public class SysTimeChecker {
	private static final String fileName = System.getProperty("user.home") + File.separator + "LastNTP.dat";
	private static final List<String> timeServers = new ArrayList<>();
	private static final Duration maxOffline = Duration.ofHours(24);
	private static final Duration maxTimeDelta = Duration.ofMinutes(1);
	private static final Duration checkInterval = Duration.ofSeconds(30);
	private static final SysTimeChecker ins = new SysTimeChecker();
	private Exception lastError = null;
	private Long lastCheck = null;
	private Long timeDelta = null;

	static {
		timeServers.add("pool.ntp.org");
		timeServers.add("time.windows.com");
		timeServers.add("0.vn.pool.ntp.org");
		timeServers.add("0.asia.pool.ntp.org");
		timeServers.add("1.asia.pool.ntp.org");
	}

	public static SysTimeChecker Ins() {
		return ins;
	}

	private SysTimeChecker() {
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					lastError = null;
					timeDelta = timeDelta();
					lastCheck = System.currentTimeMillis();
					Thread.sleep(checkInterval.toMillis());
				} catch (Exception ex) {
					lastError = ex;
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public boolean allowOutputVoucher() throws Exception {
		if (lastCheck != null && System.currentTimeMillis() - lastCheck < (Duration.ofMinutes(5).toMillis())) {
			return (timeDelta < maxTimeDelta.toMillis());
		}
		return checkValidDateOffLine();
	}

	public boolean checkTimeWhenStart() throws Exception {
		return this.timeDelta() < maxTimeDelta.toMillis();
	}

	private Long timeDelta() throws Exception {
		Exception error = null;
		for (String timeServer : timeServers) {
			try {
				Long ntpTime = PDate.getTimestampFromNTP(timeServer);
				Long localTime = System.currentTimeMillis();
				this.saveLastNTPTime(ntpTime);
				return Math.abs(localTime - ntpTime);
			} catch (Exception ex) {
				error = ex;
			}
		}
		throw error;
	}

	private boolean checkValidDateOffLine() throws Exception {
		long after = Date.valueOf("2025-01-01").getTime();
		long before = Date.valueOf("2019-10-01").getTime();
		return (System.currentTimeMillis() > before && System.currentTimeMillis() < after) && checkNTPOffline();
	}

	private void saveLastNTPTime(Long time) throws Exception {
		synchronized (fileName) {
			if (time != null) {
				try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(fileName, false))) {
					objOut.writeLong(time);
				}
			}
		}
	}

	private boolean checkNTPOffline() throws Exception {
		synchronized (fileName) {
			if (new File(fileName).exists()) {
				try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(fileName))) {
					long localTime = System.currentTimeMillis(), lastNtpTime = objIn.readLong();
					return (lastNtpTime <= localTime) && (lastNtpTime >= localTime - maxOffline.toMillis());
				}
			}
			return true;
		}
	}

	public Exception getLastError() {
		return this.lastError;
	}

	public Long getLastCheck() {
		return this.lastCheck;
	}

	public Long getTimeDelta() {
		return this.timeDelta;
	}
}
