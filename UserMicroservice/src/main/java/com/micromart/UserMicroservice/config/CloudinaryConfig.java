package com.micromart.UserMicroservice.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${CLOUDINARY_API_KEY}")
    String api_key;
    @Value("${CLOUDINARY_API_SECRET}")
    String api_secret;
    @Value("${CLOUDINARY_CLOUD_NAME}")
    String cloud_name;
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name",cloud_name,
                "api_key",api_key,
                "api_secret",api_secret,
                "secure",true
        ));
    }
}
