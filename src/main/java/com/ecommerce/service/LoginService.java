package com.ecommerce.service;

import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserResponse;

public interface LoginService {
	public OtpResponse generateOtp(String phoneNumber);

	public UserResponse loginUser(LoginRequest loginRequest);

}
