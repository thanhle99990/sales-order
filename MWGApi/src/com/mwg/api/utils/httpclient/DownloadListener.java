package com.mwg.api.utils.httpclient;

public interface DownloadListener {
	public void onDownload(long received, long length);
}