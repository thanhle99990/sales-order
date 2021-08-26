package com.mwg.api.entities;

public class APIResponse {
  private final ResponseCode responseCode;
  private ResponseData responseData;

  public APIResponse(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  public ResponseCode getResponseCode() {
    return this.responseCode;
  }

  public ResponseData getResponseData() {
    return this.responseData;
  }

  public APIResponse setResponseData(ResponseData responseData) {
    this.responseData = responseData;
    return this;
  }

}
