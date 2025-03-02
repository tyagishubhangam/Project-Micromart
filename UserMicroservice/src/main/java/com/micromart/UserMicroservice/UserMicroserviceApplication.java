package com.micromart.UserMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		Dotenv dotenv = (Dotenv) Dotenv.configure()
				.directory("../UserMicroservice")  // Specify the filename
				.load();
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		System.setProperty("GOOGLE_OAUTH_SECRET", dotenv.get("GOOGLE_OAUTH_SECRET"));
		System.setProperty("GOOGLE_OAUTH_CLIENT_ID", dotenv.get("GOOGLE_OAUTH_CLIENT_ID"));
		System.setProperty("GOOGLE_CALLBACK_URI", dotenv.get("GOOGLE_CALLBACK_URI"));
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

}
