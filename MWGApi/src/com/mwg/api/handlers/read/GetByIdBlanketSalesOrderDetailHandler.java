package com.mwg.api.handlers.read;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.mwg.api.dbresult.RGetByIdBlanketSalesOrderDetail;
import com.mwg.api.entities.request.GetByIdBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.response.GetByIdBlanketSalesOrderDetailResponseData;
import com.mwg.api.handlers.APIHandler;
import com.mwg.api.posclient.Global;
import com.mwg.api.resources.I18n;
import com.mwg.api.service.BlanketSalesOrderDetailService;
import com.mwg.api.validation.RequestValidationHelper;

public class GetByIdBlanketSalesOrderDetailHandler
        extends APIHandler<GetByIdBlanketSalesOrderDetailPayload,GetByIdBlanketSalesOrderDetailResponseData>{

    static {
        System.setProperty("file.encoding", "UTF-8");
        TimeZone.setDefault(TimeZone.getTimeZone(System.getProperty("time.zone", "Asia/Ho_Chi_Minh")));
    }

    public GetByIdBlanketSalesOrderDetailHandler() {
        super(GetByIdBlanketSalesOrderDetailPayload.class);
    }

    @Override
    protected GetByIdBlanketSalesOrderDetailResponseData handle(GetByIdBlanketSalesOrderDetailPayload request)
            throws Exception {
        RequestValidationHelper.validateBlanketSalesOrderDetailRequest(request);
        GetByIdBlanketSalesOrderDetailResponseData response = new GetByIdBlanketSalesOrderDetailResponseData();
        List<RGetByIdBlanketSalesOrderDetail> listQuery = new ArrayList<>();
        listQuery = BlanketSalesOrderDetailService.getByIdBlanketSalesOrderDetails(request);
        List<Map<String, Object>> data = listQuery.stream().map(RGetByIdBlanketSalesOrderDetail::getValueMap).collect(Collectors.toList());
        response.setData(data);
        I18n.load(Global.locale);
        String message = I18n.get("getByIdBlanketSalesOrderDetail");
        response.setMessage(message);
        return response;
    }

}
