package com.mwg.kvclient;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.bss.commons.httpclient.HttpClientPost;
import com.bss.commons.httpclient.ObjectResponseHandler;
import com.bss.commons.utils.JsonUtils;

public class KVClient {

  private static final int timeoutinSecs = 10;

  private final String url;

  public static void main(String[] args) {
    System.out.println("KVClient");
  }

  public KVClient(String url) {
    this.url = url;
  }

  public KVResponse getKey(String app, String key, String user, String pass) throws Exception {
    return cacheService(KVRequest.getRequest(app, key), user, pass);
  }

  public KVResponse remKey(String app, String key, String user, String pass) throws Exception {
    return cacheService(KVRequest.remRequest(app, key), user, pass);
  }

  public KVResponse setKey(String app, String key, String user, String pass, String value, Long version, Long lifeTimeInMills) throws Exception {
    KVRequest request = KVRequest.setRequest(app, key);
    request.setValue(value);
    request.setVersion(version);
    request.setLifeTime(lifeTimeInMills);
    return cacheService(request, user, pass);
  }

  public KVResponse cacheService(KVRequest request, String user, String pass) throws Exception {
    try (HttpClientPost httpPost = new HttpClientPost(url + "/cacheService", user, pass, timeoutinSecs)) {
      httpPost.addHeader(new BasicHeader("User-Agent", "KVClient"));
      httpPost.setEntity(new StringEntity(JsonUtils.gson.toJson(request), "UTF-8"));
      httpPost.addHeader(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
      return new ObjectResponseHandler<KVResponse>(KVResponse.class).handleResponse(httpPost.execute());
    }
  }

}
