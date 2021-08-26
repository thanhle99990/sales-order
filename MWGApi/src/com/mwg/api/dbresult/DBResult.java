package com.mwg.api.dbresult;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DBResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> nameList = new ArrayList<>();

	private Map<String, DataType> classMap = new HashMap<>();

	private Map<String, Object> valueMap = new HashMap<>();

	public DBResult() {
	}

	public DBResult(List<String> nameList, Map<String, DataType> classMap, Map<String, Object> valueMap) {
		this.nameList = nameList;
		this.classMap = classMap;
		this.valueMap = valueMap;
	}
	
	protected enum DataType {
		STRING, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, BIGDECIMA, DATE, TIME, TIMESTAMP, BOOLEAN
	}

	protected void addField(String field, DataType type, Object defaultValue) {
		nameList.add(field);
		classMap.put(field, type);
		valueMap.put(field, defaultValue);
	}

	protected void addFieldCheckExist(String field, DataType type, Object defaultValue) {
		if(!this.nameList.contains(field))
			nameList.add(field);
		classMap.put(field, type);
		valueMap.put(field, defaultValue);
	}

	protected void addFieldCheckExistAndNotNull(String field, DataType type, Object defaultValue) {
		if(!this.nameList.contains(field))
			nameList.add(field);
		classMap.put(field, type);
		if(defaultValue == null) return;
		valueMap.put(field, defaultValue);
	}
	
	public static void fillData(DBResult record, ResultSet resultSet) throws Exception {
		for (String field : record.nameList) {
			DataType type = record.classMap.get(field);
			if (type == null) {
				throw new Exception("Data type not found for field: " + field);
			}
			switch (type) {
			case STRING:
				record.valueMap.put(field, resultSet.getString(field));
				break;
			case BYTE:
				record.valueMap.put(field, resultSet.getByte(field));
				break;
			case SHORT:
				record.valueMap.put(field, resultSet.getShort(field));
				break;
			case INTEGER:
				record.valueMap.put(field, resultSet.getInt(field));
				break;
			case LONG:
				record.valueMap.put(field, resultSet.getLong(field));
				break;
			case FLOAT:
				record.valueMap.put(field, resultSet.getFloat(field));
				break;
			case DOUBLE:
				record.valueMap.put(field, resultSet.getDouble(field));
				break;
			case BIGDECIMA:
				record.valueMap.put(field, resultSet.getBigDecimal(field));
				break;
			case DATE:
				record.valueMap.put(field, resultSet.getDate(field));
				break;
			case TIME:
				record.valueMap.put(field, resultSet.getTime(field));
				break;
			case TIMESTAMP:
				record.valueMap.put(field, resultSet.getTimestamp(field));
				break;
			case BOOLEAN:
				record.valueMap.put(field, resultSet.getBoolean(field));
				break;
			default:
				throw new Exception("Data type not support: " + type.name());
			}
		}
	}

	public Object getValue(String field) {
		return valueMap.get(field);
	}

	public String dumpToString() {
		StringBuffer result = new StringBuffer();
		for (String field : this.nameList) {
			result.append(field).append("=").append(getValue(field)).append(System.lineSeparator());
		}
		return result.toString();
	}
	
	public void clearNameListAndClassMap() {
		this.classMap = null;
		this.nameList = null;
	}
	
	public List<String> getNameList(){
		return this.nameList;
	}
	
	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Map<String, DataType> getClassMap() {
		return classMap;
	}
}
