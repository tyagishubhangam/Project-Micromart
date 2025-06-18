package com.micromart.OrderMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderMicroserviceApplication {

	public static void main(String[] args) {
		String rootDir = System.getProperty("user.dir"); // Will be "UserMicroservice"
		String envPath = rootDir + "/"; // Go to Project-Micromart
		System.out.println("*****************"+envPath);
		Dotenv dotenv = (Dotenv) Dotenv.configure()
				.directory(envPath)
				.filename(".env")// Specify the filename
				.load();
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		SpringApplication.run(OrderMicroserviceApplication.class, args);
	}

}
