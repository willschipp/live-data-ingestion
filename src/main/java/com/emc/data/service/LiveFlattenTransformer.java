package com.emc.data.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LiveFlattenTransformer {

	@Autowired
	private ObjectMapper mapper;
	
	@Transformer
	public List<String> transform(Object payload) {
		//init
		List<String> points = null;
		//assume string and convert to map
		try {
			Map<String,Object> result = mapper.readValue(payload.toString(), new TypeReference<Map<String,Object>>(){});
			//pull the message info out
			String sourceId = result.get("sourceId").toString();
			String time = result.get("time").toString();
			//now loop through the points
			points = new ArrayList<String>();
			for (Map<String,Object> point : (List<Map<String,Object>>) result.get("points")) {
				StringBuilder builder = new StringBuilder(sourceId);
				builder.append(",");
				builder.append(time);
				builder.append(",");
				builder.append(point.get("pointId"));
				builder.append(",");
				builder.append(point.get("pointName"));
				builder.append(",");
				builder.append(point.get("customTag"));
				builder.append(",");
				builder.append(point.get("className"));
				builder.append(",");
				builder.append(point.get("systemTag"));
				builder.append(",");
				builder.append(point.get("physicalQuantity"));
				builder.append(",");
				builder.append(point.get("unit"));
				builder.append(",");
				builder.append(point.get("data"));
				builder.append(",");
				builder.append(point.get("status"));
				points.add(builder.toString());
			}//end for
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return points;
	}
	
}
