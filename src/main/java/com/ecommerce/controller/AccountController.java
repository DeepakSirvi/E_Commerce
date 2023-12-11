package com.ecommerce.controller;



import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Account;
import com.ecommerce.model.Status;
import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.AccountResponse;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.service.AccountService;
import com.ecommerce.util.AppConstant;

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
	@PutMapping("/{accountId}")
	 public ResponseEntity<ApiResponse> updateAccountStatus(@PathVariable Long accountId, @RequestParam(value = "newStatus") Status newStatus) {
		   System.out.println(newStatus);
	        ApiResponse response = accountService.updateAccountStatus(accountId, newStatus);
	        return ResponseEntity.ok(response);
	    }
	@GetMapping("/{accountById}")
	public ResponseEntity<Map<String, Object>> getByAccountId(@PathVariable(value = "accountById") Long accountId) {
		return  new ResponseEntity<Map<String, Object>>(accountService.getAccountById(accountId),HttpStatus.OK);
	}
	@GetMapping("/allAccount/{userId}")
	public ResponseEntity<Map<String, Object>> getAllAccountsByUserId(@PathVariable Long userId) {
		return new ResponseEntity<Map<String, Object>>(accountService.getAllAccountsByUserId(userId),HttpStatus.OK);
	}
	@GetMapping("/statusAccount/{userId}")
	public ResponseEntity<Map<String, Object>> getAccountByStatusAndUserId(@PathVariable Long userId, @RequestParam(value="status")Status status) {
		
		return new  ResponseEntity<Map<String, Object>>(accountService.getAccountByStatusAndUserId(userId, status),HttpStatus.OK);
}
}
