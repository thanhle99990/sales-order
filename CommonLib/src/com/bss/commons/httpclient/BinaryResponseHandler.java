package com.bss.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class BinaryResponseHandler implements ResponseHandler<byte[]> {
	private final long maxLen;

	public BinaryResponseHandler() {
		this(0);
	}

	public BinaryResponseHandler(long maxLen) {
		this.maxLen = maxLen;
	}

	@Override
	public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		AHttpClient.processStatusLine(response);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		long received = 0;
		int len = 0;
		try (InputStream in = response.getEntity().getContent()) {
			while ((len = in.read(buffer)) >= 0) {
				out.write(buffer, 0, len);
				if (maxLen > 0 && ((received += len) > maxLen)) {
					throw new IOException(String.format("Max size exceeded [%d] (bytes)", maxLen));
				}
			}
		}
		return out.toByteArray();
	}
}