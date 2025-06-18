package com.micromart.ProductMicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductMicroserviceApplication {

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
		System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
		System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));
		System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

}
