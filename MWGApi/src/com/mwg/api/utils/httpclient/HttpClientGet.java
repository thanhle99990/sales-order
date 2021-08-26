package com.mwg.api.utils.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

public class HttpClientGet extends AHttpClient {
	
	public HttpClientGet(String url, String username, String password,int timeoutInSec) throws Exception {
		super(url, username, password,timeoutInSec);
	}


	public HttpClientGet(String url, String username, String password) throws Exception {
		super(url, username, password);
	}

	public HttpClientGet(String url) throws Exception {
		this(url, null, null);
	}
	
	public HttpClientGet(String url,int timeoutInSecs) throws Exception {
		this(url, null, null,timeoutInSecs);
	}

	@Override
	public HttpResponse execute() throws Exception {
		StringBuilder query = new StringBuilder("?");
		getParams().forEach((name, value) -> {
			query.append(name + "=" + AHttpClient.encodeUrl(value) + "&");
		});
		HttpGet httpGet = new HttpGet(getUrl() + query.toString());
		getHeaders().forEach(header -> {
			httpGet.addHeader(header);
		});
		return getClient().execute(httpGet);
	}
}
