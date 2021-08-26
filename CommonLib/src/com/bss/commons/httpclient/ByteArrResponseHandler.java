package com.bss.commons.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class ByteArrResponseHandler implements ResponseHandler<byte[]> {

  @Override
  public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
    AHttpClient.processStatusLine(response);
    HttpEntity entity = response.getEntity();
    return entity != null ? EntityUtils.toByteArray(entity) : null;
  }
}
