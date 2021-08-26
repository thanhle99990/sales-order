package com.bss.commons.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public abstract class AHttpClient implements AutoCloseable {
	
	private static final Encoder base64encoder = Base64.getEncoder();

	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String url;
	private final CloseableHttpClient client;
	private final Map<String, String> params = new HashMap<>();
	private final List<Header> headers = new ArrayList<>();

	public AHttpClient(String url) {
		this(url, null, null);
	}

	public AHttpClient(String url, String username, String password) {
		this(url, username, password, 30);
	}

	public AHttpClient(String url, String username, String password, int timeoutInSecs) {
		this.url = url;
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setSSLContext(getSSLContext());
		clientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
		if(username!=null && password!=null) {
			clientBuilder.setDefaultCredentialsProvider(new DefaultHttpAuthenProvicer(username, password));
			String authen = base64encoder.encodeToString(String.format("%s:%s", username,password).getBytes());
			this.addHeader(new BasicHeader("authorization", String.format("Basic %s",authen))); 
		}
		this.client = clientBuilder.setDefaultRequestConfig(getConfig(timeoutInSecs)).build();
	}

	private RequestConfig getConfig(int timeoutInSecs) {
		int socketTimeout = Math.max(30, timeoutInSecs) * 1000, connTimeout = Math.min(90000, socketTimeout) , connRequestTimeout = connTimeout;
		return RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setConnectionRequestTimeout(connRequestTimeout).setConnectTimeout(connTimeout).setSocketTimeout(socketTimeout).build();
	}

	private SSLContext getSSLContext() {
		try {
			return new SSLContextBuilder().loadTrustMaterial(null, (x509CertChain, authType) -> true).build();
		} catch (Exception e) {
			return null;
		}
	}

	class DefaultHttpAuthenProvicer extends BasicCredentialsProvider {
		public DefaultHttpAuthenProvicer(String user, String pass) {
			this.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, pass));
		}
	}

	protected abstract HttpResponse execute() throws Exception;

	public <T> T execute(Class<T> clz) throws Exception {
		return new ObjectResponseHandler<T>(clz).handleResponse(execute());
	}

	public String getStringResponse() throws Exception {
		return new TextResponseHandler().handleResponse(execute());
	}

	public byte[] getDataResponse() throws Exception {
		return new ByteArrResponseHandler().handleResponse(execute());
	}

	public File getFileResponse(DownloadListener listener) throws Exception {
		return new FileDownloadResponseHandler(listener).handleResponse(execute());
	}

	public String getUrl() {
		return this.url;
	}

	public HttpClient getClient() {
		return this.client;
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public AHttpClient addParam(String name, String value) {
		this.params.put(name, value);
		return this;
	}

	public AHttpClient addParams(Map<String, String> params) {
		this.params.putAll(params);
		return this;
	}

	public AHttpClient addHeader(Header header) {
		headers.add(header);
		return this;
	}

	public static String encodeUrl(String value) {
		try {
			return URLEncoder.encode(value, Charset.forName("UTF-8").name());
		} catch (UnsupportedEncodingException e) {
			try {
				return URLEncoder.encode(value, Charset.defaultCharset().name());
			} catch (UnsupportedEncodingException e1) {
				return value;
			}
		}
	}

	public static void processStatusLine(HttpResponse response) throws HttpResponseException, IOException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= 300) {
			String responseBody = "";
			String responseHeader = "";
			String httpStatusDes = HttpStatus.getByCode(statusCode).getDescription();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseBody = EntityUtils.toString(entity);
			}
			Header[] headers = response.getAllHeaders();
			if (headers != null) {
				for (Header header : headers) {
					responseHeader += String.format("%s: %s;", header.getName(), header.getValue());
				}
			}
			JsonObject msg = new JsonObject();
			msg.addProperty("STATUS_CODE", statusCode);
			msg.addProperty("STATUS_DESC", httpStatusDes);
			msg.addProperty("RESPONSE_HEAD", responseHeader);
			msg.addProperty("RESPONSE_BODY", responseBody);
			throw new HttpResponseException(statusCode, gson.toJson(msg));
		}
	}

	public List<Header> getHeaders() {
		return this.headers;
	}

	@Override
	public void close() throws Exception {
		this.client.close();
	}
}
