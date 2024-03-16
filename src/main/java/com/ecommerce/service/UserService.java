package com.ecommerce.service;

import java.util.List;
import java.util.Map;

import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UpdateUserRequest;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;

public interface UserService {

	public ApiResponse addUser(UserRequest userRequest);

	public OtpResponse otpToDeativateAccount(UserRequest userRequest);
	
	public UserResponse getUserById(String userId);

	public ApiResponse deativateAccount(LoginRequest loginRequest);

	public Map<String, Object> updateUser(UpdateUserRequest userRequest);
	
	public Map<String, List<User>> getAllUsersbyGivenRole(String roleTitle);

}
