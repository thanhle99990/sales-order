package com.bss.commons.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientPut extends AHttpClient {

	private HttpEntity entity = null;
	
	public HttpClientPut(String url, String username, String password) throws Exception {
		super(url, username, password);
	}

	public HttpClientPut(String url, String username, String password, int timeoutInSec) throws Exception {
		super(url, username, password,timeoutInSec);
	}

	public HttpClientPut(String url) throws Exception {
		this(url, null, null);
	}
	
	public HttpClientPut(String url,int timeoutInSec) throws Exception {
		this(url, null, null,timeoutInSec);
	}

	@Override
	public HttpResponse execute() throws Exception {
		HttpPut httpPut = new HttpPut(getUrl());
		if (entity != null) {
			httpPut.setEntity(entity);
		} else {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			getParams().forEach((name, value) -> {
				params.add(new BasicNameValuePair(name, value));
			});
			httpPut.setEntity(new UrlEncodedFormEntity(params));
		}
		getHeaders().forEach(header -> {
			httpPut.addHeader(header);
		});
		return getClient().execute(httpPut);
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}

}