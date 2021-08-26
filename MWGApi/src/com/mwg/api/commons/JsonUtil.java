package com.mwg.api.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil {

	public static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting().create();

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String getString(JsonObject json, String prop) {
		if (json.has(prop) && json.get(prop).isJsonPrimitive() && json.get(prop).getAsJsonPrimitive().isString()) {
			return json.get(prop).getAsString();
		}
		return null;
	}

	public static Boolean getBoolean(JsonObject json, String prop) {
		if (json.has(prop) && json.get(prop).isJsonPrimitive() && json.get(prop).getAsJsonPrimitive().isBoolean()) {
			return json.get(prop).getAsBoolean();
		}
		return null;
	}

	public static Number getNumber(JsonObject json, String prop) {
		if (json.has(prop) && json.get(prop).isJsonPrimitive() && json.get(prop).getAsJsonPrimitive().isNumber()) {
			return json.get(prop).getAsNumber();
		}
		return null;
	}

	public static JsonObject getJsonObject(JsonObject json, String prop) {
		if (json.has(prop) && json.get(prop).isJsonObject()) {
			return json.get(prop).getAsJsonObject();
		}
		return null;
	}

	public static JsonArray getJsonArray(JsonObject json, String prop) {
		if (json.has(prop) && json.get(prop).isJsonArray()) {
			return json.get(prop).getAsJsonArray();
		}
		return null;
	}

	public static JsonObject toJsonObject(String json) throws Exception {
		return gson.fromJson(json, JsonObject.class);
	}

	public static JsonElement toJsonElement(String json) throws Exception {
		return gson.fromJson(json, JsonElement.class);
	}

  public static JsonArray toJsonArray(String json) throws Exception {
    return gson.fromJson(json, JsonArray.class);
  }
	
}
