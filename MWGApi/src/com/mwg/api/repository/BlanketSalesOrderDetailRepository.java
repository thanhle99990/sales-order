package com.mwg.api.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mwg.api.configs.PgDBConfig;
import com.mwg.api.dbresult.DBResult;
import com.mwg.api.dbresult.RGetByIdBlanketSalesOrderDetail;
import com.mwg.api.entities.request.GetByIdBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.PostBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.UpdateBlanketSalesOrderDetailPayload;

public class BlanketSalesOrderDetailRepository {
    private static BlanketSalesOrderDetailRepository blanketSalesOrderDetailRepository = null;

    public static BlanketSalesOrderDetailRepository ins() {
        if (blanketSalesOrderDetailRepository == null) {
            blanketSalesOrderDetailRepository = new BlanketSalesOrderDetailRepository();
        }
        return blanketSalesOrderDetailRepository;
    }

    public static List<RGetByIdBlanketSalesOrderDetail> getByStatusBlanketSalesOrdersDetail(
            GetByIdBlanketSalesOrderDetailPayload request) throws Exception {
        List<RGetByIdBlanketSalesOrderDetail> result = new ArrayList<>();
        if (request != null) {
            String callString = "{ ? = call salesmanagement.sm_blanketsalesorderdetail_getbyid( ? ) }";
            try (Connection connection = PgDBConfig.getDbConn().getConnection();
                 CallableStatement proc = connection.prepareCall(callString);) {
                proc.registerOutParameter(1, Types.OTHER);
                proc.setString(2, request.getBlanketSalesOrderId());
                proc.execute();
                ResultSet resultSet = (ResultSet) proc.getObject(1);
                while (resultSet.next()) {
                    RGetByIdBlanketSalesOrderDetail obj = new RGetByIdBlanketSalesOrderDetail();
                    DBResult.fillData(obj, resultSet);
                    result.add(obj);
                }
                connection.commit();
            }
        }
        return result;
    }

    public static void addBlanketSalesOrderDetail(PostBlanketSalesOrderDetailPayload request) throws Exception {
        if (request != null) {
            String callString = "{ ? = call salesmanagement.sm_blanketsalesorderdetail_add(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
            try (Connection connection = PgDBConfig.getDbConn().getConnection();
                 CallableStatement proc = connection.prepareCall(callString);) {
                proc.registerOutParameter(1, Types.VARCHAR);
                proc.setString(2, request.getBlanketSalesOrderId());
                proc.setString(3, request.getProductId());
                proc.setInt(4, request.getInventoryStatus());
                proc.setDouble(5, request.getQuantity());
                proc.setInt(6, request.getQuantityUnitId());
                proc.setDouble(7, request.getStandaPrice());
                proc.setInt(8, request.getVat());
                proc.setBoolean(9, request.getNoVat());
                proc.setDouble(10, request.getAdjusmentValue());
                proc.setString(11, request.getAdjusmentUser());
                proc.setTimestamp(12, request.getAdjusmentdate());
                proc.setString(13, request.getAdjusmentContent());
                proc.setDouble(14, request.getSalePrice());
                proc.setString(15, request.getCreatedUser());
                proc.execute();
                connection.commit();
            }
        }
    }

    public static void updateBlanketSalesOrderDetail(UpdateBlanketSalesOrderDetailPayload request) throws Exception {
        if (request != null) {
            String callString = "{call salesmanagement.sm_blanketsalesorderdetail_upd(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

            try (Connection connection = PgDBConfig.getDbConn().getConnection();
                 CallableStatement proc = connection.prepareCall(callString);) {

                proc.setInt(1, request.getBlanketSalesOrderDetailId());
                proc.setDouble(2, request.getQuantity());
                proc.setInt(3, request.getQuantityUnitId());
                proc.setDouble(4, request.getStandaPrice());
                proc.setInt(5, request.getVat());
                proc.setBoolean(6, request.getNoVat());
                proc.setDouble(7, request.getAdjusmentValue());
                proc.setString(8, request.getAdjusmentUser());
                proc.setTimestamp(9, request.getAdjusmentdate());
                proc.setString(10, request.getAdjusmentContent());
                proc.setDouble(11, request.getSalePrice());
                proc.setString(12, request.getUpdatedUser());
                proc.execute();
                connection.commit();

            }
        }
    }


}
