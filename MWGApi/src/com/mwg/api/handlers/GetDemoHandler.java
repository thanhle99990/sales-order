package com.mwg.api.handlers;


import java.util.TimeZone;
import com.mwg.api.entities.request.DemoPayload;
import com.mwg.api.entities.response.DemoResponseData;
import com.mwg.api.validation.RequestValidationHelper;

public class GetDemoHandler extends APIHandler<DemoPayload, DemoResponseData> {

	static {
		System.setProperty("file.encoding", "UTF-8");
		TimeZone.setDefault(TimeZone.getTimeZone(System.getProperty("time.zone", "Asia/Ho_Chi_Minh")));
	}

	public GetDemoHandler() {
		super(DemoPayload.class);
	}

	@SuppressWarnings("unused")
	@Override
	protected synchronized DemoResponseData handle(DemoPayload request) throws Exception {
		// Validate request
		RequestValidationHelper.validateRequest(request);
		// ======================================================================
		DemoResponseData response = new DemoResponseData();

		
		return response;
	}
}