package com.mwg.caches;

public interface IValueTransformer<T> {
     public T fromData(byte[] data) throws Exception;
     public byte[] toData(T value) throws Exception;
}
