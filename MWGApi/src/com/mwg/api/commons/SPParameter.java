package com.mwg.api.commons;

public class SPParameter<T> {

	Class<?> clazz;

	T value;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public SPParameter(Class<?> clazz, T value) throws Exception {
		super();
		this.clazz = clazz;
		this.value = value;

		if (!clazz.isInstance(value)) {
			throw new Exception("Clazz and Value are not same Type");
		}

		if (clazz == Integer.class) {
			System.out.println("Int - " + this.value);
		} else if (clazz == Long.class) {
			System.out.println("Long - " + this.value);
		} else if (clazz == Double.class) {
			System.out.println("Double - " + this.value);
		} else if (clazz == String.class) {
			System.out.println("String - " + this.value);
		}
	}

}
