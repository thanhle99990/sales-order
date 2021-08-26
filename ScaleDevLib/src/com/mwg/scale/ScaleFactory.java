package com.mwg.scale;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScaleFactory {

  private static Map<String, Class<? extends IScale>> scaleNameClzMap = new HashMap<>();

  private static Class<? extends IScale> defaultScaleClz = DigiScale.class;

  static {
    scaleNameClzMap.put("0#DIGI_ONLY_SCALE", DigiScale.class);
    scaleNameClzMap.put("1#DIGI_WITH_LABEL", DigiLabelScale.class);
    scaleNameClzMap.put("2#CAS_SCALE_LABEL", CASLabelScale.class);
    scaleNameClzMap.put("3#CAS_ONLY_SCALE", CASScale.class);
  }

  public static List<String> getScaleNames() {
    List<String> result = Arrays.asList(scaleNameClzMap.keySet().toArray(new String[] {}));
    Collections.sort(result);
    return result;
  }

  public static String getScaleName(IScale scale) {
    for (String name : getScaleNames()) {
      if (scale.getClass() == scaleNameClzMap.get(name)) {
        return name;
      }
    }
    return null;
  }

  private static Class<? extends IScale> getScaleClass(Integer type) {
    for (String name : getScaleNames()) {
      if (parseScaleTypeOfName(name) == type) {
        return scaleNameClzMap.get(name);
      }
    }
    return null;
  }

  public static Integer parseScaleTypeOfName(String name) {
    if (name != null) {
      Pattern pattern = Pattern.compile("([0-9]{1})#(.+)");
      Matcher matcher = pattern.matcher(name.trim());
      if (matcher.matches()) {
        return Integer.valueOf(matcher.group(1));
      }
    }
    return null;
  }

  private static Integer getScaleType(IScale scale) {
    return parseScaleTypeOfName(getScaleName(scale));
  }

  public static String toConf(IScale scale) throws Exception {
    Integer type = getScaleType(scale);
    return (type != null) ? String.format("%s#%d", scale.getConf(), type) : null;
  }

  public static IScale fromConf(String conf) throws Exception {
    if (conf != null && !conf.trim().isEmpty()) {
      Pattern pattern = Pattern.compile("(.+)#([0-9]{1})");
      Matcher matcher = pattern.matcher(conf.trim());
      Class<? extends IScale> clz = defaultScaleClz;
      if (matcher.matches()) {
        conf = matcher.group(1);
        Integer type = Integer.valueOf(matcher.group(2));
        if ((clz = getScaleClass(type)) == null) {
          throw new Exception(String.format("UNKNOWN SCALE TYPE: %d", type));
        }
      }
      IScale scale = clz.getConstructor().newInstance();
      scale.setConf(conf);
      return scale;
    } else {
      throw new Exception("COULDN'T CREATE SCALE FROM EMPTY CONFIG !");
    }
  }
}
