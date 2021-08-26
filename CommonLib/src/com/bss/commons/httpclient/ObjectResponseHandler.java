package com.bss.commons.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class ObjectResponseHandler<T> implements ResponseHandler<T> {
  private final Class<T> clz;

  public ObjectResponseHandler(Class<T> clz) {
    super();
    this.clz = clz;
  }

  @Override
  public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
    AHttpClient.processStatusLine(response);
    HttpEntity entity = response.getEntity();
    return entity == null ? null : new Gson().fromJson(EntityUtils.toString(entity), clz);
  }
}
