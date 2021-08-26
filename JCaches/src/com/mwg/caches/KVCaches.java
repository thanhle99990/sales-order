package com.mwg.caches;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;

import com.mwg.kvclient.KVClient;
import com.mwg.kvclient.KVRequest;
import com.mwg.kvclient.KVResponse;

public class KVCaches<T extends Serializable> extends ACaches<T> {

  private final KVClient kvClient;
  private final String user;
  private final String pass;
  private final String app;

  public KVCaches(String url, String app, String user, String pass) {
    this.kvClient = new KVClient(url);
    this.app = app;
    this.user = user;
    this.pass = pass;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(String key) throws Exception {
    KVResponse response = kvClient.cacheService(KVRequest.getRequest(app, key), user, pass);
    if (response.getErrorCode() == 0) {
      return (T) CUtils.fromStringData(response.getValue());
    }
    if (response.getErrorCode() == -1) {
      return null;
    }
    throw new Exception(response.getErrorMessage());
  }

  @Override
  public void remove(String key) throws Exception {
    KVResponse response = kvClient.cacheService(KVRequest.remRequest(app, key), user, pass);
    if (response.getErrorCode() < -1) {
      throw new Exception(response.getErrorMessage());
    }
  }

  @Override
  public void put(String key, T value, Duration lifetime) throws Exception {
    KVRequest request = KVRequest.setRequest(app, key);
    request.setValue(CUtils.toStringData(value));
    request.setLifeTime(lifetime.toMillis());
    KVResponse response = kvClient.cacheService(request, user, pass);
    if (response.getErrorCode() < 0) {
      throw new Exception(response.getErrorMessage());
    }
  }

  @Override
  public Collection<String> keys() throws Exception {
    throw new RuntimeException("NOT SUPPORT OPERATION !");
  }

  @Override
  public void clear() {
    throw new RuntimeException("NOT SUPPORT OPERATION !");
  }

  @Override
  public void close() {
  }

}
