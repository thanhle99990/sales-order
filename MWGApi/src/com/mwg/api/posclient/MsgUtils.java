package com.mwg.api.posclient;

import java.sql.Timestamp;

import javax.swing.ImageIcon;
import com.mwg.api.posclient.webservice.ERPWsExp;
import com.mwg.api.resources.I18n;

public class MsgUtils {

	private static String errorMsg = "";

	public static void showMsg(String msg) {
	}

	public static void showMsg(String msg, ImageIcon icon) {
	}

	public static void showWarnMsg(String msg) {
	}

	public static void showErrorMsg(String msg, Exception e) {
		if (e instanceof ERPWsExp) {
			errorMsg = ((ERPWsExp) e).getErrorMsg();
		} 
		else {
			errorMsg = e.toString();
		}
	}

	public static void showErrorMsg(Exception e) {
		MsgUtils.showErrorMsg(I18n.get("anErrorOccurred"), e);
	}

	public static void showErrorMsg(String msg) {
	}
	
	public static String getErrorMsg() {
		return errorMsg;
	}
	
	public static void logConsole(String msg) {
		System.out.println(msg + " " + new Timestamp(System.currentTimeMillis()));
	}
	
}
