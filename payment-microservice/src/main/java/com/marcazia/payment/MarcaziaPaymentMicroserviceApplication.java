package com.marcazia.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class MarcaziaPaymentMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcaziaPaymentMicroserviceApplication.class, args);
	}

}
