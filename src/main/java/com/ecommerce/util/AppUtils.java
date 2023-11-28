package com.ecommerce.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class AppUtils {
   
	public Integer generateOtp() {
		Random r = new Random();
		return r.nextInt(1000,9999);
	}
	
}
