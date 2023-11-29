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
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.INVALID_REQUEST);
			   throw new BadRequestException(apiResponse);
		}
	}
	
}
