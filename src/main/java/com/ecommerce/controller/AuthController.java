package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.service.LoginService;
import com.ecommerce.service.UserService;

@RestController
@RequestMapping("/ecommerce/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> saveUser(@Validated @RequestBody UserRequest user) {

		return new ResponseEntity<ApiResponse>(userService.addUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/logout")
	public ResponseEntity<ApiResponse> logoutUser() {
		System.out.println("Controller Logout");
		return new ResponseEntity<ApiResponse>(loginService.logoutUser(), HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<OtpResponse> getOtp(@RequestBody UserRequest userRequest) {
		return new ResponseEntity<OtpResponse>(loginService.generateOtp(userRequest.getUserMobile()),
				HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUserWithMobile(@Validated @RequestBody LoginRequest loginRequest) {
		System.err.println("lOGIN");
		return new ResponseEntity<UserResponse>(loginService.loginUser(loginRequest), HttpStatus.OK);
	}

}
