package com.mwg.api.posclient.timechecker;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mwg.api.posclient.timechecker.DbTimeChecker;
import com.mwg.api.posclient.timechecker.SysTimeChecker;

public class TimeChecker {
	private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	public static boolean allowOutputVoucher() throws Exception {
		try {
			return SysTimeChecker.Ins().allowOutputVoucher() && DbTimeChecker.Ins().allowOutputVoucher() && checkDayBegining();
		} catch (Exception e) {
//			MsgUtils.saveDebugInfo(e);
			throw new Exception(e);
		}
//		return false;
	}
	public static boolean checkTimeWhenStart() throws Exception {
		return SysTimeChecker.Ins().checkTimeWhenStart() && DbTimeChecker.Ins().checkTimeWhenStart();
	}

	private static boolean checkDayBegining() {
		// KHONG CHO XUAT TAI THOI DIEM SANG NGAY MOI
		return df.format(new Date()).compareTo("23:58:30") < 0;
	}
}
