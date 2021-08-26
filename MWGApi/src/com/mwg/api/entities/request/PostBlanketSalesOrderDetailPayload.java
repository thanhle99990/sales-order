package com.mwg.api.entities.request;

import java.sql.Timestamp;

import com.mwg.api.entities.APIRequest;


public class PostBlanketSalesOrderDetailPayload extends APIRequest{
    private String blanketSalesOrderId;
    private String productId;
    private Integer inventoryStatus;
    private Double quantity;
    private Integer quantityUnitId;
    private Double standaPrice;
    private Integer vat;
    private Boolean noVat;
    private Double adjusmentValue;
    private String adjusmentUser;
    private Timestamp adjusmentdate;
    private String adjusmentContent;
    private Double salePrice;
    private String createdUser;

    public String getBlanketSalesOrderId() {
        return blanketSalesOrderId;
    }

    public void setBlanketSalesOrderId(String blanketSalesOrderId) {
        this.blanketSalesOrderId = blanketSalesOrderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(Integer inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUnitId() {
        return quantityUnitId;
    }

    public void setQuantityUnitId(Integer quantityUnitId) {
        this.quantityUnitId = quantityUnitId;
    }

    public Double getStandaPrice() {
        return standaPrice;
    }

    public void setStandaPrice(Double standaPrice) {
        this.standaPrice = standaPrice;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Boolean getNoVat() {
        return noVat;
    }

    public void setNoVat(Boolean noVat) {
        this.noVat = noVat;
    }

    public Double getAdjusmentValue() {
        return adjusmentValue;
    }

    public void setAdjusmentValue(Double adjusmentValue) {
        this.adjusmentValue = adjusmentValue;
    }

    public String getAdjusmentUser() {
        return adjusmentUser;
    }

    public void setAdjusmentUser(String adjusmentUser) {
        this.adjusmentUser = adjusmentUser;
    }

    public Timestamp getAdjusmentdate() {
        return adjusmentdate;
    }

    public void setAdjusmentdate(Timestamp adjusmentdate) {
        this.adjusmentdate = adjusmentdate;
    }

    public String getAdjusmentContent() {
        return adjusmentContent;
    }

    public void setAdjusmentContent(String adjusmentContent) {
        this.adjusmentContent = adjusmentContent;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
