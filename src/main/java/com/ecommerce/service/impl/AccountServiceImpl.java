package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Account;
import com.ecommerce.model.Status;
import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.AccountResponse;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.repository.AccountRepo;
import com.ecommerce.service.AccountService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
	private  AccountRepo accountRepo;
    @Autowired
    public ModelMapper modelMapper;
    
    @Autowired
    private AppUtils appUtils;
    
    
    public AccountResponse accountToAccountResponse(Account account) {
    	return this.modelMapper.map(account, AccountResponse.class);
    	
    } 
    
    
    public Account accountToAccountRequest(AccountRequest accountRequest) {
    	
    	return this. modelMapper.map(accountRequest, Account.class);
    
    	
    }
    
	
	@Override
	public ApiResponse addaccount(AccountRequest accountRequest) {
		if (accountRepo.existsByAccountNumber(accountRequest.getAccountNumber())) {
            throw new  BadRequestException(AppConstant.ACCOUNT_NUMBER_TAKEN);
        }
		accountRequest.setUser(new UserRequest(appUtils.getUserId()));
		accountRequest.setStatus(Status.ACTIVE);
		Account accountResponse=this.accountRepo.save(this.accountToAccountRequest(accountRequest));
		
		ApiResponse apiResponse = new ApiResponse(AppConstant.ADDRESS_ADDED);
	    return  apiResponse;
	}


	@Override
	public ApiResponse updateAccountStatus(String accountId, Status newStatus) {
		
            Optional<Account> optionalAccount = accountRepo.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                validateStatusTransition(account.getStatus(), newStatus);
                System.out.println(newStatus);
                account.setStatus(newStatus);
                accountRepo.save(account);
                String statusMessage = newStatus == Status.ACTIVE ?
                        AppConstant.ACCOUNT_ACTIVATED : AppConstant.ACCOUNT_DEACTIVATED;
                return new ApiResponse(statusMessage);
            } else {
            	throw new BadRequestException(AppConstant.ACCOUNT_NOT_FOUND);
            }
        
    }
	 private void validateStatusTransition(Status currentStatus, Status newStatus) {
	        if ((currentStatus == Status.ACTIVE && newStatus == Status.ACTIVE) ||
	            (currentStatus == Status.INACTIVE && newStatus == Status.INACTIVE)) {
	            throw new BadRequestException(AppConstant.INVALID_TRANSITION);
	        }
	 }


	@Override
	public Map<String,Object> getAccountById(String accountId) {
		
		   Optional<Account> optionalAccount = accountRepo.findById(accountId);
           if (optionalAccount.isPresent()) {
        	   Map<String,Object> response= new HashMap<>();
               Account account = optionalAccount.get();
               AccountResponse accountResponse = accountToAccountResponse(account);
		       response.put("accoutDetail", accountResponse);
		       return response ;
           } else 
               {
            	throw new BadRequestException(AppConstant.ACCOUNT_NOT_FOUND);
            	}
	    }


	@Override
	public Map<String, Object> getAllAccountsByUserId(String userId) {
	
            Map<String, Object> response = new HashMap<>();
            List<Account> accounts = accountRepo.findAllByUserId(userId);
                List<AccountResponse> accountResponses = accounts.stream()
                        .map(this::accountToAccountResponse)
                        .collect(Collectors.toList());
                response.put("accounts", accountResponses);
                return response;
    }


	@Override
	public Map<String, Object> getAccountByStatusAndUserId(String userId, Status status) {
	   
	        Map<String, Object> response = new HashMap<>();
	        List<Account> activeAccounts = accountRepo.findByStatusAndUserId(status, userId);

	        
	            List<AccountResponse> accountResponses = activeAccounts.stream()
	                    .map(this::accountToAccountResponse)
	                    .collect(Collectors.toList());
	            response.put("accounts", accountResponses);
	        return response;
	}

	
	@Override
	public Map<String, Object> updateAccountDetailsById(Account updatedDetails) {
		        Map<String,Object> response = new HashMap<>();
		        Account account = accountRepo.findById(updatedDetails.getId()).orElseThrow(()->new BadRequestException(AppConstant.ACCOUNT_NOT_FOUND));
		        if (!account.getAccountNumber().equals(updatedDetails.getAccountNumber()) &&
		                accountRepo.existsByAccountNumber(updatedDetails.getAccountNumber())) {
		            throw new BadRequestException(AppConstant.DUPLICATE_ACCOUNT_NUMBER);
		        }    
		        account.setAccountHolderName(updatedDetails.getAccountHolderName());
		        account.setAccountNumber(updatedDetails.getAccountNumber());
		        account.setBankName(updatedDetails.getBankName());
		        account.setBankIFSCcode(updatedDetails.getBankIFSCcode());
		        account.setVenderGSTnumber(updatedDetails.getVenderGSTnumber());
		        account.setPanNumber(updatedDetails.getPanNumber());  
		        account.setUpdatedAt(updatedDetails.getCreatedAt());
		        account.setUpdatedBy(updatedDetails.getCreatedBy());
		        account.setUser(updatedDetails.getUser());
		        account.setVenderGSTnumber(updatedDetails.getVenderGSTnumber());
		       
		        this.accountRepo.save(account);
		        response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.UPDATE_ACCOUNT);
		        response.put("account", accountToAccountResponse(account));
		        return response;
		    }
}
        
		        		
		      

	   
		      
	
	
	
	
	
