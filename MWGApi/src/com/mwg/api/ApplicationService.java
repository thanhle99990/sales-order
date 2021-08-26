package com.mwg.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mwg.api.configs.AuthUser;
import com.mwg.api.configs.ConsoleLogWiter;
import com.mwg.api.configs.DBConnConfig;
import com.mwg.api.configs.DBConnPool;
import com.mwg.api.configs.DbConfig;
import com.mwg.api.configs.ILogWriter;
import com.mwg.api.configs.Permission;
import com.mwg.api.handlers.APIHandler;

public class ApplicationService {

	private static ApplicationService ins = null;

	private Map<String, APIHandler<?, ?>> handlers = null;

	private Map<String, Permission> permissions = null;

	private Map<String, AuthUser> users = null;

	private DBConnPool dbConnPool = null;

	private DBConnPool repDbConnPool = null;

	private ILogWriter logWriter = null;

	private static String hostname = null;
	
	private static List<DBConnConfig> dbConfigs = null;

	public synchronized void startup() throws Exception {
		if (ins == null) {
			this.initServ();
			ins = this;
		}
	}

	public synchronized void shutdown() {
		ins = null;
		destroy();
	}

	private void initServ() throws Exception {
		if (logWriter == null) {
			logWriter = new ConsoleLogWiter();
		}
		if (permissions == null) {
			this.permissions = new HashMap<>();
		}
		if (handlers == null) {
			handlers = new HashMap<>();
		}
		if (users == null) {
			users = new HashMap<>();
		}
		Thread.sleep(2000L);
	}

	private void destroy() {
		unregisterJdbcDriver();
	}

	private void unregisterJdbcDriver() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
			}
		}
	}

	public DBConnPool getDbConnPool() {
		return this.dbConnPool;
	}

	public void setDbConnPool(DBConnPool dbConnPool) {
		this.dbConnPool = dbConnPool;
	}

	public DBConnPool getRepDbConnPool() {
		return this.repDbConnPool;
	}

	public void setRepDbConnPool(DBConnPool repDbConnPool) {
		this.repDbConnPool = repDbConnPool;
	}

	public ILogWriter getLogWriter() {
		return this.logWriter;
	}

	public void setLogWriter(ILogWriter logWriter) {
		this.logWriter = logWriter;
	}

	public Map<String, AuthUser> getUsers() {
		return this.users;
	}

	public void setUsers(Map<String, AuthUser> users) {
		this.users = users;
	}

	public Map<String, APIHandler<?, ?>> getHandlers() {
		return this.handlers;
	}

	public void setHandlers(Map<String, APIHandler<?, ?>> handlers) {
		this.handlers = handlers;
	}

	public Map<String, Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Map<String, Permission> permissions) {
		this.permissions = permissions;
	}

	public static ApplicationService ins() throws Exception {
		if (ins != null) {
			return ins;
		}
		throw new Exception("APPLICATION SERVICE IS NOT READY YET.");
	}

	public static String getHostname() {
		if (hostname == null) {
			try {
				return (hostname = InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException ex) {
				return (hostname = "UNKNOWN");
			}
		}
		return hostname;
	}
	
	public static void addDBConnConfig(DBConnConfig config) {
		if (dbConfigs == null) {
			dbConfigs = new ArrayList<DBConnConfig>();
		}
		dbConfigs.add(config);
	}
	
	public static List<DBConnConfig> getDBConnConfigs() {
		return dbConfigs;
	}
	
	public static DBConnConfig getDBConnConfig(int index) {
		DBConnConfig dBConnConfig = getDBConnConfigs().get(index);
		if(dBConnConfig == null) {
			addDbConfig();
			return getDBConnConfigs().get(index);
		}else {
			return dBConnConfig;
		}
	}

	private static void addDbConfig() {
		///////////LAY CONN TU CONFIG.CONF////////////////////
		try {
			DBConnConfig dBConnConfigData = DbConfig.getDBConnConfig();
			if(!dBConnConfigData.getConnStr().isEmpty() && !dBConnConfigData.getConnStr().equals("")) {
				ApplicationService.addDBConnConfig(dBConnConfigData);
			}
		} catch (Exception e) {
		}
		////////////////////////////////////////////////////////
	}
}
