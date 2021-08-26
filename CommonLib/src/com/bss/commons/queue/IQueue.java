package com.bss.commons.queue;

import java.util.List;

public interface IQueue<T> {

  public boolean put(T obj) throws Exception;

  public boolean put(List<T> objs) throws Exception;

  public List<T> get(int maxLen) throws Exception;

  public List<T> poll(int maxLen) throws Exception;

}
