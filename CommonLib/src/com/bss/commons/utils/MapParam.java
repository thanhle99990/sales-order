package com.bss.commons.utils;

import java.util.HashMap;
import java.util.Map;

public class MapParam {
   private final Map<String, Object> dataMap = new HashMap<>();
   
   private MapParam() {
   }
   
   public static MapParam ins(String key,Object value) {
	   MapParam ins = new MapParam().add(key, value);
	   return ins;
   }
      
   public MapParam add(String key,Object value) {
	   dataMap.put(key, value);
	   return this;
   }
   
   public Map<String, Object> toMap(){
	   return dataMap;
   }
}
