package com.mwg.api.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public final class TListMap<K, V> {

	private final List<K> keyList = new ArrayList<K>();

	private final Map<K, V> objMap = new LinkedHashMap<>();

	public void addToFirst(V obj, K key) {
		remove(key);
		synchronized (keyList) {
			keyList.add(0, key);
			objMap.put(key, obj);
		}
	}

	public void addToLast(V obj, K key) {
		remove(key);
		synchronized (keyList) {
			keyList.add(key);
			objMap.put(key, obj);
		}
	}

	public int size() {
		return keyList.size();
	}

	public V remove(K key) {
		synchronized (keyList) {
			keyList.remove(key);
			return objMap.remove(key);
		}
	}

	public int index(K key) {
		return keyList.indexOf(key);
	}

	public V get(K key) {
		return objMap.get(key);
	}

	public V get(int idx) {
		return (idx >= 0 && idx < size()) ? objMap.get(keyList.get(idx)) : null;
	}

	public void clear() {
		synchronized (keyList) {
			keyList.clear();
			objMap.clear();
		}
	}

	public Collection<V> getCollection() {
		return Collections.unmodifiableCollection(PCollectionUtils.selectAndTransform(keyList, key -> true, key -> objMap.get(key)));
	}
	
}
