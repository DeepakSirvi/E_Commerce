package com.ecommerce.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

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
    	return this.modelMapper.map(accountRequest, Account.class);
    }
    
	
	@Override
	public ApiResponse addaccount(AccountRequest accountRequest) {
		if (accountRepo.existsByAccountNumber(accountRequest.getAccountNumber())) {
            throw new  BadRequestException(AppConstant.ACCOUNT_NUMBER_TAKEN);
        }
		accountRequest.setUser(new UserRequest(appUtils.getUserId()));
		accountRequest.setStatus(Status.ACTIVE);
		Account accountResponse=this.accountRepo.save(this.accountToAccountRequest(accountRequest));
		
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.ADDRESS_ADDED);
	    return  apiResponse;
	}


	@Override
	public ApiResponse updateAccountStatus(Long accountId, Status newStatus) {
		try {
            Optional<Account> optionalAccount = accountRepo.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                validateStatusTransition(account.getStatus(), newStatus);
                System.out.println(newStatus);
                account.setStatus(newStatus);
                accountRepo.save(account);
                String statusMessage = newStatus == Status.ACTIVE ?
                        AppConstant.ACCOUNT_ACTIVATED : AppConstant.ACCOUNT_DEACTIVATED;
                return new ApiResponse(Boolean.TRUE, statusMessage);
            } else {
            	throw new AccountNotFoundException(AppConstant.ACCOUNT_NOT_FOUND);
            }
        } catch (Exception e) {
        	throw new RuntimeException(AppConstant.ERROR_UPDATING_ACCOUNT_STATUS + e.getMessage());
        }
    }
	 private void validateStatusTransition(Status currentStatus, Status newStatus) {
	        if ((currentStatus == Status.ACTIVE && newStatus == Status.ACTIVE) ||
	            (currentStatus == Status.INACTIVE && newStatus == Status.INACTIVE)) {
	            throw new BadRequestException("Invalid status transition");
	        }
	 }


	@Override
	public Map<String,Object> getAccountById(Long accountId) {
		 try {
			 Map<String,Object> response= new HashMap<>();
	            Optional<Account> optionalAccount = accountRepo.findById(accountId);
	            if (optionalAccount.isPresent()) {
	                Account account = optionalAccount.get();
	                AccountResponse accountResponse = accountToAccountResponse(account);
	                response.put("message",AppConstant.ACCOUNT_FOUND );
	                response.put("accoutDetail", accountResponse);
	                return response ;
	            } else {
	            	throw new AccountNotFoundException(AppConstant.ACCOUNT_NOT_FOUND);
	            }
	        } catch (Exception e) {
	            throw  new BadRequestException(e.getMessage());
	        }
	    }


	@Override
	public Map<String, Object> getAllAccountsByUserId(Long userId) {
		try {
            Map<String, Object> response = new HashMap<>();
            List<Account> accounts = accountRepo.findAllByUserId(userId);
            if (!accounts.isEmpty()) {
                List<AccountResponse> accountResponses = accounts.stream()
                        .map(this::accountToAccountResponse)
                        .collect(Collectors.toList());
                response.put("message", AppConstant.ACCOUNTS_FOUND);
                response.put("accounts", accountResponses);

                return response;
            } else {
            	throw new AccountNotFoundException(AppConstant.ACCOUNT_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
	@Override
	public Map<String, Object> getAccountByStatusAndUserId(Long userId, Status status) {
	    try {
	        Map<String, Object> response = new HashMap<>();
	        
	        List<Account> activeAccounts = accountRepo.findByStatusAndUserId(status, userId);

	        if (!activeAccounts.isEmpty()) {
	            List<AccountResponse> accountResponses = activeAccounts.stream()
	                    .map(this::accountToAccountResponse)
	                    .collect(Collectors.toList());

	            response.put("message", AppConstant.ACCOUNTS_FOUND);
	            response.put("accounts", accountResponses);
	        } else {
	            response.put("message", AppConstant.NO_ACCOUNTS_FOUND);
	            response.put("accounts", Collections.emptyList());
	        }
	       
	        return response;
	    } catch (Exception e) {
	        throw new BadRequestException(e.getMessage());
	    }
	}

	
	@Override
	public Map<String, Object> updateAccountDetailsById(Long accountId, Account updatedDetails) {
		        Map<String,Object> response = new HashMap<>();
		        Account account = accountRepo.findById(accountId).orElseThrow(()->new BadRequestException(AppConstant.ACCOUNT_NOT_FOUND));
		        try {
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
//		        account.setStatus(updatedDetails.getStatus());
		        account.setUpdatedAt(updatedDetails.getCreatedAt());
		        account.setUpdatedBy(updatedDetails.getCreatedBy());
		        account.setUser(updatedDetails.getUser());
		        account.setVenderGSTnumber(updatedDetails.getVenderGSTnumber());
		        this.accountRepo.save(account);
		        response.put("dee", AppConstant.UPDATE_ACCOUNT);
		        response.put("account", accountToAccountResponse(account));
		        return response;
		    } catch (BadRequestException e) {
		        response.put("dee", AppConstant.DUPLICATE_ACCOUNT_NUMBER);
		        response.put("message", e.getMessage());
		        return response;
		    }
		}
}
        
		        		
		      

	   
		      
	
	
	
	
	
