package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;

public interface UserService {

	public ApiResponse addUser(UserRequest userRequest);

	public OtpResponse otpToDeativateAccount(UserRequest userRequest);
	
	public UserResponse getUserById(String userId);

	public ApiResponse deativateAccount(LoginRequest loginRequest);

	public Map<String, Object> updateUser(UserRequest userRequest);

}
