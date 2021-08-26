package com.mwg.api.posclient.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mwg.api.configs.DBConnPool;
import com.mwg.api.configs.PgDBConfig;

public class CallStoreProcedure {

	private static CallStoreProcedure callStoreProcedure;

	public static CallStoreProcedure ins() {
		if (callStoreProcedure == null) {
			callStoreProcedure = new CallStoreProcedure();
		}
		return callStoreProcedure;
	}
	
	public ResultSet callStoreProcedure(String schema, String procedureName, List<Object> params, boolean isNeedOutput) throws Exception {
		String callString = buildCallStoreProcedureString(procedureName, params.size(), isNeedOutput);
		DBConnPool dbConn = PgDBConfig.getDbConn();
		try (Connection conn = dbConn.getConnection();
				CallableStatement proc = conn.prepareCall(callString)) {
			conn.createStatement().execute("SET search_path = " + schema);
			
			int countParams = 1;
			
			if (isNeedOutput) {
				proc.registerOutParameter(countParams, Types.OTHER);
				countParams++;
			}
			
			if (params != null && !params.isEmpty()) {
				for (Object param : params) {
					if (param instanceof Integer) {
						proc.setInt(countParams, (int) param);
					} else if (param instanceof Long) {
						proc.setLong(countParams, (long) param);
					} else if (param instanceof Double) {
						proc.setDouble(countParams, (double) param);
					} else if (param instanceof String) {
						proc.setString(countParams, (String) param);
					} else if (param instanceof Date) {
						proc.setDate(countParams, new java.sql.Date(((Date)param).getTime()));
					}
					countParams++;
				}
			}
			proc.execute();
			ResultSet results = (ResultSet) proc.getObject(1);
			conn.commit();
			return results;
		}
	}
	
	public String buildCallStoreProcedureString(String procedureName, int inputQuantity, boolean isNeedOutput)
			throws Exception {
		String output = isNeedOutput ? "? =" : "";
		String questionMarksParams = buildQuestionMarksParams(inputQuantity);
		String callString = String.format("{ %s call  %s(%s) }", output, procedureName, questionMarksParams);
		return callString;
	}
	
	public String buildQuestionMarksParams(int quantity) throws Exception {
		if (quantity < 0) {
			throw new Exception("Invalid quantity of question marks. Quantity need to greater than or equal to 0");
		}
		List<String> questionMarks = new ArrayList<String>();
		if (quantity > 0) {
			for (int i = 0; i < quantity; i++) {
				questionMarks.add("?");
			}
		}
		String questionMarkParams = String.join(", ", questionMarks);
		return String.format("%s", questionMarkParams);
	}
	
}
