package com.mwg.jhttp;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class HttpsService {

  private final HttpsServer httpserver;
  
  private Executor executor = Executors.newWorkStealingPool(16);

  private final List<IServHandler> handlers = new ArrayList<IServHandler>();
  
  public HttpsService(int port, List<IServHandler> handlers) throws Exception {
    this.httpserver = HttpsServer.create(new InetSocketAddress(port), 128);
    if(handlers!=null) {
    	this.handlers.addAll(handlers);
    }
  }

  public HttpsService setJksStore(InputStream jksInputStream, String password) throws Exception {
    this.httpserver.setHttpsConfigurator(new HttpsConfigurator(getSSSlCtx(jksInputStream, password)) {
      public void configure (HttpsParameters params) {
      }
    });
    return this;
  }
  
  public HttpsService addHttpHandler(IServHandler handler) {
	  this.handlers.add(handler);
	  return this;
  }
  
  public HttpsService setParallelism(int parallelism) {
  	executor = Executors.newWorkStealingPool(parallelism);
  	return this;
  }

  public HttpsService start() {
    handlers.forEach(handler -> {
      ServHandler srvHandler = new ServHandler(handler);
      httpserver.createContext(srvHandler.getPath(), srvHandler.getHandler()).getFilters().add(new ParameterFilter());
    });
    ServHandler usage = new ServHandler(new UsageHandler(handlers));
    httpserver.createContext(usage.getPath(), usage.getHandler()).getFilters().add(new ParameterFilter());
    httpserver.setExecutor(executor);
    httpserver.start();
    return this;
  }

  public HttpsService stop() {
    httpserver.stop(0);
    return this;
  }

  private static SSLContext getSSSlCtx(InputStream jksInputStream, String password) throws Exception {
    SSLContext ctx = SSLContext.getInstance("SSL");
    KeyStore keystore = KeyStore.getInstance("JKS");
    keystore.load(jksInputStream, password.toCharArray());
    TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    trustMgrFactory.init(keystore);
    keyMgrFactory.init(keystore, password.toCharArray());
    ctx.init(keyMgrFactory.getKeyManagers(), trustMgrFactory.getTrustManagers(), new SecureRandom());
    return ctx;
  }

}
