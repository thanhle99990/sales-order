package com.bss.commons.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler {
  private final InputStream inputStream;
  private final Consumer<String> consumer;
  private boolean isEOF = false;

  private StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
    this.inputStream = inputStream;
    this.consumer = consumer;
  }

  public static StreamGobbler create(InputStream inputStream, Consumer<String> consumer) {
    return new StreamGobbler(inputStream, consumer);
  }

  private synchronized void doBackground() {
    if (inputStream != null) {
      synchronized (inputStream) {
        String line = null;
        try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          while (true) {
            try {
              line = reader.readLine();
            } catch (Exception e) {
              break;
            }
            if (line != null) {
              consume(line);
            } else {
              break;
            }
          }
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        } finally {
          isEOF = true;
        }
      }
    }
  }

  private void consume(String line) throws Exception {
    if (consumer != null) {
      consumer.accept(line);
    }
  }

  public boolean isEOF() {
    return isEOF;
  }

  public StreamGobbler start() {
    new Thread(() -> doBackground()).start();
    return this;
  }
}
