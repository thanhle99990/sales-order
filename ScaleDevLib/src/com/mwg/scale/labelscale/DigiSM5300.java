package com.mwg.scale.labelscale;

import java.util.Map;

import com.mwg.scale.DigiHelper;

public class DigiSM5300 extends DigiSM500 {
  @Override
  public void writeTemplateToScale(Map<String, Object> config) throws Exception {
    DigiHelper.writePrintFormatToDigiScale(LabelScaleFactory.workingPath, getConf(), "SM-IP-F52_3.DAT");
    DigiHelper.writeTextToDigiScale(LabelScaleFactory.workingPath, getConf(), "SM-IP-F56_3.DAT");
  }
}
