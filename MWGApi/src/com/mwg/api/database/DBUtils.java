package com.mwg.api.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.dbutils.QueryRunner;

import com.mwg.api.commons.SQLParser;
import com.mwg.api.commons.Utils;


public class DBUtils {

	public static String createInsertSql(String table, List<ColumnValue> fieldList) {
		String sql = "INSERT INTO {{table}} ({{fields}}) VALUES ({{values}})";
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();
		for (ColumnValue column : fieldList) {
			if (fields.length() > 0) {
				fields.append(",").append(column.getColumn());
			} else {
				fields.append(column.getColumn());
			}
			if (values.length() > 0) {
				values.append(",").append("?");
			} else {
				values.append("?");
			}
		}
		sql = Utils.replaceParams(sql, "table", table);
		sql = Utils.replaceParams(sql, "fields", fields.toString());
		sql = Utils.replaceParams(sql, "values", values.toString());
		return sql;
	}

	public static void fillPreparedStmt(PreparedStatement pStmt, List<ColumnValue> fieldList) throws Exception {
		Object[] params = new Object[fieldList.size()];
		for (int i = 0; i < fieldList.size(); i++) {
			params[i] = fieldList.get(i).getValue();
		}
		QueryRunner runner = new QueryRunner();
		runner.fillStatement(pStmt, params);
	}

	public static List<String> getTableList(Connection conn) throws SQLException {
		DatabaseMetaData metadata = conn.getMetaData();
		List<String> tables = new ArrayList<>();
		ResultSet rs = metadata.getTables(null, null, null, new String[] { "TABLE" });
		while (rs.next()) {
			String schema = rs.getString("TABLE_SCHEM").toLowerCase();
			String table = rs.getString("TABLE_NAME").toLowerCase();
			tables.add(schema + "." + table);
		}
		return tables;
	}

	public static boolean isTableExist(Connection conn, String table) throws SQLException {
		List<String> tables = getTableList(conn);
		return CollectionUtils.exists(tables, new Predicate() {
			@Override
			public boolean evaluate(Object value) {
				return table.equalsIgnoreCase(value.toString());
			}
		});
	}

	public static void exeSqlScript(Connection conn, String script) throws SQLException {
		SQLParser parser = new SQLParser(script);
		for (String stmt : parser.getStmtList()) {
			conn.createStatement().execute(stmt);
		}
	}
	
}
