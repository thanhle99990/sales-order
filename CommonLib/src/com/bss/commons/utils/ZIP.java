package com.bss.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZIP {
  private static final Charset charset = Charset.forName("UTF-8");
  private static final Encoder encoder = Base64.getEncoder();
  private static final Decoder decoder = Base64.getDecoder();

  public static byte[] zip(byte[] data) throws Exception {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      try (GZIPOutputStream zos = new GZIPOutputStream(baos)) {
        zos.write(data);
      }
      return baos.toByteArray();
    }
  }

  public static byte[] unZip(byte[] zip) throws Exception {
    try (GZIPInputStream zis = new GZIPInputStream(new ByteArrayInputStream(zip));) {
      return IOUtils.toByteArray(zis);
    }
  }

  public static String zip(String data) throws Exception {
    return encoder.encodeToString(zip(data.getBytes(charset)));
  }

  public static String unZip(String zip) throws Exception {
    return new String(unZip(decoder.decode(zip)), charset);
  }
}
