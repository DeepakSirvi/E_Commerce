package com.ecommerce;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;

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
		Runtime runtime = Runtime.getRuntime();
		System.out.println("Max memory " + runtime.maxMemory()/(1024*1024));
		System.out.println("Total memory " + runtime.totalMemory()/(1024*1024));
		System.out.println("Free memory " + runtime.freeMemory()/(1024*1024));
		System.out.println("Available Proccessor " + runtime.availableProcessors());
		System.out.println("Used memory " + (runtime.totalMemory() - runtime.freeMemory())/(1024*1024));
		}
}
