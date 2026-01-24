package com.marcazia.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MarcaziaNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcaziaNotificationServiceApplication.class, args);
	}

}
