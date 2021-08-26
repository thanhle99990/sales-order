package com.mwg.api.Mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MwgMapper {

	private static MwgMapper mwgMapper = null;
	private ModelMapper mapper = null;

	public MwgMapper() {
		mapper = new ModelMapper();
		// use strict to prevent over eager matching (happens with ID fields)
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
	}

	// static method to create instance of Singleton class
	public static MwgMapper getInstance() {
		if (mwgMapper == null) {
			mwgMapper = new MwgMapper();
		}

		return mwgMapper;
	}

}