package com.mwg.jhttp;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface IServHandler {
  public JsonElement handle(JsonObject param) throws IOException;

  public JsonObject getDescription();

  public String getPath();
}
