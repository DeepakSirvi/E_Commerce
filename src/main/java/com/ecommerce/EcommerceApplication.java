package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {
	
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
