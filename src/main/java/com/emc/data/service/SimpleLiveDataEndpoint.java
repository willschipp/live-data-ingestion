package com.emc.data.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emc.data.domain.DataPoint;
import com.emc.data.domain.DataPointRepository;
import com.emc.data.domain.Extension;
import com.emc.data.domain.ExtensionRepository;
import com.emc.data.domain.MessageObject;
import com.emc.data.domain.MessageObjectRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SimpleLiveDataEndpoint implements LiveDataEndpoint {

	private static final Log logger = LogFactory.getLog(SimpleLiveDataEndpoint.class);
	
	@Autowired
	private MessageObjectRepository messageObjectRepository;
	
	@Autowired
	private DataPointRepository dataPointRepository;
	
	@Autowired
	private ExtensionRepository extensionRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public void save(String payload) {
		try {
			//convert to a map
			Map<String,Object> map = mapper.readValue(payload, new TypeReference<Map<String,Object>>() {});
			//now map to the objects
			MessageObject messageObject = new MessageObject();
			messageObject.setSourceId(map.get("sourceId").toString());
			messageObject.setDataTime(new Date(Long.parseLong(map.get("time").toString())));
			//now process the points
			List<DataPoint> dataPointSet = new ArrayList<DataPoint>();
			List<Map<String,Object>> dataPoints = (List<Map<String,Object>>) map.get("points");
			for (Map<String,Object> dataPointMap : dataPoints) {
				DataPoint dataPoint = new DataPoint();
				dataPoint.setClassName(dataPointMap.get("className").toString());
				dataPoint.setCustomTag(dataPointMap.get("customTag").toString());
				dataPoint.setData(dataPointMap.get("data").toString());
				dataPoint.setPhysicalQuantity(dataPointMap.get("physicalQuantity").toString());
				dataPoint.setPointId(Integer.parseInt(dataPointMap.get("pointId").toString()));
				dataPoint.setPointName(dataPointMap.get("pointName").toString());
				dataPoint.setStatus(dataPointMap.get("status").toString());
				dataPoint.setSystemTag(dataPointMap.get("systemTag").toString());
				dataPoint.setUnit(dataPointMap.get("unit").toString());
				//now do the extensions
				List<Extension> extensions = new ArrayList<Extension>();
				List<Map<String,Object>> extensionsList = (List<Map<String,Object>>) dataPointMap.get("extensions");
				for (Map<String,Object> extensionsMap : extensionsList) {
					Extension extension = new Extension();
					extension.setName(extensionsMap.get("extensionName").toString());
					extension.setType(extensionsMap.get("extensionType").toString());
					extensions.add(extension);
				}//end for
				//save the extensions
				extensionRepository.save(extensions);
				//add
				dataPoint.setExtensions(extensions);
				dataPointSet.add(dataPoint);
			}//end for
			//save
			dataPointRepository.save(dataPointSet);
			//add
			messageObject.setDataPoints(dataPointSet);
			//save
			messageObjectRepository.save(messageObject);
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}
