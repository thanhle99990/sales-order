package com.mwg.api.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {
  public static String dumpException(Exception ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    return sw.toString();
  }
}
