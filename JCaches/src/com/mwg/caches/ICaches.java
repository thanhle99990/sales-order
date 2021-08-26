package com.mwg.caches;

import java.time.Duration;
import java.util.Collection;

public interface ICaches<T> {

  public T get(String key) throws Exception;

  public T get(String key, IInitCache<T> init) throws Exception;

  public void remove(String key) throws Exception;

  public void put(String key, T value, Duration lifetime) throws Exception;

  public boolean exist(String key) throws Exception;

  public Collection<String> keys() throws Exception;

  public void close();

  public void clear();
}
