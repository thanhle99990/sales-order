package com.mwg.api.posclient.webservice;

public class ERPWsExp extends Exception {
	
	private final String errorMsg;

	private final String errorMsgDetail;

	private static final long serialVersionUID = 1L;

	public ERPWsExp(String errorMsg, String errorMsgDetail) {
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.errorMsgDetail = errorMsgDetail;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public String getErrorMsgDetail() {
		return this.errorMsgDetail;
	}
}
