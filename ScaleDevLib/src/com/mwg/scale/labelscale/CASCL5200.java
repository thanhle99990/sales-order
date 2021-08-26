package com.mwg.scale.labelscale;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cas.code.CommonDefines.EmActionType;
import com.cas.code.CommonDefines.EmDataType;
import com.cas.code.CommonDefines.EmScaleModel;
import com.cas.code.CommonDefines.PluFieldID;
import com.cas.commlib.CasCommLib;
import com.cas.trans.vo.Label;
import com.mwg.scale.CASHelper;
import com.mwg.scale.CASLabelScale;
import com.mwg.scale.IScale;
import com.mwg.scale.resources.Res;
import com.mwg.scale.utils.Utils;

public class CASCL5200 implements ILabelScale {
  private String ip = "127.0.0.1";
  private static int port = 20304;

  @Override
  public Object buildPLURecord(int pluNumber, String pluName, int formLabel, Map<String, String> txtFields, int unitPrice, short sellByDate, short sellByTime) throws Exception {
    String ingredient = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.IngredientKey), "");
    String usageGuide = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.UsageGuideKey), "");
    String preservation = Utils.Coalesce(txtFields.get(ILabelScale.TextFieldKey.PreservationKey), "");
    //////////////////////////////////////////////////////////////////////////////////////////////////
    Map<String, String> tmpPlu = new HashMap<>();
    tmpPlu.put(PluFieldID.pluName1.name(), pluName);
    if (!ingredient.isEmpty()) {
      tmpPlu.put(PluFieldID.pluName2.name(), ingredient);
    }
    if (!usageGuide.isEmpty()) {
      tmpPlu.put(PluFieldID.pluName3.name(), usageGuide);
    }
    if (!preservation.isEmpty()) {
      tmpPlu.put(PluFieldID.extBarcode.name(), preservation);
    }
    tmpPlu.put(PluFieldID.departmentNo.name(), String.format("%d", 1));
    tmpPlu.put(PluFieldID.pluNo.name(), String.format("%d", pluNumber));
    tmpPlu.put(PluFieldID.pluType.name(), String.format("%d", 1));
    tmpPlu.put(PluFieldID.groupNo.name(), String.format("%d", 1));
    tmpPlu.put(PluFieldID.itemCode.name(), String.format("%d", pluNumber));
    tmpPlu.put(PluFieldID.priceType.name(), String.format("%d", 0));
    tmpPlu.put(PluFieldID.unitPrice.name(), String.format("%.2f", (float) unitPrice));
    tmpPlu.put(PluFieldID.sellbyDate.name(), String.format("%d", sellByDate + 1));
    tmpPlu.put(PluFieldID.sellbyTime.name(), String.format("%d", sellByTime));
    tmpPlu.put(PluFieldID.labelNo.name(), String.format("%d", formLabel + 34));
    tmpPlu.put(PluFieldID.ingredientNo.name(), String.format("%d", 20));
    return tmpPlu;
  }

  @Override
  public void writePLUDataToScale(List<Object> pluDataList) throws Exception {
    final Map<String, String> scaleInfo = CASHelper.makeScaleInfo(ip, port, EmScaleModel.CL5200);
    final Map<String, String> scaleEnv = CASHelper.makeScaleEnv(EmDataType.PLU, EmActionType.Send);
    final CASResult casResult = new CASResult();
    final CasCommLib cas = new CasCommLib();
    cas.sendToScale(scaleEnv, pluDataList, scaleInfo, casResult);
    casResult.waitForResult();
    CASHelper.destroyCasIns(cas);
  }

  @Override
  public void writeTemplateToScale(Map<String, Object> config) throws Exception {
    writeLabel5xToScale("Label_51");
    writeLabel5xToScale("Label_52");
  }

  private void writeLabel5xToScale(String label5x) throws Exception {
    final Map<String, String> scaleEnv = CASHelper.makeScaleEnv(EmDataType.LabelInfo, EmActionType.Send);
    final Map<String, String> scaleInfo = CASHelper.makeScaleInfo(ip, port, EmScaleModel.CL5200);
    final Label label = new Label();
    final CasCommLib cas = new CasCommLib();
    final CASResult casResult = new CASResult();
    label.setFilePath(createLabel5xTemp(label5x).getAbsolutePath());
    cas.sendToScale(scaleEnv, label, scaleInfo, casResult);
    casResult.waitForResult();
    CASHelper.destroyCasIns(cas);
  }

  @Override
  public boolean testAvailable() throws Exception {
    IScale scale = new CASLabelScale();
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

  private static File createLabel5xTemp(String name) throws Exception {
    File tempFile = File.createTempFile(name, ".lfm");
    try (OutputStream out = new FileOutputStream(tempFile); InputStream in = Res.class.getResourceAsStream(String.format("%s.lfm", name))) {
      Utils.copyStream(in, out);
    }
    tempFile.deleteOnExit();
    return tempFile;
  }
}
