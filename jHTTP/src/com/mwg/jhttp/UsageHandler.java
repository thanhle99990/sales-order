package com.mwg.jhttp;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UsageHandler implements IServHandler {
  private final List<IServHandler> handlers;

  public UsageHandler(List<IServHandler> handlers) {
    this.handlers = handlers;
  }

  @Override
  public JsonElement handle(JsonObject param) throws IOException {
    JsonArray descriptions = new JsonArray();
    handlers.forEach(handler -> {
      descriptions.add(handler.getDescription());
    });
    return descriptions;
  }

  @Override
  public JsonObject getDescription() {
    return new JsonObject();
  }

  @Override
  public String getPath() {
    return "/";
  }
}