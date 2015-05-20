package com.emc.data.service;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import com.emc.data.Application;
import com.emc.data.domain.DataPointRepository;
import com.emc.data.domain.ExtensionRepository;
import com.emc.data.domain.MessageObjectRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=Application.class)
public class SimpleLiveDataEndpointIT {

	@Autowired
	private LiveDataEndpoint liveDataEndpoint;
	
	@Autowired
	private MessageObjectRepository messageObjectRepository;
	
	@Autowired
	private DataPointRepository dataPointRepository;
	
	@Autowired
	private ExtensionRepository extensionRepository;
	
	private String json;
	
	@Before
	public void before() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileCopyUtils.copy(this.getClass().getResourceAsStream("/tmp.json"),bos);
		json = new String(bos.toByteArray());
	}
	
	@After
	public void after() {
		//kill
		messageObjectRepository.deleteAllInBatch();
		dataPointRepository.deleteAllInBatch();
		extensionRepository.deleteAllInBatch();		
	}
	
	@Test
	public void testSave() {
		assertTrue(messageObjectRepository.count() <= 0);
		//send the string
		liveDataEndpoint.save(json);
		//test
		assertTrue(messageObjectRepository.count() > 0);
	}

}
