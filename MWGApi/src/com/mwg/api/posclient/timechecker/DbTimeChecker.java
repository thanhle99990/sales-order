package com.mwg.api.posclient.timechecker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;

import com.mwg.api.configs.PgDBConfig;

public class DbTimeChecker {
	private Long timeDelta = null;
	private Long lastCheck = null;
	private Exception lastError = null;

	private static DbTimeChecker ins = new DbTimeChecker();

	private static final Duration checkInterval = Duration.ofSeconds(30);

	private static final Duration maxTimeDelta = Duration.ofMinutes(1);

	private DbTimeChecker() {
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					timeDelta = timeDelta();
					lastCheck = System.currentTimeMillis();
				} catch (Exception ex) {
					lastError = ex;
				}
        try {
          Thread.sleep(checkInterval.toMillis());
        } catch (InterruptedException ex) {
          break;
        }
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static DbTimeChecker Ins() {
		return ins;
	}

	private long timeDelta() throws Exception {
		String sql = "select now()::timestamp";
		try (Connection conn = PgDBConfig.getDbConn().getConnection(); PreparedStatement pStmt = conn.prepareStatement(sql)) {
			ResultSet resultSet = pStmt.executeQuery();
			if (resultSet.next()) {
				return Math.abs(System.currentTimeMillis() - resultSet.getTimestamp(1).getTime());
			}
		}
		throw new Exception("Could not get time from database.");
	}

	public boolean allowOutputVoucher() throws Exception {
		if (lastCheck != null && System.currentTimeMillis() - lastCheck < (Duration.ofMinutes(5).toMillis())) {
			return (timeDelta < maxTimeDelta.toMillis());
		}
		throw new Exception("Could not get time from database.");
	}

	public boolean checkTimeWhenStart() throws Exception {
		return this.timeDelta() < maxTimeDelta.toMillis();
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
