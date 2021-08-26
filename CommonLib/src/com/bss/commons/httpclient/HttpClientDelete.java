package com.bss.commons.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;

public class HttpClientDelete extends AHttpClient {
	
	public HttpClientDelete(String url, String username, String password,int timeoutInSec) throws Exception {
		super(url, username, password,timeoutInSec);
	}


	public HttpClientDelete(String url, String username, String password) throws Exception {
		super(url, username, password);
	}

	public HttpClientDelete(String url) throws Exception {
		this(url, null, null);
	}
	
	public HttpClientDelete(String url,int timeoutInSecs) throws Exception {
		this(url, null, null,timeoutInSecs);
	}

	@Override
	public HttpResponse execute() throws Exception {
		StringBuilder query = new StringBuilder("?");
		getParams().forEach((name, value) -> {
			query.append(name + "=" + AHttpClient.encodeUrl(value) + "&");
		});
		HttpDelete httpDel = new HttpDelete(getUrl() + query.toString());
		getHeaders().forEach(header -> {
			httpDel.addHeader(header);
		});
		return getClient().execute(httpDel);
	}
}
