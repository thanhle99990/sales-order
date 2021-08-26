package com.mwg.caches;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashCaches<T> extends ACaches<T> {

  private static final int defaultSize = 10240;
  private final IKeyTransformer keyTransfomer;
  private final Map<String, CacheObject<T>> map;

  public HashCaches(IKeyTransformer keyTransfomer, int size) {
    this.keyTransfomer = keyTransfomer;
    this.map = new ConcurrentHashMap<>(new LRUHashMap<String, CacheObject<T>>(size));
  }

  public HashCaches() {
    this((key -> key), defaultSize);
  }

  @Override
  public T get(String key) throws Exception {
    CacheObject<T> obj = map.get(keyTransfomer.transform(key));
    return (obj != null && !obj.isExpired()) ? obj.getValue() : null;
  }

  @Override
  public void remove(String key) throws Exception {
    synchronized (map) {
      map.remove(keyTransfomer.transform(key));
    }
  }

  @Override
  public void put(String key, T value, Duration lifetime) throws Exception {
    if (key != null && value != null) {
      synchronized (map) {
        map.put(keyTransfomer.transform(key), new CacheObject<T>(value, lifetime.toMillis()));
      }
    }
  }

  public long size() {
    return map.size();
  }

  public void gc() {
    synchronized (map) {
      map.keySet().forEach(key -> {
        if (map.get(key).isExpired()) {
          map.remove(key);
        }
      });
    }
  }

  @Override
  public void clear() {
    synchronized (map) {
      map.clear();
    }
  }

  static class CacheObject<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private long timestamp = System.currentTimeMillis();
    private final long expireInMillis;
    private final T value;

    public CacheObject(T value, long expireInMillis) {
      this.value = value;
      this.expireInMillis = expireInMillis;
    }

    public T getValue() {
      return this.value;
    }

    public boolean isExpired() {
      return (System.currentTimeMillis() - timestamp) > expireInMillis;
    }
  }

  @Override
  public void close() {
    this.clear();
  }
 
  @Override
  public Collection<String> keys() throws Exception {
    return Collections.unmodifiableCollection(map.keySet());
  }

}
