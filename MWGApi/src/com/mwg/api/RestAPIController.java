package com.mwg.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mwg.api.configs.DBConnConfig;
import com.mwg.api.configs.DbConfig;
import com.mwg.api.configs.Permission;
import com.mwg.api.entities.APIResponse;
import com.mwg.api.entities.ResponseCode;
import com.mwg.api.entities.ResponseData;
import com.mwg.api.entities.response.HelthCheckResponseData;
import com.mwg.api.handlers.APIHandler;
import com.mwg.api.utils.Utils;

@RestController
@RequestMapping("/api/{ver}")
public class RestAPIController {
	
	@PostMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public APIResponse APILoyalty(@PathVariable("ver") String ver, @PathVariable("func") String func, @RequestBody String payload, HttpServletRequest req, HttpServletResponse res) {
		APIResponse response = null;
		
		final long startTime = System.currentTimeMillis();
		try {
			String dbConfigPath = req.getServletContext().getRealPath("/WEB-INF/db-config.xml");
			addDbConfig(dbConfigPath);
			
			this.setHeader(res);
			final APIHandler<?, ?> handler = getHandler(func, ver);
			if (handler != null) {
				response = new APIResponse(new ResponseCode(0, "OK", "")).setResponseData(handler.handle(payload));
			} else {
				response = new APIResponse(new ResponseCode(-2, "FUNCTION_NOT_FOUND", ""));
			}
		} catch (Exception ex) {
			logError(ex);
			response = new APIResponse(new ResponseCode(-1024, ex.getMessage(), Utils.dumpException(ex)));
		}
		response.getResponseCode().setExeInMills(System.currentTimeMillis() - startTime).setUsername("username").setVersion(ver).setFunction(func);
		return response;
	}
	
	@PutMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public APIResponse putAPILoyalty(@PathVariable("ver") String ver, @PathVariable("func") String func, @RequestBody String payload, HttpServletRequest req, HttpServletResponse res) {
		APIResponse response = null;
		final long startTime = System.currentTimeMillis();
		
		try {
			String dbConfigPath = req.getServletContext().getRealPath("/WEB-INF/db-config.xml");
			addDbConfig(dbConfigPath);
			
			this.setHeader(res);
			final APIHandler<?, ?> handler = getHandler(func, ver);
			if (handler != null) {
				response = new APIResponse(new ResponseCode(0, "OK", "")).setResponseData(handler.handle(payload));
			} else {
				response = new APIResponse(new ResponseCode(-2, "FUNCTION_NOT_FOUND", ""));
			}
		} catch (Exception ex) {
			logError(ex);
			response = new APIResponse(new ResponseCode(-1024, ex.getMessage(), Utils.dumpException(ex)));
		}
		response.getResponseCode().setExeInMills(System.currentTimeMillis() - startTime).setUsername("username").setVersion(ver).setFunction(func);
		return response;
	}
	
	@GetMapping(value = "/healthcheck")
	public APIResponse getAPILoyalty1(@PathVariable("ver") String ver, HttpServletRequest req, HttpServletResponse res) {
		APIResponse response = null;
		final long startTime = System.currentTimeMillis();
		HelthCheckResponseData resData = new HelthCheckResponseData();
		resData.setMessage("OK");
		response = new APIResponse(new ResponseCode(0, "OK", "")).setResponseData(resData);
		response.getResponseCode().setExeInMills(System.currentTimeMillis() - startTime).setUsername("username").setVersion(ver);
		return response;
	}
	
	@GetMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public APIResponse getAPILoyalty(@PathVariable("ver") String ver, @PathVariable("func") String func, @RequestBody String payload, HttpServletRequest req, HttpServletResponse res) {
        APIResponse response = null;
        final long startTime = System.currentTimeMillis();
        try {
            String dbConfigPath = req.getServletContext().getRealPath("/WEB-INF/db-config.xml");
            addDbConfig(dbConfigPath);
            
            this.setHeader(res);
            final APIHandler<?, ?> handler = getHandler(func, ver);
            if (handler != null) {
                response = new APIResponse(new ResponseCode(0, "OK", "")).setResponseData(handler.handle(payload));
            } else {
                response = new APIResponse(new ResponseCode(-2, "FUNCTION_NOT_FOUND", ""));
            }
        } catch (Exception ex) {
            logError(ex);
            response = new APIResponse(new ResponseCode(-1024, ex.getMessage(), Utils.dumpException(ex)));
        }
        response.getResponseCode().setExeInMills(System.currentTimeMillis() - startTime).setUsername("username").setVersion(ver).setFunction(func);
        return response;
    }
	
	@DeleteMapping(value = "/{func}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public APIResponse deleteAPILoyalty(@PathVariable("ver") String ver, @PathVariable("func") String func, @RequestBody String payload, HttpServletRequest req, HttpServletResponse res) {
		APIResponse response = null;
		final long startTime = System.currentTimeMillis();
		try {
			String dbConfigPath = req.getServletContext().getRealPath("/WEB-INF/db-config.xml");
			addDbConfig(dbConfigPath);
			
			this.setHeader(res);
			final APIHandler<?, ?> handler = getHandler(func, ver);
			if (handler != null) {
				response = new APIResponse(new ResponseCode(0, "OK", "")).setResponseData(handler.handle(payload));
			} else {
				response = new APIResponse(new ResponseCode(-2, "FUNCTION_NOT_FOUND", ""));
			}
		} catch (Exception ex) {
			logError(ex);
			response = new APIResponse(new ResponseCode(-1024, ex.getMessage(), Utils.dumpException(ex)));
		}
		response.getResponseCode().setExeInMills(System.currentTimeMillis() - startTime).setUsername("username").setVersion(ver).setFunction(func);
		return response;
	}

	private void setHeader(HttpServletResponse res) throws Exception {
		res.addHeader("Content-Type", "application/json; charset=utf-8");
		res.setHeader("NodeName", ApplicationService.getHostname());
	}

	private boolean checkPermission(String user, String func, String ver) throws Exception {
		Permission per = ApplicationService.ins().getPermissions().get(user);
		return (per != null) ? per.hasPermission(func) : false;
	}

	private APIHandler<?, ?> getHandler(String func, String ver) throws Exception {
		return ApplicationService.ins().getHandlers().get(String.format("%s/%s", ver, func));
	}
	
	private void logError(Exception ex) {
		try {
			ApplicationService.ins().getLogWriter().error(ex.getMessage(), ex);
		} catch (Exception e) {
      ex.printStackTrace(); 
		}
	}
	
	private void addDbConfig(String configPath) throws ParserConfigurationException, SAXException, IOException {
		if (ApplicationService.getDBConnConfigs() != null) {
			return;
		}
		
		///////////LAY CONN TU CONFIG.CONF////////////////////
		try {
			DBConnConfig dBConnConfigData = DbConfig.getDBConnConfig();
			if(!dBConnConfigData.getConnStr().isEmpty() && !dBConnConfigData.getConnStr().equals("")) {
				ApplicationService.addDBConnConfig(dBConnConfigData);
				return;
			}
		} catch (Exception e) {
		}
		////////////////////////////////////////////////////////
		
		File fXmlFile = new File(configPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("pgConnConfig");
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				DBConnConfig dBConnConfig = new DBConnConfig(
						eElement.getElementsByTagName("connStr").item(0).getTextContent(),
						eElement.getElementsByTagName("user").item(0).getTextContent(),
						eElement.getElementsByTagName("pass").item(0).getTextContent(),
						Integer.parseInt(eElement.getElementsByTagName("maxConn").item(0).getTextContent())
				);
				
				//////DB STANDBY//////////////////////////////////////////////////////
				String connStrSTB = "";
				if(eElement.getElementsByTagName("connStrSTB").getLength() > 0) {
					connStrSTB = eElement.getElementsByTagName("connStrSTB").item(0).getTextContent();
					if(connStrSTB != null && !connStrSTB.isEmpty()) {
						dBConnConfig.setConnStrSTB(connStrSTB);
					}
				}
				if(connStrSTB == null || connStrSTB.isEmpty()) {
					dBConnConfig.setConnStrSTB(dBConnConfig.getConnStr());
				}
				String userSTB = "";
				if(eElement.getElementsByTagName("userSTB").getLength() > 0) {
					userSTB = eElement.getElementsByTagName("userSTB").item(0).getTextContent();
					if(userSTB != null && !userSTB.isEmpty()) {
						dBConnConfig.setUserSTB(userSTB);
					}
				}
				if(userSTB == null || userSTB.isEmpty()) {
					dBConnConfig.setUserSTB(dBConnConfig.getUser());
				}
				String passSTB = "";
				if(eElement.getElementsByTagName("passSTB").getLength() > 0) {
					passSTB = eElement.getElementsByTagName("passSTB").item(0).getTextContent();
					if(passSTB != null && !passSTB.isEmpty()) {
						dBConnConfig.setPassSTB(passSTB);
					}
				}
				if(passSTB == null || passSTB.isEmpty()) {
					dBConnConfig.setPassSTB(dBConnConfig.getPass());
				}
				String maxConnSTB = "";
				if(eElement.getElementsByTagName("maxConnSTB").getLength() > 0) {
					maxConnSTB = eElement.getElementsByTagName("maxConnSTB").item(0).getTextContent();
					if(maxConnSTB != null && !maxConnSTB.isEmpty()) {
						dBConnConfig.setMaxConnSTB(Integer.parseInt(maxConnSTB));
					}
				}
				if(maxConnSTB == null || maxConnSTB.isEmpty()) {
					dBConnConfig.setMaxConnSTB(dBConnConfig.getMaxConn());
				}
				/////////////////////////////////////////////////////
				
				ApplicationService.addDBConnConfig(dBConnConfig);
			}
		}
	}
}