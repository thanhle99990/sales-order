package com.mwg.scale.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Utils {
  public static Socket createSocket(String ip, int port, int timeout) throws Exception {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(ip, port), timeout);
    return socket;
  }

  public static void copyStream(InputStream stream, OutputStream output) throws Exception {
    byte[] buffer = new byte[10240];
    int len = 0;
    while ((len = stream.read(buffer)) != -1) {
      if (len > 0) {
        output.write(buffer, 0, len);
      }
    }
  }

  public static int readInputStreamWithTimeout(InputStream is, byte[] buff, int timeoutMillis) throws IOException {
    int bufferOffset = 0;
    long maxTimeMillis = System.currentTimeMillis() + timeoutMillis;
    while (System.currentTimeMillis() < maxTimeMillis && bufferOffset < buff.length) {
      int readLength = Math.min(is.available(), buff.length - bufferOffset);
      int readResult = is.read(buff, bufferOffset, readLength);
      if (readResult == -1) {
        break;
      }
      bufferOffset += readResult;
    }
    return bufferOffset;
  }

  public static String streamToString(InputStream stream, String charset) throws Exception {
    return new String(streamToByteArray(stream), charset);
  }

  public static byte[] streamToByteArray(InputStream stream) throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[10240];
    int len = 0;
    while ((len = stream.read(buffer)) != -1) {
      if (len > 0) {
        output.write(buffer, 0, len);
      }
    }
    return output.toByteArray();
  }

  public static byte[] hexString2ByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  public static byte[] Number2BCD(long num) {
    int digits = 0;
    long temp = num;
    while (temp != 0) {
      digits++;
      temp /= 10;
    }
    int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;
    byte bcd[] = new byte[byteLen];
    for (int i = 0; i < digits; i++) {
      byte tmp = (byte) (num % 10);
      if (i % 2 == 0) {
        bcd[i / 2] = tmp;
      } else {
        bcd[i / 2] |= (byte) (tmp << 4);
      }
      num /= 10;
    }
    for (int i = 0; i < byteLen / 2; i++) {
      byte tmp = bcd[i];
      bcd[i] = bcd[byteLen - i - 1];
      bcd[byteLen - i - 1] = tmp;
    }
    return bcd;
  }

  public static <T> T Coalesce(T v, T d) {
    return (v == null) ? d : v;
  }
}
