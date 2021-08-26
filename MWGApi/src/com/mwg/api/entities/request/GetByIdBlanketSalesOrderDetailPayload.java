package com.mwg.api.entities.request;

import com.mwg.api.entities.APIRequest;

public class GetByIdBlanketSalesOrderDetailPayload extends APIRequest {
    private String blanketSalesOrderId;

    public String getBlanketSalesOrderId() {
        return blanketSalesOrderId;
    }

    public void setBlanketSalesOrderId(String _blanketSalesOrderId) {
        this.blanketSalesOrderId = _blanketSalesOrderId;
    }
}
