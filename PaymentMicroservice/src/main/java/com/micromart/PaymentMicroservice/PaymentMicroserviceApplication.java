package com.micromart.PaymentMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PaymentMicroserviceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = (Dotenv) Dotenv.configure()
				.directory("../PaymentMicroservice")  // Specify the filename
				.load();
		System.setProperty("PAYPAL_CLIENT_ID",dotenv.get("PAYPAL_CLIENT_ID"));
		System.setProperty("PAYPAL_CLIENT_SECRET",dotenv.get("PAYPAL_CLIENT_SECRET"));

		SpringApplication.run(PaymentMicroserviceApplication.class, args);
	}

}
