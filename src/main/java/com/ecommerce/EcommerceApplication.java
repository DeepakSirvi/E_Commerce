package com.ecommerce;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;
=======
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class EcommerceApplication {

	
	
	
	@Value("${cloud.name}")
	private String cloudName;
	@Value("${cloud.api-key}")
	private String cloudApiKey;
	@Value("${cloud.secret-key}")
	private String apiSecretKey;

	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(String.format("cloudinary://%s:%s@%s",cloudApiKey,apiSecretKey,cloudName ));
	
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
		}
}
