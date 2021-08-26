package com.mwg.api.commons;

import java.io.Serializable;

public class AVP<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String attr;

	private final T value;

	public AVP(String attr, T value) {
		this.attr = attr;
		this.value = value;
		if (this.attr == null) {
			throw new RuntimeException("Attribute must be not null.");
		}
	}

	public String getAttr() {
		return attr;
	}

	public T getValue() {
		return value;
	}
	
}
