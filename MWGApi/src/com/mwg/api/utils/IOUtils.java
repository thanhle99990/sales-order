package com.mwg.api.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

  public static String toString(InputStream stream, String charset) throws Exception {
    return new String(toByteArray(stream), charset);
  }

  public static void copy(InputStream stream, OutputStream output) throws Exception {
    int len = 0;
    byte[] buffer = new byte[1024];
    while ((len = stream.read(buffer)) != -1) {
      if (len > 0) {
        output.write(buffer, 0, len);
      }
    }
  }

  public static byte[] toByteArray(InputStream stream) throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    IOUtils.copy(stream, output);
    return output.toByteArray();
  }

  public static int readInputStreamWithTimeout(InputStream is, byte[] buff, int timeoutMillis) throws IOException {
    int bufferOffset = 0;
    long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
    while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < buff.length) {
      int readLength = java.lang.Math.min(is.available(), buff.length - bufferOffset);
      int readResult = is.read(buff, bufferOffset, readLength);
      if (readResult == -1) {
        break;
      }
      bufferOffset += readResult;
    }
    return bufferOffset;
  }
}
