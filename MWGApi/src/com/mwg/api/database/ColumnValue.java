package com.mwg.api.database;

import com.mwg.api.commons.AVP;

public class ColumnValue extends AVP<Object> {

	private static final long serialVersionUID = 1L;

	public ColumnValue(String column, Object value) {
		super(column, value);
	}

	public String getColumn() {
		return this.getAttr();
	}
	
}
