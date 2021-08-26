package com.mwg.api.utils.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class FileDownloadResponseHandler implements ResponseHandler<File> {
  private final DownloadListener listener;
  private File targetFile = null;

  public FileDownloadResponseHandler(File targetFile, DownloadListener listener) {
    this.listener = listener;
  }

  public FileDownloadResponseHandler(DownloadListener listener) {
    this(null, listener);
  }

  @Override
  public File handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
    AHttpClient.processStatusLine(response);
    long lenght = response.getEntity().getContentLength();
    File outputFile = (targetFile == null) ? File.createTempFile("download", ".bin") : targetFile;
    byte[] buffer = new byte[1024];
    long received = 0;
    int len = 0;
    try (OutputStream out = new FileOutputStream(outputFile); InputStream in = response.getEntity().getContent()) {
      while ((len = in.read(buffer)) >= 0) {
        out.write(buffer, 0, len);
        received += len;
        if (listener != null) {
          listener.onDownload(received, lenght);
        }
      }
    }
    return outputFile;
  }
}