package com.micromart.Gateway_Microservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayMicroserviceApplication {

	public static void main(String[] args) {
		String rootDir = System.getProperty("user.dir"); // Will be "UserMicroservice"
		String envPath = rootDir + "/"; // Go to Project-Micromart
		System.out.println(envPath);
		Dotenv dotenv = (Dotenv) Dotenv.configure()
				.directory(envPath)
				.filename(".env")// Specify the filename
				.load();
		System.setProperty(("frontend.url"), dotenv.get("frontend.url"));
		System.setProperty(("JWT.SECRET_KEY"), dotenv.get("JWT.SECRET_KEY"));
		SpringApplication.run(GatewayMicroserviceApplication.class, args);
	}

}
