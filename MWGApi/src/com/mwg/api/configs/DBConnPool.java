package com.mwg.api.configs;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;

public abstract class DBConnPool {

  private static final int maxWaitInMills = 5000;
  private static final int queryTimeoutInSeconds = 30 * 60;
  private static final int maxOpenPreparedStatements = 512;
  private static final int validationQueryTimeoutInSeconds = 5;

  private final BasicDataSource dataSource = new BasicDataSource();

  public DBConnPool(Class<?> driverClass, String connStr, String user, String pass, int maxConn) throws Exception {
    maxConn = Math.max(1, maxConn);
    ////////////////////////////////////////////////////////////////////////
    dataSource.setDriverClassName(driverClass.getName());
    ////////////////////////////////////////////////////////////////////////
    dataSource.setUsername(user);
    dataSource.setPassword(pass);
    dataSource.setUrl(connStr);
    dataSource.setMinIdle(0);
    dataSource.setMaxTotal(maxConn);
    dataSource.setRollbackOnReturn(true);
    dataSource.setDefaultAutoCommit(false);
    dataSource.setMaxIdle(Math.min(maxConn, 8));
    dataSource.setInitialSize(Math.min(maxConn, 8));
    ////////////////////////////////////////////////////////////////////////
    dataSource.setMaxWaitMillis(maxWaitInMills);
    dataSource.setDefaultQueryTimeout(queryTimeoutInSeconds);
    dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
    dataSource.setValidationQueryTimeout(validationQueryTimeoutInSeconds);
    ////////////////////////////////////////////////////////////////////////
    dataSource.getConnection().close();
    ////////////////////////////////////////////////////////////////////////
  }

  public Connection getConnection() throws Exception {
    Connection conn = dataSource.getConnection();
    conn.setAutoCommit(false);
    return conn;
  }

  public Connection getAutoCommitConnection() throws Exception {
    Connection conn = dataSource.getConnection();
    conn.setAutoCommit(true);
    return conn;
  }

  public void close() throws Exception {
    dataSource.close();
  };
}
