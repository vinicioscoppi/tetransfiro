package com.tetransfiro;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TetransfiroApplication {

	@Value("${queue.purchase.name}")
	private String purchaseQueue;

	@Bean
	public Queue queue() {
		return new Queue(purchaseQueue, true);
	}

	public static void main(String[] args) {
		SpringApplication.run(TetransfiroApplication.class, args);
	}

}
