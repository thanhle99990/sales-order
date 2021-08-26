package com.mwg.api.configs;

import java.io.InputStream;

import com.bss.commons.utils.IOUtils;
import com.bss.commons.utils.OBJ;
import com.google.gson.JsonObject;
import com.mwg.api.commons.JsonUtil;
import com.mwg.api.resources.MRes;

public class DbConfig {

	private static DBConnConfig setDBConnConfig(JsonObject data) throws Exception {
			
			DBConnConfig dBConnConfig = new DBConnConfig("", "", "", 0);
			
			if(!data.isJsonNull() && data.has("connStr")) {
				dBConnConfig = new DBConnConfig(data.get("connStr").getAsString(), data.get("user").getAsString(), data.get("pass").getAsString(), data.get("maxConn").getAsInt());
				
		}
			return dBConnConfig;
	}

	public static DBConnConfig getDBConnConfig() throws Exception {
		DBConnConfig dBConnConfig = new DBConnConfig("", "", "", 0);
		try (InputStream in = MRes.getResourceStream("conf/dbConfigData.conf")) {
			JsonObject json = JsonUtil.gson.fromJson((String) OBJ.unZip(IOUtils.toString(in, "UTF-8")),
					JsonObject.class);
			dBConnConfig = setDBConnConfig(json);
		}
		return dBConnConfig;
	}

}
