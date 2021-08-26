package com.mwg.api.commons;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import com.bss.commons.utils.OsCheck;
import com.bss.commons.utils.ShellCmd;

public class Ping {

	private final String host;

	  public static Ping host(String host) {
	    return new Ping(host);
	  }

	  private Ping(String host) {
	    this.host = host;
	  }

	  public boolean isReachable(int timeoutInMilis) throws Exception {
	    switch (OsCheck.getOSType()) {
	    case Windows:
	      return InetAddress.getByName(host).isReachable(timeoutInMilis);
	    case MacOS:
	    case Linux:
	      AtomicBoolean isOk = new AtomicBoolean(false);
	      ShellCmd.create().addCmd("ping -c 1 " + host).outputConsumer(line -> {
	        if (line.toLowerCase().contains("0% packet loss") || line.toLowerCase().contains("0.0% packet loss")) {
	          isOk.set(true);
	        }
	      }).exe(timeoutInMilis);
	      return isOk.get();
	    default:
	      break;
	    }
	    throw new Exception("UNSUPPORT PING ON THIS OS.");
	  }
	
}
