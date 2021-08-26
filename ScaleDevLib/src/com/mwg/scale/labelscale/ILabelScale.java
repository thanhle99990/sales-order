package com.mwg.scale.labelscale;

import java.util.List;
import java.util.Map;

public interface ILabelScale {

  public static class TextFieldKey {
    public static final String IngredientKey = "ingredient";
    public static final String UsageGuideKey = "usageguide";
    public static final String PreservationKey = "preservation";
  }

  public Object buildPLURecord(int pluNumber, String pluName, int formLabel, Map<String, String> txtFields, int unitPrice, short sellByDate, short sellByTime) throws Exception;

  public void writePLUDataToScale(List<Object> pluDataList) throws Exception;

  public void writeTemplateToScale(Map<String, Object> config) throws Exception;

  public boolean testAvailable() throws Exception;

  public void setConf(String conf);

  public String getConf();

}
