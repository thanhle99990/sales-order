package com.mwg.scale.labelscale;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mwg.scale.DigiHelper;

public class LabelScaleFactory {

  private static Map<String, Class<? extends ILabelScale>> scaleNameClzMap = new HashMap<>();
  private static Class<? extends ILabelScale> defaultScaleClz = DigiSM500.class;

  public static final File workingPath = new File(System.getProperty("user.home") + File.separator + "ScaleDev");

  static {
    scaleNameClzMap.put("1#CAS_CL5200", CASCL5200.class);
    scaleNameClzMap.put("2#DIGI_SM500", DigiSM500.class);
    scaleNameClzMap.put("3#DIGI_SM5300", DigiSM5300.class);
  }

  public static List<String> getScaleNames() {
    List<String> result = Arrays.asList(scaleNameClzMap.keySet().toArray(new String[] {}));
    Collections.sort(result);
    return result;
  }

  public static String getScaleName(ILabelScale scale) {
    for (String name : getScaleNames()) {
      if (scale.getClass() == scaleNameClzMap.get(name)) {
        return name;
      }
    }
    return null;
  }

  private static Class<? extends ILabelScale> getScaleClass(Integer type) {
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

  private static Integer getScaleType(ILabelScale scale) {
    return parseScaleTypeOfName(getScaleName(scale));
  }

  public static String toConf(ILabelScale scale) throws Exception {
    Integer type = getScaleType(scale);
    return (type != null) ? String.format("%s#%d", scale.getConf(), type) : null;
  }

  public static ILabelScale fromConf(String conf) throws Exception {
    Pattern pattern = Pattern.compile("(.+)#([0-9]{1})");
    Matcher matcher = pattern.matcher(conf.trim());
    Class<? extends ILabelScale> clz = defaultScaleClz;
    if (matcher.matches()) {
      conf = matcher.group(1);
      Integer type = Integer.valueOf(matcher.group(2));
      if ((clz = getScaleClass(type)) == null) {
        throw new Exception(String.format("UNKNOWN SCALE TYPE: %d", type));
      }
    }
    ILabelScale scale = clz.getConstructor().newInstance();
    scale.setConf(conf);
    return scale;
  }

  public static void init() throws Exception {
    if (!workingPath.exists()) {
      workingPath.mkdirs();
    }
    DigiHelper.copyDigiTcpApp(workingPath);
  }
}
