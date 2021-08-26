package com.mwg.jhttp;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class HttpService {

  private final HttpServer httpserver;
  
  private Executor executor = Executors.newWorkStealingPool(16);

  private final List<IServHandler> handlers = new ArrayList<IServHandler>();
  
  public HttpService(int port, List<IServHandler> handlers) throws Exception {
    this.httpserver = HttpServer.create(new InetSocketAddress(port), 128);
    if(handlers!=null) {
    	this.handlers.addAll(handlers);
    }
  }
  
  public HttpService addHttpHandler(IServHandler handler) {
	  this.handlers.add(handler);
	  return this;
  }
  
  public HttpService setParallelism(int parallelism) {
  	executor = Executors.newWorkStealingPool(parallelism);
  	return this;
  }

  public HttpService start() {
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

  public HttpService stop() {
    httpserver.stop(0);
    return this;
  }
  
}
