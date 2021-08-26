package com.mwg.api.utils.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientPost extends AHttpClient {

	private HttpEntity entity = null;
	
	public HttpClientPost(String url, String username, String password) throws Exception {
		super(url, username, password);
	}

	public HttpClientPost(String url, String username, String password, int timeoutInSec) throws Exception {
		super(url, username, password,timeoutInSec);
	}

	public HttpClientPost(String url) throws Exception {
		this(url, null, null);
	}
	
	public HttpClientPost(String url,int timeoutInSec) throws Exception {
		this(url, null, null,timeoutInSec);
	}

	@Override
	public HttpResponse execute() throws Exception {
		HttpPost httpPost = new HttpPost(getUrl());
		if (entity != null) {
			httpPost.setEntity(entity);
		} else {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			getParams().forEach((name, value) -> {
				params.add(new BasicNameValuePair(name, value));
			});
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		}
		getHeaders().forEach(header -> {
			httpPost.addHeader(header);
		});
		return getClient().execute(httpPost);
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}

}