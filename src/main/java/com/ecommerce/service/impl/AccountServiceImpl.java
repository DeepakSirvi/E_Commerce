package com.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Account;
import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.AccountResponse;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.repository.AccountRepo;
import com.ecommerce.service.AccountService;
import com.ecommerce.util.AppConstant;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
	private  AccountRepo accountRepo;
    @Autowired
    public ModelMapper modelMapper;
    
    
    public AccountResponse accountToAccountResponse(Account account) {
    	return this.modelMapper.map(account, AccountResponse.class);
    } 
    
    public Account accountToAccountRequest(AccountRequest accountRequest) {
    	return this.modelMapper.map(accountRequest, Account.class);
    }
	
	@Override
	public ApiResponse addaccount(AccountRequest accountRequest) {
		Account accountResponse=this.accountRepo.save(this.accountToAccountRequest(accountRequest));
		ApiResponse apiResponse = new ApiResponse("",HttpStatus.OK);
	    return  apiResponse;
	}

	@Override
	public AccountResponse updateaccount(AccountRequest accountRequest) {
		Account account = this.accountRepo.findById(accountRequest.getId()).orElseThrow(()-> new ResourceNotFoundException(AppConstant.ACCOUNT_NOT_FOUND+accountRequest.getId()));
		
		account.setStatus(accountRequest.getStatus());
		
		
		account = accountRepo.save(account);
		  
		return accountToAccountResponse(account);
	}

	
}
