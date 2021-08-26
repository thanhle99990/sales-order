package com.mwg.scale.labelscale;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.mwg.scale.DigiHelper;
import com.mwg.scale.DigiLabelScale;
import com.mwg.scale.IScale;
import com.mwg.scale.PLUUtils;
import com.mwg.scale.utils.Utils;

public class DigiSM500 implements ILabelScale {

  private String ip = "127.0.0.1";

  @Override
  public Object buildPLURecord(int pluNumber, String pluName, int formLabel, Map<String, String> txtFields, int unitPrice, short sellByDate, short sellByTime) throws Exception {
    String ingredient = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.IngredientKey), "");
    String usageGuide = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.UsageGuideKey), "");
    String preservation = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.PreservationKey), "");
    return PLUUtils.buildPLURecord(pluNumber, pluName, formLabel, ingredient, usageGuide, preservation, unitPrice, sellByDate, sellByTime);
  }

  @Override
  public void writePLUDataToScale(List<Object> pluDataList) throws Exception {
    String fileNamePlu = LabelScaleFactory.workingPath + File.separator + "SM" + ip + "F37.DAT";
    try (OutputStream output = new FileOutputStream(fileNamePlu)) {
      for (Object pluData : pluDataList) {
        output.write(((String) pluData).getBytes("ASCII"));
      }
      output.write(DigiHelper.getDigiPLUFooter().getBytes("ASCII"));
    }
    DigiHelper.writePluDataToDigiScale(LabelScaleFactory.workingPath, ip);
  }

  @Override
  public void writeTemplateToScale(Map<String, Object> config) throws Exception {
    DigiHelper.writePrintFormatToDigiScale(LabelScaleFactory.workingPath, ip, "SM-IP-F52_2.DAT");
    DigiHelper.writeTextToDigiScale(LabelScaleFactory.workingPath, ip, "SM-IP-F56_2.DAT");
  }

  @Override
  public boolean testAvailable() throws Exception {
    IScale scale = new DigiLabelScale();
    scale.setConf(ip);
    scale.getValueInGam();
    return true;
  }

  @Override
  public void setConf(String conf) {
    this.ip = conf;

  }

  @Override
  public String getConf() {
    return this.ip;
  }
}
