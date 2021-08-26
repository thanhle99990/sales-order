package com.mwg.api.configs;

public interface ILogWriter {
  //////////////////////////////////////////////
  public void error(String error, Exception ex);

  public void warn(String warn, Exception ex);

  public void debug(String debug);

  public void info(String info);

}
