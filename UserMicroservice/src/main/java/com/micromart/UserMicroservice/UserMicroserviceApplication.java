package com.micromart.UserMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class UserMicroserviceApplication {

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
		System.setProperty("GOOGLE_OAUTH_SECRET", dotenv.get("GOOGLE_OAUTH_SECRET"));
		System.setProperty("GOOGLE_OAUTH_CLIENT_ID", dotenv.get("GOOGLE_OAUTH_CLIENT_ID"));
		System.setProperty("GOOGLE_CALLBACK_URI", dotenv.get("GOOGLE_CALLBACK_URI"));
		System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
		System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));
		System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
		System.setProperty(("frontend.url"), dotenv.get("frontend.url"));
		System.setProperty(("JWT.SECRET_KEY"), dotenv.get("JWT.SECRET_KEY"));
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

}
