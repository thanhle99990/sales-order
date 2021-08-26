package com.mwg.jhttp;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpHandler;

public interface IHttpHandler {
	public JsonObject getDescription();

	public HttpHandler getHandler();

	public String getPath();
}
