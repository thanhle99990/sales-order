package com.mwg.api.validation;

import com.mwg.api.entities.request.DemoPayload;
import com.mwg.api.entities.request.PostBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.GetByIdBlanketSalesOrderDetailPayload;
import com.mwg.api.entities.request.UpdateBlanketSalesOrderDetailPayload;
import com.mwg.api.posclient.Global;
import com.mwg.api.resources.I18n;
import java.sql.Timestamp;

public class RequestValidationHelper {

	@SuppressWarnings("null")
	private static String getMessage(String key) throws Exception {
		I18n.load(Global.locale);
		return I18n.get(key);
	}

	public static void validateRequest(DemoPayload request) throws Exception {
		if (request == null) {
			throw new Exception("requestBodyIsNull,storeIdCannotBeNull");
		}
	}
	public static void validateBlanketSalesOrderDetailRequest(PostBlanketSalesOrderDetailPayload request) throws Exception {
		if (request == null) {
			throw new Exception(getMessage("requestBodyIsNull"));
		}
		validateBlanketSalesOrderId(request.getBlanketSalesOrderId());
		validateProductId(request.getProductId());
		validateInventoryStatus(request.getInventoryStatus());
		validateQuantity(request.getQuantity());
		validateQuantityUnitId(request.getQuantityUnitId());
		validateStandaPrice(request.getStandaPrice());
		validateVat(request.getVat());
		validateNoVat(request.getNoVat());
		validateAdjusmentValue(request.getAdjusmentValue());
		validateAdjusmentUser(request.getAdjusmentUser());
		validateAdjusmentdate(request.getAdjusmentdate());
		validateAdjusmentContent(request.getAdjusmentContent());
		validateSalePrice(request.getSalePrice());
		validateCreatedUser(request.getCreatedUser());

	}


	private static void validateCreatedUser(String createdUser) throws Exception{
		if (createdUser == null || createdUser.trim().isEmpty()) {
			throw new Exception(getMessage("createdUserCannotBeNull"));
		}
	}

	private static void validateSalePrice(Double salePrice) throws Exception{
		if(salePrice == null) {
			throw new Exception(getMessage("salePriceCannotBeNull"));
		}

	}

	private static void validateAdjusmentContent(String adjusmentContent) throws Exception{
		if (adjusmentContent == null || adjusmentContent.trim().isEmpty()) {
			throw new Exception(getMessage("adjusmentContentCannotBeNull"));
		}
	}

	private static void validateAdjusmentdate(Timestamp adjusmentdate) throws Exception{
		if(adjusmentdate == null) {
			throw new Exception(getMessage("adjusmentdateCannotBeNull"));
		}
	}

	private static void validateAdjusmentUser(String adjusmentUser) throws Exception{
		if (adjusmentUser == null || adjusmentUser.trim().isEmpty()) {
			throw new Exception(getMessage("adjusmentUserCannotBeNull"));
		}
	}

	private static void validateAdjusmentValue(Double adjusmentValue) throws Exception{
		if(adjusmentValue == null) {
			throw new Exception(getMessage("adjusmentValueCannotBeNull"));
		}
	}

	private static void validateNoVat(Boolean noVat) throws Exception{
		if(noVat == null) {
			throw new Exception(getMessage("noVatCannotBeNull"));
		}

	}

	private static void validateVat(Integer vat) throws Exception{
		if(vat == null) {
			throw new Exception(getMessage("vatCannotBeNull"));
		}
	}

	private static void validateStandaPrice(Double standaPrice) throws Exception{
		if(standaPrice == null) {
			throw new Exception(getMessage("standaPriceCannotBeNull"));
		}
	}

	private static void validateQuantityUnitId(Integer quantityUnitId) throws Exception{
		if(quantityUnitId == null) {
			throw new Exception(getMessage("quantityUnitIdCannotBeNull"));
		}
	}

	private static void validateQuantity(Double quantity) throws Exception{
		if(quantity == null) {
			throw new Exception(getMessage("quantityCannotBeNull"));
		}
	}

	private static void validateInventoryStatus(Integer inventoryStatus) throws Exception{
		if(inventoryStatus == null) {
			throw new Exception(getMessage("inventoryStatusCannotBeNull"));
		}
	}

	private static void validateProductId(String productId) throws Exception{
		if (productId == null || productId.trim().isEmpty()) {
			throw new Exception(getMessage("productIdCannotBeNull"));
		}
	}

	private static void validateBlanketSalesOrderId(String blanketSalesOrderId) throws Exception {
		if (blanketSalesOrderId == null || blanketSalesOrderId.trim().isEmpty()) {
			throw new Exception(getMessage("blanketSalesOrderIdCannotBeNull"));
		}
	}


	public static void validateBlanketSalesOrderDetailRequest(UpdateBlanketSalesOrderDetailPayload request) throws Exception {
		if (request == null) {
			throw new Exception(getMessage("requestBodyIsNull"));
		}
		validateBlanketSalesOrderDetailId(request.getBlanketSalesOrderDetailId());
		validateQuantity(request.getQuantity());
		validateQuantityUnitId(request.getQuantityUnitId());
		validateStandaPrice(request.getStandaPrice());
		validateVat(request.getVat());
		validateNoVat(request.getNoVat());
		validateAdjusmentValue(request.getAdjusmentValue());
		validateAdjusmentUser(request.getAdjusmentUser());
		validateAdjusmentdate(request.getAdjusmentdate());
		validateAdjusmentContent(request.getAdjusmentContent());
		validateSalePrice(request.getSalePrice());
		validateCreatedUser(request.getUpdatedUser());
	}

	private static void validateBlanketSalesOrderDetailId(Integer blanketSalesOrderDetailId) throws Exception{
		if(blanketSalesOrderDetailId == null) {
			throw new Exception(getMessage("blanketSalesOrderDetailIdCannotBeNull"));
		}
	}

	public static void validateBlanketSalesOrderDetailRequest(GetByIdBlanketSalesOrderDetailPayload request) throws Exception {
		if (request == null) {
			throw new Exception(getMessage("requestBodyIsNull"));
		}
		validateBlanketSalesOrderId(request.getBlanketSalesOrderId());
	}

}
