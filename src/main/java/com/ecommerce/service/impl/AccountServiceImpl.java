package com.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.ADDRESS_ADDED);
	    return  apiResponse;
	}

}
