package com.mwg.kvclient;

import java.io.Serializable;

public class KVRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String action = null;

	private String app = null;

	private String key = null;

	private String value = null;

	private Long version = null;

	private String extraInfo = null;

	private Long lifeTime = null;

	private String options = null;

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getExtraInfo() {
		return this.extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public Long getLifeTime() {
		return this.lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	
	public static KVRequest getRequest(String app,String key) {
		 KVRequest request = new KVRequest();
		 request.setApp(app);
		 request.setKey(key);
		 request.setAction("GET");
		 return request;
	}
	
	public static KVRequest remRequest(String app,String key) {
		 KVRequest request = new KVRequest();
		 request.setApp(app);
		 request.setKey(key);
		 request.setAction("REM");
		 return request;
	}
	
	public static KVRequest setRequest(String app,String key) {
		 KVRequest request = new KVRequest();
		 request.setApp(app);
		 request.setKey(key);
		 request.setAction("SET");
		 return request;
	}

}
