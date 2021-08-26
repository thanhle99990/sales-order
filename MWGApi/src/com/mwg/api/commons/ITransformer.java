package com.mwg.api.commons;

public interface ITransformer<T, V> {
	public V trans(T t);
}
