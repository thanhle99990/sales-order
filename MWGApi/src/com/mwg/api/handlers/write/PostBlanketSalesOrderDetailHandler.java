package com.mwg.api.handlers.write;

import com.mwg.api.entities.request.PostBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.response.PostBlanketSalesOrderDetailResponseData;
import com.mwg.api.handlers.APIHandler;
import com.mwg.api.posclient.Global;
import com.mwg.api.resources.I18n;
import com.mwg.api.service.BlanketSalesOrderDetailService;
import com.mwg.api.validation.RequestValidationHelper;

public class PostBlanketSalesOrderDetailHandler
        extends APIHandler<PostBlanketSalesOrderDetailPayload, PostBlanketSalesOrderDetailResponseData> {

    public PostBlanketSalesOrderDetailHandler() {
        super(PostBlanketSalesOrderDetailPayload.class);
    }

    @Override
    protected PostBlanketSalesOrderDetailResponseData handle(PostBlanketSalesOrderDetailPayload request) throws Exception {
        RequestValidationHelper.validateBlanketSalesOrderDetailRequest(request);
        PostBlanketSalesOrderDetailResponseData response = new PostBlanketSalesOrderDetailResponseData();
        BlanketSalesOrderDetailService.addBlanketSalesOrderDetail(request);
        I18n.load(Global.locale);
        String message = I18n.get("postBlanketSalesOrderDetail");
        response.setMessage(message);
        return response;
    }

}
