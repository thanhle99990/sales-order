package com.mwg.api.configs;

public class PGConnPool extends DBConnPool {

  public PGConnPool(String connStr, String user, String pass, int maxConn) throws Exception {
    super(org.postgresql.Driver.class, connStr, user, pass, maxConn);
  }

}
