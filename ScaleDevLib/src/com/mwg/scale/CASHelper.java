package com.mwg.scale;

import java.util.HashMap;
import java.util.Map;

import com.cas.code.CommonDefines.EmActionType;
import com.cas.code.CommonDefines.EmCommType;
import com.cas.code.CommonDefines.EmDataType;
import com.cas.code.CommonDefines.EmScaleModel;
import com.cas.code.CommonDefines.MapKeyScaleEnv;
import com.cas.code.CommonDefines.MapKeyScaleInfo;
import com.cas.code.CommonDefines.ModuleConfigField;
import com.cas.code.CommonDefines.ScaleType;
import com.cas.commlib.CasCommLib;

public class CASHelper {
	public static Map<String, String> makeScaleEnv(EmDataType type, EmActionType actionType) {
		Map<String, String> returnScaleEnv = new HashMap<String, String>();
		switch (type) {
		case PLU:
			returnScaleEnv.put(MapKeyScaleEnv.MK_ENCODING, "Cp1258");
			returnScaleEnv.put(MapKeyScaleEnv.MK_ACTION, actionType.getActionCode());
			returnScaleEnv.put(MapKeyScaleEnv.MK_DATATYPE, EmDataType.PLU.getDataType());
			returnScaleEnv.put(MapKeyScaleEnv.MK_COMMTYPE, EmCommType.COMMTYPE_TCPIP.getCommType());
			returnScaleEnv.put(ModuleConfigField.decimalPointPrice.name(), "0");
			returnScaleEnv.put(ModuleConfigField.decimalPointWeight.name(), "0");
			returnScaleEnv.put(ModuleConfigField.decimalPointPercent.name(), "0");
			break;
		case LabelInfo:
			returnScaleEnv.put(MapKeyScaleEnv.MK_ENCODING, "Cp1258");
			returnScaleEnv.put(MapKeyScaleEnv.MK_ACTION, actionType.getActionCode());
			returnScaleEnv.put(MapKeyScaleEnv.MK_DATATYPE, EmDataType.LabelInfo.getDataType());
			returnScaleEnv.put(MapKeyScaleEnv.MK_COMMTYPE, EmCommType.COMMTYPE_TCPIP.getCommType());
			break;
		case Ingredient:
			returnScaleEnv.put(MapKeyScaleEnv.MK_ENCODING, "Cp1258");
			returnScaleEnv.put(MapKeyScaleEnv.MK_ACTION, actionType.getActionCode());
			returnScaleEnv.put(MapKeyScaleEnv.MK_DATATYPE, EmDataType.Ingredient.getDataType());
			returnScaleEnv.put(MapKeyScaleEnv.MK_COMMTYPE, EmCommType.COMMTYPE_TCPIP.getCommType());
			break;
		case Weight:
			returnScaleEnv.put(MapKeyScaleEnv.MK_ENCODING, "Cp1258");
			returnScaleEnv.put(MapKeyScaleEnv.MK_ACTION, actionType.getActionCode());
			returnScaleEnv.put(MapKeyScaleEnv.MK_DATATYPE, EmDataType.Weight.getDataType());
			returnScaleEnv.put(MapKeyScaleEnv.MK_COMMTYPE, EmCommType.COMMTYPE_TCPIP.getCommType());
			break;
		default:
			break;
		}
		return returnScaleEnv;
	}

	public static Map<String, String> makeScaleInfo(String strIPAddress, Integer port, EmScaleModel scaleModel) {
		Map<String, String> returnScaleInfo = new HashMap<String, String>();
		returnScaleInfo.put(MapKeyScaleInfo.MK_IP, strIPAddress);
		returnScaleInfo.put(MapKeyScaleInfo.MK_PORT, port.toString());
		returnScaleInfo.put(MapKeyScaleInfo.MK_MODEL, scaleModel.getModelCode());
		returnScaleInfo.put(MapKeyScaleInfo.MK_SCALETYPE, ScaleType.Pole.getTypeCode());
		return returnScaleInfo;
	}

	public static void destroyCasIns(CasCommLib ins) {
		try {
			ins.allKillTcpClients();
			ins.quitTcpClient();
		} catch (Exception e) {
			//////////////////////////////////////////////////
		}
	}

}
