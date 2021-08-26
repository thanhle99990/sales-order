package com.mwg.api.commons;

public interface IConverter<I, O> {
	public O convert(I in) throws Exception;
}
