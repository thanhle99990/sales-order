package com.bss.commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils {
  public static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();

  public static Boolean getBoolean(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isBoolean()) {
        return jsonElement.getAsJsonPrimitive().isBoolean();
      }
      throw new RuntimeException("NOT A BOOLEAN VALUE !");
    }
    return null;
  }

  public static String getString(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
        return jsonElement.getAsJsonPrimitive().getAsString();
      }
      throw new RuntimeException("NOT A STRING VALUE !");
    }
    return null;
  }

  public static Long getLong(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
        return jsonElement.getAsJsonPrimitive().getAsLong();
      }
      throw new RuntimeException("NOT A NUMBER VALUE !");
    }
    return null;
  }

  public static Integer getInt(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
        return jsonElement.getAsJsonPrimitive().getAsInt();
      }
      throw new RuntimeException("NOT A NUMBER VALUE !");
    }
    return null;
  }

  public static Float getFloat(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
        return jsonElement.getAsJsonPrimitive().getAsFloat();
      }
      throw new RuntimeException("NOT A NUMBER VALUE !");
    }
    return null;
  }

  public static JsonArray getAsJsonArray(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonArray()) {
        return jsonElement.getAsJsonArray();
      }
      throw new RuntimeException("NOT A JSON ARRAY !");
    }
    return null;
  }

  public static boolean isNull(JsonElement jsonElement) {
    return jsonElement == null || jsonElement.isJsonNull();
  }

  public static JsonObject getAsJsonObject(JsonElement jsonElement) {
    if (jsonElement != null && !jsonElement.isJsonNull()) {
      if (jsonElement.isJsonObject()) {
        return jsonElement.getAsJsonObject();
      }
      throw new RuntimeException("NOT A JSON OBJECT !");
    }
    return null;
  }
}
