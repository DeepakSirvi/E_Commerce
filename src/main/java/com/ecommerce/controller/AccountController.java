package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.AccountRequest;
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
		return new ResponseEntity<ApiResponse>(this.accountService.addaccount(accountRequest),HttpStatus.CREATED);
	}
	
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<ApiResponse>updateaccount(@PathVariable Long id  , @RequestBody AccountRequest accountRequest){
//		ApiResponse response = accountService.updateaccount(id, accountRequest);
        return new ResponseEntity(this.accountService.updateaccount(accountRequest),HttpStatus.OK);
	}	
	
}
