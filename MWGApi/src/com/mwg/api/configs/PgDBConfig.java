package com.mwg.api.configs;

import java.sql.Connection;

import com.mwg.api.ApplicationService;
import com.mwg.api.posclient.Const;

public class PgDBConfig {
	
	public static DBConnPool dbConn;
	
	public static DBConnPool dbConnSTB;
	
	public static DBConnPool getDbConn() throws Exception {
		if (dbConn == null) {
			return doPGConnPool();
		}
		return dbConn;
	}
	
	public static DBConnPool getDbConnSTB() throws Exception {
		if (dbConnSTB == null) {
			return doSTBPGConnPool();
		}
		return dbConnSTB;
	}
	
	private static synchronized DBConnPool doPGConnPool() throws Exception{
		if (dbConn == null) {
			DBConnConfig dbConnConfig = ApplicationService.getDBConnConfig(Const.CONN_CONFIG_POS);
			dbConn = new PGConnPool(dbConnConfig.getConnStr(),
									dbConnConfig.getUser(),
									dbConnConfig.getPass(),
									dbConnConfig.getMaxConn());
		}
		return dbConn;
	}
	
	private static synchronized DBConnPool doSTBPGConnPool() throws Exception{
		if (dbConnSTB == null) {
			DBConnConfig dbConnConfig = ApplicationService.getDBConnConfig(Const.CONN_CONFIG_POS);
			dbConnSTB = new PGConnPool(dbConnConfig.getConnStrSTB(),
									dbConnConfig.getUserSTB(),
									dbConnConfig.getPassSTB(),
									dbConnConfig.getMaxConnSTB());
		}
		return dbConnSTB;
	}
}
