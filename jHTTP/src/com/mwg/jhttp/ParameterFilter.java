package com.mwg.jhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ParameterFilter extends Filter {

  private static final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setPrettyPrinting().create();

  public static final String charset = "UTF-8";

  public static final String paramKeyJson = "parametersJson";

  public static final String paramKeyError = "parametersError";

  @Override
  public String description() {
    return "Parses the requested for parameters";
  }

  @Override
  public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
    parsePostParameters(exchange);
    parseGetParameters(exchange);
    chain.doFilter(exchange);
  }

  private void parseGetParameters(HttpExchange exchange) throws UnsupportedEncodingException {
    URI requestedUri = exchange.getRequestURI();
    String query = requestedUri.getRawQuery();
    JsonObject jsonData = (JsonObject) exchange.getAttribute(paramKeyJson);
    if (jsonData == null) {
      jsonData = new JsonObject();
      exchange.setAttribute(paramKeyJson, jsonData);
    }
    parseQuery(query, jsonData);
  }

  private void parsePostParameters(HttpExchange exchange) throws IOException {
    if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
      String request = getRequestBody(exchange, charset);
      JsonObject jsonData = new JsonObject();
      try {
        jsonData = gson.fromJson(request, JsonObject.class);
        exchange.setAttribute(paramKeyJson, (jsonData != null) ? jsonData : new JsonObject());
      } catch (Exception ex) {
        parseQuery(request, jsonData);
      }
      exchange.setAttribute(paramKeyJson, jsonData);
    }
  }

  private void parseQuery(String query, JsonObject jsonParam) throws UnsupportedEncodingException {
    if (query != null) {
      String pairs[] = query.split("[&]");
      for (String pair : pairs) {
        String param[] = pair.split("[=]");
        String key = null;
        String value = null;
        if (param.length > 0) {
          key = URLDecoder.decode(param[0], charset);
        } else {
          continue;
        }
        if (param.length > 1) {
          value = URLDecoder.decode(param[1], charset);
        } else {
          continue;
        }
        addParam2Json(jsonParam, key, value);
      }
    }
  }

  private static void addParam2Json(JsonObject json, String key, String value) {
    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
      json.addProperty(key, Boolean.valueOf(value));
    } else
      try {
        json.addProperty(key, Long.valueOf(value));
      } catch (Exception ex1) {
        try {
          json.addProperty(key, Double.valueOf(value));
        } catch (Exception ex2) {
          json.addProperty(key, value);
        }
      }
  }

  public static JsonObject getParam(HttpExchange exchange) {
    return (JsonObject) exchange.getAttribute(paramKeyJson);
  }

  public static void clearParam(HttpExchange exchange) {
    exchange.setAttribute(paramKeyJson, null);
  }

  private static String getRequestBody(HttpExchange exchange, String charset) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), charset))) {
      return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }
  }

}
