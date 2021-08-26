package com.mwg.api.dbresult;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBResultList<E extends DBResult> {

	private List<E> results;
	
	public DBResultList() {
		results = new ArrayList<>();
	}
	
	public void addDBResult(Class<E> clazz, ResultSet resultSet) throws Exception {
		E obj = clazz.newInstance();
		
		DBResult.fillData(obj, resultSet);
		obj.clearNameListAndClassMap();
		results.add(obj);
	}
	
	public List<E> getResults() {
		return results;
	}
	
}
