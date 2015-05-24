package com.emc.data.service;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/integration/*.xml")
public class SimpleLiveDataEndpointIT {

	@Autowired
	private MessageChannel input;
	
	@Autowired
	private AbstractSubscribableChannel output;
	
	private String json;
	
	@Before
	public void before() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileCopyUtils.copy(this.getClass().getResourceAsStream("/tmp.json"),bos);
		json = new String(bos.toByteArray());
	}
		
	@Test
	public void testSave() throws Exception {
		Message<?> message = MessageBuilder.withPayload(json).build();
		output.subscribe(new MessageHandler() {

			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				System.out.println(">>>>>> "  + message);
			}
			
		});
		//send
		input.send(message);
		//wait
		Thread.sleep(5 * 1000);
	}

}
