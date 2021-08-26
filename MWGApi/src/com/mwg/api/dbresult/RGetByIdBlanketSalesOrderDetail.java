package com.mwg.api.dbresult;

public class RGetByIdBlanketSalesOrderDetail extends DBResult {
    public RGetByIdBlanketSalesOrderDetail() {
        addField("blanketsalesorderid", DataType.STRING, null);
        addField("blanketsalesorderdetailid", DataType.INTEGER, 0);
        addField("outputtypeid", DataType.INTEGER, 0);
        addField("productid", DataType.STRING, null);
        addField("productname", DataType.STRING, null);

        addField("isallowdecimal", DataType.BOOLEAN, null);
        addField("inventorystatus", DataType.INTEGER, 0);
        addField("quantity", DataType.DOUBLE, 0);
        addField("quantityunitid", DataType.INTEGER, 0);
        addField("standaprice", DataType.DOUBLE, 0);

        addField("vat", DataType.INTEGER, 0);
        addField("novat", DataType.BOOLEAN, null);
        addField("adjusmentvalue", DataType.DOUBLE, 0);
        addField("adjusmentuser", DataType.STRING, null);
        addField("adjusmentdate", DataType.TIMESTAMP, null);

        addField("adjusmentcontent", DataType.STRING, null);
        addField("saleprice", DataType.DOUBLE, 0);
        addField("inputdate", DataType.TIMESTAMP, null);

    }

}
