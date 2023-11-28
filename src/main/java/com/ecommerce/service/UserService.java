package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserRequest;

public interface UserService {

	public ApiResponse  addUser(UserRequest userRequest);

	public OtpResponse otpToDeativateAccount(UserRequest userRequest);

}
