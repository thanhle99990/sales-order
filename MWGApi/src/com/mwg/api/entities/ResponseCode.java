package com.mwg.api.entities;

import com.mwg.api.ApplicationService;

public class ResponseCode {
  private final int code;
  private final String msg;
  private final String msgDetail;
  private Long exeInMills = null;
  private String version= null;
  private String function= null;
  private String username= null;
  private final String hostname = ApplicationService.getHostname();

  public ResponseCode(int code, String msg, String msgDetail) {
	if(msg != null)
	  msg = msg.replace("java.lang.Exception: ", "").replace("java.lang.RuntimeException: ", "");
    this.msg = msg;
    this.code = code;
    this.msgDetail = msgDetail;
  }

  public int getCode() {
    return this.code;
  }

  public String getMsg() {
    return this.msg;
  }

  public String getMsgDetail() {
    return this.msgDetail;
  }

  public String getHostname() {
    return this.hostname;
  }

  public Long getExeInMills() {
    return this.exeInMills;
  }

  public ResponseCode setExeInMills(Long exeInMills) {
    this.exeInMills = exeInMills;
    return this;
  }

	public String getVersion() {
		return version;
	}

	public ResponseCode setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getFunction() {
		return function;
	}

	public ResponseCode setFunction(String function) {
		this.function = function;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public ResponseCode setUsername(String username) {
		this.username = username;
		return this;
	}
}
