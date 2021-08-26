package com.mwg.scale;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import com.mwg.scale.exeptions.NotStableException;
import com.mwg.scale.exeptions.OverflowException;
import com.mwg.scale.utils.BCD;
import com.mwg.scale.utils.Utils;

public class DigiLabelScale implements IScale {

	private final static String command = "A00100000000";

	private String ip = "127.0.0.1";

	@Override
	public long getValueInGam() throws Exception {
		int len = 6;
		byte[] buff = new byte[len];
		try (Socket socket = Utils.createSocket(ip, getScalePortFromIp(ip), 1000)) {
			try (OutputStream out = socket.getOutputStream(); InputStream in = socket.getInputStream()) {
				out.write(Utils.hexString2ByteArray(command));
				out.flush();
				if (Utils.readInputStreamWithTimeout(in, buff, IScale.scaleTimeoutMs) < len) {
					throw new Exception(String.format("Timed out in %d ms",IScale.scaleTimeoutMs));
				}
				byte status = buff[5];
				switch (status) {
				case 0:
					return BCD.BCD2Number(Arrays.copyOfRange(buff, 1, 5));
				case 1:
					throw new OverflowException();
				case 2:
					throw new NotStableException();
				default:
					break;
				}
				throw new Exception("Unknown exception.");
			}
		}
	}

	@Override
	public String getConf() {
		return ip;
	}

	@Override
	public void setConf(String conf) {
		this.ip = conf;
	}

	private static int getScalePortFromIp(String ip) {
		String ipPart[] = ip.split("\\.");
		if (ipPart.length == 4 && ipPart[3].matches("[0-9]+")) {
			return Integer.parseInt(ipPart[3]) + 2000;
		}
		return 0;
	}
}
