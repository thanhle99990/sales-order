package com.mwg.api.posclient.utilities.nearexpire;

import com.mwg.api.commons.AVP;

public class ApplyType extends AVP<Integer> {

	private static final long serialVersionUID = 1L;

	public ApplyType(String attr, Integer value) {
		super(attr, value);
	}

	@Override
	public String toString() {
		return this.getAttr();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && (obj instanceof ApplyType) && getAttr().equals(((ApplyType) obj).getAttr());
	}
	
}
