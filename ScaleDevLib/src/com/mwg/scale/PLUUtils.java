package com.mwg.scale;

import com.mwg.scale.utils.BCD;
import com.mwg.scale.utils.PNumber;
import com.mwg.scale.utils.PString;

public class PLUUtils {
  private static final String charset = "ASCII";
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static final String BCC = "0C";
  private static final String BCC_Continue = "0D";
  private static final String pluStatusStr = "7400";
  private static final String barcodeFormatStr = "05";
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static final String startC = "03";
  private static final String pluStatus3Str = "5FE0DB";
  private static final String pluStatus4Str = "5F20DB";
  private static final String pluStatusIngr = "5FA0DB";
  private static final String pluStatusSpec = "5F60DB";
  private static final String mainGroupCode = "0997";
  private static final String pluNamePrefix2 = "0000000000000000000000000000000000002000000100000000000000000000";

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static String genEanData(Integer pluNumber) {
    String pluCode = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(pluNumber)), '0', 5);
    return PString.padRight("21" + pluCode, '0', 14);
  }

  public static String buildPLURecord(int pluNumber, String pluName, int formLabel, String ingredient, String specMess, String keep, int unitPrice, short sellByDate,
      short sellByTime) throws Exception {
    if (pluName.length() > 255) {
      pluName = pluName.substring(0, 255);
    }
    StringBuilder record = new StringBuilder();
    String keepSize = "";
    String pluNameSize = "";
    String specMessSize = "";
    String ingredientSize = "";
    String contentNameStr = "";
    String contentIngrStr = "";
    String contentSpecStr = "";
    String contentKeepStr = "";
    if (!ingredient.isEmpty()) {
      ingredientSize = PString.padLeft(Integer.toHexString(ingredient.length()).toUpperCase(), '0', 2);
      contentIngrStr = startC + ingredientSize + PNumber.bytes2Hex(ingredient.getBytes(charset)) + BCC;
    }
    if (!specMess.isEmpty() && keep.isEmpty()) {
      specMessSize = PString.padLeft(Integer.toHexString(specMess.length()).toUpperCase(), '0', 2);
      contentSpecStr = startC + specMessSize + PNumber.bytes2Hex(specMess.getBytes(charset)) + BCC;
    } else if (specMess.isEmpty() && !keep.isEmpty()) {
      keepSize = PString.padLeft(Integer.toHexString(keep.length()).toUpperCase(), '0', 2);
      contentKeepStr = startC + keepSize + PNumber.bytes2Hex(keep.getBytes(charset)) + BCC;
    } else if (!specMess.isEmpty() && !keep.isEmpty()) {
      specMessSize = PString.padLeft(Integer.toHexString(specMess.length()).toUpperCase(), '0', 2);
      contentSpecStr = startC + specMessSize + PNumber.bytes2Hex(specMess.getBytes(charset)) + BCC_Continue;
      keepSize = PString.padLeft(Integer.toHexString(keep.length()).toUpperCase(), '0', 2);
      contentKeepStr = startC + keepSize + PNumber.bytes2Hex(keep.getBytes(charset)) + BCC;
    }
    pluNameSize = PString.padLeft(Integer.toHexString(pluName.length()).toUpperCase(), '0', 2);
    contentNameStr = startC + pluNameSize + PNumber.bytes2Hex(pluName.getBytes(charset)) + BCC;
    String contentStr = pluNamePrefix2 + contentNameStr + contentIngrStr + contentSpecStr + contentKeepStr;
    String eanDataStr = genEanData(pluNumber);
    String pluNumberStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(pluNumber)), '0', 8);
    String unitPriceStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(unitPrice)), '0', 8);
    String sellByDateStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(sellByDate)), '0', 4);
    String sellByTimeStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(sellByTime)), '0', 4);
    String usedByDateStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(0)), '0', 4);
    String labelFormat1Str = PString.padLeft(Integer.toHexString(formLabel).toUpperCase(), '0', 2);
    String labelFormat2Str = "00";
    String recordSizeStr = PString.padLeft(Integer.toHexString((66 + contentStr.length()) / 2).toUpperCase(), '0', 4);
    record.append(pluNumberStr).append(recordSizeStr).append(pluStatusStr);
    if (!ingredient.isEmpty() && (!specMess.isEmpty() || !keep.isEmpty())) {
      record.append(pluStatus3Str);
    } else if (ingredient.isEmpty() && (!specMess.isEmpty() || !keep.isEmpty())) {
      record.append(pluStatusSpec);
    } else if (!ingredient.isEmpty() && specMess.isEmpty() && keep.isEmpty()) {
      record.append(pluStatusIngr);
    } else {
      record.append(pluStatus4Str);
    }
    record.append(unitPriceStr).append(labelFormat1Str).append(labelFormat2Str).append(barcodeFormatStr).append(eanDataStr).append(mainGroupCode);
    record.append(sellByDateStr).append(sellByTimeStr).append(usedByDateStr).append(contentStr);
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    return record.toString();
  }
}
