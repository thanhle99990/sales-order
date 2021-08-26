package com.mwg.api.commons;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bss.commons.httpclient.ByteArrResponseHandler;
import com.bss.commons.httpclient.HttpClientPost;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SoapUtils {

	private static int defaultTimeoutInSecond = 15;

	public static Document sendSoapRequest(String soapRequest, String soapUrl) throws Exception {
		return sendSoapRequest(soapRequest, soapUrl, defaultTimeoutInSecond);
	}

	public static Document sendSoapRequest(String soapRequest, String soapUrl, int timeoutInSec) throws Exception {
		try (HttpClientPost post = new HttpClientPost(soapUrl, timeoutInSec)) {
			post.addHeader(new BasicHeader("Content-Type", "text/xml; charset=utf-8"));
			post.setEntity(new StringEntity(soapRequest));
			byte[] xmlData = new ByteArrResponseHandler().handleResponse(post.execute());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new ByteArrayInputStream(xmlData));
			doc.getDocumentElement().normalize();
			return doc;
		}
	}
	
	public static JsonElement GetJsonByPath(JsonElement element, String path) throws Exception {
		if (path == null || path.trim().isEmpty()) {
			return element;
		}
		if (element == null || !element.isJsonObject()) {
			throw new Exception("Element must be JsonObject.");
		}
		String memberName = path.split("/")[0].trim();
		JsonElement result = element.getAsJsonObject().get(memberName);
		if (result == null || memberName.equals(path.trim())) {
			return result;
		}
		return GetJsonByPath(result, path.trim().substring(memberName.length() + 1));
	}
	
	public static JsonElement Node2Json(Node node, boolean convert) {
		if (node != null) {
			if (node.getChildNodes().getLength() == 1 && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
				String content = node.getFirstChild().getTextContent();
				if (content == null) {
					return JsonNull.INSTANCE;
				} else {
					if (convert) {
						try {
							return new JsonPrimitive(Long.parseLong(content));
						} catch (Exception ex) {
						}
						try {
							return new JsonPrimitive(Double.parseDouble(content));
						} catch (Exception ex) {
						}
						if (content.toUpperCase().matches("TRUE|FALSE")) {
							return new JsonPrimitive(Boolean.parseBoolean(content));
						}
					}
					return new JsonPrimitive(content);
				}
			} else {
				NodeList childs = node.getChildNodes();
				JsonObject jsonObject = new JsonObject();
				for (int i = 0; i < childs.getLength(); i++) {
					JsonElement childElemOld = jsonObject.get(childs.item(i).getNodeName());
					JsonElement childElemNew = Node2Json(childs.item(i), convert);
					if (childElemOld == null) {
						jsonObject.add(childs.item(i).getNodeName(), childElemNew);
					} else {
						if (childElemOld.isJsonArray()) {
							childElemOld.getAsJsonArray().add(childElemNew);
						} else {
							JsonArray arr = new JsonArray();
							arr.add(childElemOld);
							arr.add(childElemNew);
							jsonObject.add(childs.item(i).getNodeName(), arr);
						}
					}
				}
				return jsonObject;
			}
		}
		return null;
	}
	
}
