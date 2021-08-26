package com.mwg.scale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.mwg.scale.resources.Res;
import com.mwg.scale.utils.BCD;
import com.mwg.scale.utils.OsCheck;
import com.mwg.scale.utils.PNumber;
import com.mwg.scale.utils.PString;
import com.mwg.scale.utils.ProcessUtils;
import com.mwg.scale.utils.Utils;
import com.mwg.scale.utils.WrappedObject;

public class DigiHelper {

  private static final String charset = "ASCII";
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static final String BCC = "0C";
  private static final String barcodeFormatStr = "05";
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static final String startC = "03";
  private static final String pluStatusStr1 = "7400";
  private static final String pluStatus2Str = "5FE0DB";
  private static final String pluStatus3Str = "5F20DB";
  private static final String mainGroupCode = "0997";
  private static final String pluNamePrefix = "0000000000000000000000000000000000002000000100000000000000000000";
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private static String genEanData(Integer pluNumber) {
    String pluCode = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(pluNumber)), '0', 5);
    return PString.padRight("21" + pluCode, '0', 14);
  }

  public static String genEanDataWithWeight(Integer pluNumber, long weightInGam) {
    String pluCode = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(pluNumber)), '0', 5);
    String weightCode = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(weightInGam)), '0', 5);
    return PString.padRight("21" + pluCode + weightCode, '0', 13);
  }

  public static String getDigiPLUFooter() throws Exception {
    return Utils.streamToString(Res.class.getResourceAsStream("PLUFooter.txt"), charset);
  }

  public static void writePluDataToDigiScale(File path, String scaleIp) throws Exception {
    String fileName = OsCheck.getOSType() == OsCheck.OSType.Windows ? path + "\\digiwtcp.exe" : "./digiltcp";
    File resultFile = new File(path + File.separator + "result");
    if (resultFile.exists()) {
      resultFile.delete();
    }
    String cdCmd = ProcessUtils.getCDCommand(path);
    String runCmd = fileName + " WR 37 " + scaleIp;
    ProcessUtils.runCommand(new String[] { cdCmd, runCmd }, path, null);
    String result = Utils.streamToString(new FileInputStream(resultFile), charset);
    final WrappedObject<Integer> resultCode = new WrappedObject<>();
    try (Scanner scaner = new Scanner(result)) {
      scaner.forEachRemaining(line -> {
        if (line.startsWith(scaleIp + ":")) {
          resultCode.setValue(Integer.valueOf(line.substring(scaleIp.length() + 1)));
        }
      });
    }
    if (resultCode.getValue() == null) {
      throw new Exception("Result code not found.");
    }
    if (resultCode.getValue() != 0) {
      throwExceptionFromDigiScaleErrorCode(resultCode.getValue());
    }
  }

  public static void writePrintFormatToDigiScale(File path, String scaleIp, String F52File) throws Exception {
    String fileName = OsCheck.getOSType() == OsCheck.OSType.Windows ? path + "\\digiwtcp.exe" : "./digiltcp";
    File resultFile = new File(path + File.separator + "result");
    if (resultFile.exists()) {
      resultFile.delete();
    }
    String fileOut = path + File.separator + "SM" + scaleIp + "F52.DAT";
    try (InputStream input = Res.class.getResourceAsStream(F52File); OutputStream output = new FileOutputStream(fileOut)) {
      Utils.copyStream(input, output);
    }
    String cdCmd = ProcessUtils.getCDCommand(path);
    String runCmd = fileName + " WR 52 " + scaleIp;
    ProcessUtils.runCommand(new String[] { cdCmd, runCmd }, path, null);
    String result = Utils.streamToString(new FileInputStream(resultFile), charset);
    final WrappedObject<Integer> resultCode = new WrappedObject<>();
    try (Scanner scaner = new Scanner(result)) {
      scaner.forEachRemaining(line -> {
        if (line.startsWith(scaleIp + ":")) {
          resultCode.setValue(Integer.valueOf(line.substring(scaleIp.length() + 1)));
        }
      });
    }
    if (resultCode.getValue() == null) {
      throw new Exception("Result code not found.");
    }
    if (resultCode.getValue() != 0) {
      throwExceptionFromDigiScaleErrorCode(resultCode.getValue());
    }
  }

  public static void writeTextToDigiScale(File path, String scaleIp, String F56File) throws Exception {
    String fileName = OsCheck.getOSType() == OsCheck.OSType.Windows ? path + "\\digiwtcp.exe" : "./digiltcp";
    File resultFile = new File(path + File.separator + "result");
    if (resultFile.exists()) {
      resultFile.delete();
    }
    String fileOut = path + File.separator + "SM" + scaleIp + "F56.DAT";
    try (InputStream input = Res.class.getResourceAsStream(F56File); OutputStream output = new FileOutputStream(fileOut)) {
      Utils.copyStream(input, output);
    }
    String cdCmd = ProcessUtils.getCDCommand(path);
    String runCmd = fileName + " WR 56 " + scaleIp;
    ProcessUtils.runCommand(new String[] { cdCmd, runCmd }, path, null);
    String result = Utils.streamToString(new FileInputStream(resultFile), charset);
    final WrappedObject<Integer> resultCode = new WrappedObject<>();
    try (Scanner scaner = new Scanner(result)) {
      scaner.forEachRemaining(line -> {
        if (line.startsWith(scaleIp + ":")) {
          resultCode.setValue(Integer.valueOf(line.substring(scaleIp.length() + 1)));
        }
      });
    }
    if (resultCode.getValue() == null) {
      throw new Exception("Result code not found.");
    }
    if (resultCode.getValue() != 0) {
      throwExceptionFromDigiScaleErrorCode(resultCode.getValue());
    }
  }

  private static void throwExceptionFromDigiScaleErrorCode(int errorCode) throws Exception {
    switch (errorCode) {
    case -1:
      throw new Exception("Error opening input or output file.");
    case -2:
      throw new Exception("Error reading input file.");
    case -3:
      throw new Exception("Error writing to input or output file.");
    case -5:
      throw new Exception("Error connecting to scale.");
    case -6:
      throw new Exception("Error receiving data from scale");
    case -7:
      throw new Exception("Error sending data to scale.");
    case -8:
      throw new Exception("Read error returned by scale");
    case -9:
      throw new Exception("Write error returned by scale");
    case -10:
      throw new Exception("No record error returned by scale.");
    case -11:
      throw new Exception("No space error returned by scale.");
    case -12:
      throw new Exception("Undefined error returned by scale.");
    default:
      throw new Exception("Undefined error returned by scale.");
    }
  }

  public static String buildDigiPLURecord(int pluNumber, String pluName, int formLabel, String ingredient, String specMess, int unitPrice, short sellByDate, short sellByTime)
      throws Exception {
    StringBuilder record = new StringBuilder();
    String pluNameSize = PString.padLeft(Integer.toHexString(pluName.length()).toUpperCase(), '0', 2);
    String eanDataStr = genEanData(pluNumber);
    String contentStr = pluNamePrefix + startC + pluNameSize + PNumber.bytes2Hex(pluName.getBytes(charset)) + BCC;
    String ingredientSize = "";
    String specMessSize = "";
    if (ingredient != null) {
      ingredientSize = PString.padLeft(Integer.toHexString(ingredient.length()).toUpperCase(), '0', 2);
      contentStr += startC + ingredientSize + PNumber.bytes2Hex(ingredient.getBytes(charset)) + BCC;
    }
    if (specMess != null) {
      specMessSize = PString.padLeft(Integer.toHexString(specMess.length()).toUpperCase(), '0', 2);
      contentStr += startC + specMessSize + PNumber.bytes2Hex(specMess.getBytes(charset)) + BCC;
    }
    if (ingredient != null && specMess != null) {
      contentStr = pluNamePrefix + startC + pluNameSize + PNumber.bytes2Hex(pluName.getBytes(charset)) + BCC;
      contentStr += startC + ingredientSize + PNumber.bytes2Hex(ingredient.getBytes(charset)) + BCC;
      contentStr += startC + specMessSize + PNumber.bytes2Hex(specMess.getBytes(charset)) + BCC;
    }
    String pluNumberStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(pluNumber)), '0', 8);
    String unitPriceStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(unitPrice)), '0', 8);
    String sellByDateStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(sellByDate)), '0', 4);
    String sellByTimeStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(sellByTime)), '0', 4);
    String usedByDateStr = PString.padLeft(BCD.Number2BCDHexString(Long.valueOf(0)), '0', 4);
    String labelFormat1Str = PString.padLeft(Integer.toHexString(formLabel).toUpperCase(), '0', 2);
    String labelFormat2Str = "00";
    String recordSizeStr = PString.padLeft(Integer.toHexString((66 + contentStr.length()) / 2).toUpperCase(), '0', 4);
    if (ingredient != null && specMess != null) {
      record.append(pluNumberStr).append(recordSizeStr).append(pluStatusStr1).append(pluStatus2Str).append(unitPriceStr);
    } else {
      record.append(pluNumberStr).append(recordSizeStr).append(pluStatusStr1).append(pluStatus3Str).append(unitPriceStr);
    }
    record.append(labelFormat1Str).append(labelFormat2Str).append(barcodeFormatStr).append(eanDataStr).append(mainGroupCode);
    record.append(sellByDateStr).append(sellByTimeStr).append(usedByDateStr).append(contentStr);
    return record.toString();
  }

  public static void copyDigiTcpApp(File workingPath) throws Exception {
    String fileName = OsCheck.getOSType() == OsCheck.OSType.Windows ? "digiwtcp.exe" : "digiltcp";
    File outFile = new File(workingPath + File.separator + fileName);
    if (!outFile.exists()) {
      try (InputStream input = Res.class.getResourceAsStream(fileName); OutputStream output = new FileOutputStream(outFile)) {
        Utils.copyStream(input, output);
      }
      if (OsCheck.getOSType() == OsCheck.OSType.MacOS || OsCheck.getOSType() == OsCheck.OSType.Linux) {
        ProcessUtils.runCommand(new String[] { "chmod 755 " + fileName }, workingPath, null);
      }
    }
  }
}
