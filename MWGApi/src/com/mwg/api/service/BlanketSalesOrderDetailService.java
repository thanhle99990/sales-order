package com.mwg.api.service;

import java.util.List;

import com.mwg.api.dbresult.RGetByIdBlanketSalesOrderDetail;
import com.mwg.api.entities.request.PostBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.GetByIdBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.UpdateBlanketSalesOrderDetailPayload;
import com.mwg.api.repository.BlanketSalesOrderDetailRepository;

public class BlanketSalesOrderDetailService {
    private static BlanketSalesOrderDetailService blanketSalesOrderDetailService = null;

    public static BlanketSalesOrderDetailService ins() {
        if (blanketSalesOrderDetailService == null) {
            blanketSalesOrderDetailService = new BlanketSalesOrderDetailService();
        }
        return blanketSalesOrderDetailService;
    }
    public static List<RGetByIdBlanketSalesOrderDetail> getByIdBlanketSalesOrderDetails(
            GetByIdBlanketSalesOrderDetailPayload request) throws Exception {
        return BlanketSalesOrderDetailRepository.getByStatusBlanketSalesOrdersDetail(request);
    }
    public static void addBlanketSalesOrderDetail(PostBlanketSalesOrderDetailPayload request) throws Exception {
        BlanketSalesOrderDetailRepository.addBlanketSalesOrderDetail(request);
    }

    public static void updateBlanketSalesOrder(UpdateBlanketSalesOrderDetailPayload request) throws Exception {
        BlanketSalesOrderDetailRepository.updateBlanketSalesOrderDetail(request);
    }



}
