package com.mwg.scale;

public interface IScale {

  public static int scaleTimeoutMs = 3000;

  public long getValueInGam() throws Exception;

  public void setConf(String conf);

  public String getConf();

}
