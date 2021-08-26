package com.bss.commons.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CQueue<T> implements IQueue<T> {

  private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();

  private final int maxSize;

  public CQueue(int maxSize) {
    this.maxSize = maxSize;
  }

  public CQueue() {
    this(100000);
  }

  @Override
  public synchronized boolean put(T obj) throws Exception {
    if (queue.size() < maxSize) {
      return queue.add(obj);
    }
    return false;
  }

  @Override
  public synchronized boolean put(List<T> objs) throws Exception {
    if (queue.size() + objs.size() <= maxSize) {
      return queue.addAll(objs);
    }
    return false;
  }

  @Override
  public synchronized List<T> get(int maxLen) throws Exception {
    List<T> result = new ArrayList<>();
    return result;
  }

  @Override
  public synchronized List<T> poll(int maxLen) throws Exception {
    List<T> result = new ArrayList<>();
    return result;
  }
}

class QueueObj<T> {
  public QueueObj<T> prev = null;
  public QueueObj<T> next = null;
  public T data = null;
}
