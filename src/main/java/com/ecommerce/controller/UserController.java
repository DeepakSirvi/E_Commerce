package com.ecommerce.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UpdateUserRequest;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.service.UserService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@RestController
@RequestMapping("/ecommerce/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AppUtils appUtils;

	@PostMapping("/deactivate/otp")
	public ResponseEntity<OtpResponse> getOtpDeativate(@RequestBody UserRequest userRequest) {
		return new ResponseEntity<OtpResponse>(userService.otpToDeativateAccount(userRequest), HttpStatus.OK);
	}

	@PostMapping("/deactivate")
	public ResponseEntity<ApiResponse> deativateAccount(@RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<ApiResponse>(userService.deativateAccount(loginRequest), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<UserResponse> getUser() {
		return new ResponseEntity<UserResponse>(userService.getUserById(appUtils.getUserId()), HttpStatus.OK);
	}

	@GetMapping("/byrole/{roleTitle}")
	public ResponseEntity<?> getAllUsersbyGivenRole(
			@PathVariable String roleTitle ,
			@RequestParam(value="pageIndex"  , required= false , defaultValue= AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value= "pagesize" , required= false , defaultValue= AppConstant.DEFAULT_PAGE_SIZE)   Integer size,
			@RequestParam(value= "sortDir" , required= false , defaultValue= AppConstant.DEFAULT_SORT_DIR) String SortDir)
			{
		return new ResponseEntity<Map>(userService.getAllUsersbyGivenRole(roleTitle ,page, size, SortDir), HttpStatus.OK);
		
	}
	
	@PutMapping("")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UpdateUserRequest userRequest) {

		return new ResponseEntity<Map<String, Object>>(userService.updateUser(userRequest), HttpStatus.OK);
	}
}