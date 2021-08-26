package com.bss.commons.httpclient;

public interface DownloadListener {
	public void onDownload(long received, long length);
}