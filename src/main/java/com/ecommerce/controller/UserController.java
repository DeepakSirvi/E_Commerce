package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.service.UserService;


@RestController
@RequestMapping("/ecommerce/user")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/deactivate/otp")
	public ResponseEntity<OtpResponse> getOtpDeativate(@RequestBody UserRequest userRequest) 
	{
		return new ResponseEntity<OtpResponse>(userService.otpToDeativateAccount(userRequest),HttpStatus.OK);
	}

}
