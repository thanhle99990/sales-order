package com.mwg.api.utils.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class TextResponseHandler implements ResponseHandler<String> {

  @Override
  public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
    AHttpClient.processStatusLine(response);
    HttpEntity entity = response.getEntity();
    return entity == null ? null : EntityUtils.toString(entity);
  }
}
