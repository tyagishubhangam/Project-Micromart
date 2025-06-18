package com.micromart.PaymentMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PaymentMicroserviceApplication {

	public static void main(String[] args) {
		String rootDir = System.getProperty("user.dir"); // Will be "UserMicroservice"
		String envPath = rootDir + "/"; // Go to Project-Micromart
		System.out.println(envPath);
		Dotenv dotenv = (Dotenv) Dotenv.configure()
				.directory(envPath)
				.filename(".env")// Specify the filename
				.load();
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		System.setProperty("PAYPAL_CLIENT_ID",dotenv.get("PAYPAL_CLIENT_ID"));
		System.setProperty("PAYPAL_CLIENT_SECRET",dotenv.get("PAYPAL_CLIENT_SECRET"));
		System.setProperty("frontend.url",dotenv.get("frontend.url"));
		SpringApplication.run(PaymentMicroserviceApplication.class, args);
	}

}
