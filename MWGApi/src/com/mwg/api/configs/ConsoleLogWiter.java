package com.mwg.api.configs;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mwg.api.utils.Utils;

public class ConsoleLogWiter implements ILogWriter {
  private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void error(String error, Exception ex) {
    if (ex != null) {
      System.err.println(String.format("[ERROR] %s: [%s] -> %s", df.format(new Date()), error, Utils.dumpException(ex)));
    } else {
      System.err.println(String.format("[ERROR] %s: %s", df.format(new Date()), error));
    }
  }

  @Override
  public void warn(String warn, Exception ex) {
    if (ex != null) {
      System.err.println(String.format("[WARN] %s: [%s] -> %s", df.format(new Date()), warn, Utils.dumpException(ex)));
    } else {
      System.err.println(String.format("[WARN] %s: %s", df.format(new Date()), warn));
    }
  }

  @Override
  public void debug(String debug) {
    System.out.println(String.format("[DEBUG] %s: %s", df.format(new Date()), debug));
  }

  @Override
  public void info(String info) {
    System.out.println(String.format("[INFO] %s: %s", df.format(new Date()), info));
  }

}
