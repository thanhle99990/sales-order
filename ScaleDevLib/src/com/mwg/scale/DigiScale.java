package com.mwg.scale;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.mwg.scale.exeptions.NotStableException;
import com.mwg.scale.utils.Utils;

public class DigiScale implements IScale {
	
	private final static String command = "05";
		
	private String ip = "127.0.0.1";

	private final int port = 4000;
	
  @Override
  public long getValueInGam() throws Exception {
		int len = 9;
		byte[] buff = new byte[len];
		try (Socket socket = Utils.createSocket(ip, port, 1000)) {
			try (OutputStream out = socket.getOutputStream(); InputStream in = socket.getInputStream()) {
				out.write(Utils.hexString2ByteArray(command));
				out.flush();
				int byteRead = 0;
				if ((byteRead = Utils.readInputStreamWithTimeout(in, buff, IScale.scaleTimeoutMs)) < len) {
					if (byteRead == 0) {
						throw new Exception(String.format("Timed out in %d ms",IScale.scaleTimeoutMs));
					}
					else {
						throw new NotStableException();
					}
				}
				try {
					// RESPONSE FORMAT 000.542KG //////////////////////////////////////////////////////////////
					String response = new String(buff, "ASCII").trim();
					Double weight = Double.valueOf(response.substring(0, response.length() - 2)) * 1000D;
					return weight.longValue();
				}
				catch (Exception ex) {
					throw new NotStableException();
				}
			}
		}
  }

  @Override
  public String getConf() {
    return this.ip;
  }

  @Override
  public void setConf(String conf) {
     this.ip = conf;
  }

}
