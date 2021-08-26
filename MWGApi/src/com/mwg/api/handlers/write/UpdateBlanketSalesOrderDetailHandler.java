package com.mwg.api.handlers.write;

import com.mwg.api.entities.request.UpdateBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.response.UpdateBlanketSalesOrderDetailResponseData;
import com.mwg.api.handlers.APIHandler;
import com.mwg.api.posclient.Global;
import com.mwg.api.resources.I18n;
import com.mwg.api.service.BlanketSalesOrderDetailService;
import com.mwg.api.validation.RequestValidationHelper;

public class UpdateBlanketSalesOrderDetailHandler extends APIHandler<UpdateBlanketSalesOrderDetailPayload, UpdateBlanketSalesOrderDetailResponseData>{

    public UpdateBlanketSalesOrderDetailHandler() {
        super(UpdateBlanketSalesOrderDetailPayload.class);
    }

    @Override
    protected UpdateBlanketSalesOrderDetailResponseData handle(UpdateBlanketSalesOrderDetailPayload request) throws Exception {
        RequestValidationHelper.validateBlanketSalesOrderDetailRequest(request);
        UpdateBlanketSalesOrderDetailResponseData response = new UpdateBlanketSalesOrderDetailResponseData();
        BlanketSalesOrderDetailService.updateBlanketSalesOrder(request);
        I18n.load(Global.locale);
        String message = I18n.get("updateBlanketSalesOrderDetail");
        response.setMessage(message);
        return response;
    }

}
