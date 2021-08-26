package com.mwg.api.configs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import com.google.gson.JsonObject;
import com.mwg.api.ApplicationService;
import com.mwg.api.utils.IOUtils;

public class LoggableDispatcherServlet extends DispatcherServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (!(request instanceof ContentCachingRequestWrapper)) {
      request = new ContentCachingRequestWrapper(request);
    }
    if (!(response instanceof ContentCachingResponseWrapper)) {
      response = new ContentCachingResponseWrapper(response);
    }
    HandlerExecutionChain handler = getHandler(request);
    try {
      super.doDispatch(request, response);
    } finally {
      log(request, response, handler);
      updateResponse(response);
    }
  }

  private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) throws Exception {
    if (responseToCache.getStatus() != 200) {
      JsonObject log = new JsonObject();
      log.addProperty("path", requestToCache.getRequestURI());
      log.addProperty("timestamp", System.currentTimeMillis());
      log.addProperty("httpStatus", responseToCache.getStatus());
      log.addProperty("httpMethod", requestToCache.getMethod());
      log.addProperty("clientIp", requestToCache.getRemoteAddr());
      log.addProperty("requestData", getRequestPayload(requestToCache));
      log.addProperty("responseData", getResponsePayload(responseToCache));
      log.addProperty("javaMethod", (handler != null) ? handler.toString() : null);
      ApplicationService.ins().getLogWriter().info(log.toString());
    }
  }

  private String getResponsePayload(HttpServletResponse response) {
    try {
      ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
      if (wrapper != null) {
        return new String(wrapper.getContentAsByteArray(), "UTF-8");
      }
    } catch (Exception e) {
    }
    return null;
  }

  private String getRequestPayload(HttpServletRequest request) {
    try {
      return new String(IOUtils.toByteArray(request.getInputStream()));
    } catch (Exception e) {
      /////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    return null;
  }

  private void updateResponse(HttpServletResponse response) throws IOException {
    WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class).copyBodyToResponse();
    //////////////////////////////////////////////////////////////////////////////////////////////////////
  }

}
