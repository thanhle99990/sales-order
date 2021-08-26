package com.mwg.api.handlers;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mwg.api.entities.APIRequest;
import com.mwg.api.entities.ResponseData;

public abstract class APIHandler<I extends APIRequest, O extends ResponseData> {
	private Gson gson;
	private final Class<I> clz;

	public APIHandler(Class<I> clz) {
		this.clz = clz;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			@SuppressWarnings("deprecation")
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					return df.parse(json.getAsJsonPrimitive().getAsString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});

		gson = builder.setDateFormat(DateFormat.FULL).create();
	}

	public O handle(String payload) throws Exception {
		I request = gson.fromJson(payload, clz);
		return handle(request);
	}

	protected abstract O handle(I request) throws Exception;
}
