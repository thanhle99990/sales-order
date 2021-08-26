package com.mwg.api.entities.response;

import com.mwg.api.entities.ResponseData;

public class HelthCheckResponseData extends ResponseData {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
