package com.ecommerce.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.payload.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AppUtils {
	
	
	@Autowired
	private JwtUtils jwtUtils;
	private String token;
   
	public Integer generateOtp() {
		Random r = new Random();
		return r.nextInt(1000,9999);
	}
	
	public Long getUserId() {
		HttpServletRequest  httpRequest = RequestContextHolder.getRequest();
		if(httpRequest!=null)
		{
		   String token = httpRequest.getHeader("Authorization");
		   return jwtUtils.getUserIdFromToken(token);
		}
		else
		{
			   throw new BadRequestException(AppConstant.INVALID_REQUEST);
		}
	}
	
public static final void validatePageAndSize(Integer page, Integer size) {
		
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size <= 0) {
			throw new BadRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstant.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
		}
	}

	
}
