package com.tetransfiro.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher{
	
	@Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    
    private Logger logger = LoggerFactory.getLogger(MessagePublisher.class);

    public void send(String message) {
        this.template.convertAndSend(queue.getName(), message);
        logger.debug("Sent <{}>", message);
    }
}
