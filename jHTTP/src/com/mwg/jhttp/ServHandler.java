package com.mwg.jhttp;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServHandler implements HttpHandler, IHttpHandler {

  private static final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

  private final IServHandler handler;

  public ServHandler(IServHandler handler) {
    this.handler = handler;
  }

  @Override
  public JsonObject getDescription() {
    return handler.getDescription();
  }

  @Override
  public HttpHandler getHandler() {
    return this;
  }

  @Override
  public String getPath() {
    return handler.getPath();
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    JsonObject param = ParameterFilter.getParam(exchange);
    try {
      responseJsonData(exchange, handler.handle(param));
    } catch (Exception ex) {
      JsonObject result = new JsonObject();
      result.addProperty("responseCode", -1);
      result.addProperty("responseMessage", ex.getMessage());
      responseJsonData(exchange, result);
    }
  }

  private static void responseJsonData(HttpExchange httpExchange, JsonElement jsonData) throws IOException {
    ParameterFilter.clearParam(httpExchange);
    httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
    if (httpExchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
      httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
      httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization, x-xsrf-token");
    }
    byte[] responseData = jsonData != null ? gson.toJson(jsonData).getBytes("UTF-8") : "".getBytes();
    httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
    httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate");
    httpExchange.getResponseHeaders().set("Pragma", "no-cache");
    httpExchange.getResponseHeaders().set("Expires", "0");
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    httpExchange.sendResponseHeaders(200, responseData.length);
    httpExchange.getResponseBody().write(responseData);
    httpExchange.getResponseBody().flush();
    httpExchange.close();
  }
}
