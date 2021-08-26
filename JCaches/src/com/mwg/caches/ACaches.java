package com.mwg.caches;

public abstract class ACaches<T> implements ICaches<T> {
  @Override
  public T get(String key, IInitCache<T> init) throws Exception {
    T value = this.get(key);
    /////////////////////////////////////////////////////////////
    if (value == null) {
      this.put(key, (value = init.value()), init.lifetime());
    }
    /////////////////////////////////////////////////////////////
    return value;
    /////////////////////////////////////////////////////////////
  }
  @Override
  public boolean exist(String key) throws Exception {
    return this.get(key) != null;
  }
}
