package com.mwg.caches;

import java.time.Duration;

public interface IInitCache<T> {
  public T value() throws Exception;

  public Duration lifetime();
}
