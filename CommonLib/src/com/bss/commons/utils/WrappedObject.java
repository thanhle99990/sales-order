
package com.bss.commons.utils;

public class WrappedObject<T> {

    private T value = null;

    public WrappedObject() {
    }

    public WrappedObject(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

 
    public void setValue(T value) {
        this.value = value;
    }

}
