package com.bss.commons.logging;

import org.apache.log4j.Logger;

public class MLogFactory {
  public static MLogger getLogger(Class<?> clazz) {
    return new MLogger(Logger.getLogger(clazz));
  }

  public static class MLogger {
    private final Logger _LOG;

    private MLogger(Logger log) {
      _LOG = log;
    }

    public void INFO(String msg) {
      _LOG.info(msg);
    }

    public void INFO(String msg, Throwable t) {
      _LOG.info(msg, t);
    }

    public void WARN(String msg) {
      _LOG.warn(msg);
    }

    public void WARN(String msg, Throwable t) {
      _LOG.warn(msg, t);
    }

    public void ERROR(String msg) {
      _LOG.error(msg);
    }

    public void ERROR(String msg, Throwable t) {
      _LOG.error(msg, t);
    }
  }
}
