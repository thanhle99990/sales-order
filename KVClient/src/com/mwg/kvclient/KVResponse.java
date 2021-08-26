package com.mwg.kvclient;

import java.io.Serializable;

public class KVResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer errorCode = null;

	private String errorMessage = null;

	private String errorMessageDetail = null;

	private String app = null;

	private String key = null;

	private String value = null;

	private Long version = null;

	private String extraInfo = null;

	private String hashedKey = null;

	private Long lifeTime = null;

	private Long createdTime = null;

	private String creatorUser = null;

	private Long processTime = null;

	private Integer cacheSize = null;

	private String action = null;

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessageDetail() {
		return this.errorMessageDetail;
	}

	public void setErrorMessageDetail(String errorMessageDetail) {
		this.errorMessageDetail = errorMessageDetail;
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

	public String getHashedKey() {
		return this.hashedKey;
	}

	public void setHashedKey(String hashedKey) {
		this.hashedKey = hashedKey;
	}

	public Long getLifeTime() {
		return this.lifeTime;
	}

	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Long getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatorUser() {
		return this.creatorUser;
	}

	public void setCreatorUser(String creatorUser) {
		this.creatorUser = creatorUser;
	}

	public Long getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public Integer getCacheSize() {
		return this.cacheSize;
	}

	public void setCacheSize(Integer cacheSize) {
		this.cacheSize = cacheSize;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}