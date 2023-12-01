package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.AccountResponse;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.service.AccountService;

@RestController
@RequestMapping("/ecommerce/account")
@CrossOrigin
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> addAccount(@RequestBody AccountRequest accountRequest ){
		System.out.println("sdfgs");
		return new ResponseEntity<ApiResponse>(this.accountService.addaccount(accountRequest),HttpStatus.CREATED);

	}
}
