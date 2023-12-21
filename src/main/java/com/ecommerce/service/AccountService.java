package com.ecommerce.service;


import java.util.Map;

import com.ecommerce.model.Account;
import com.ecommerce.model.Status;
import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.ApiResponse;

public interface AccountService {

  public ApiResponse addaccount(AccountRequest accountRequest);
  
  public ApiResponse updateAccountStatus(Long accountId, Status newStatus);
  
  public Map<String, Object> getAccountById(Long accountId);
  
  public  Map<String, Object> getAllAccountsByUserId(Long userId);
  
  public  Map<String, Object> getAccountByStatusAndUserId(Long userId, Status status);
  
  public  Map<String, Object> updateAccountDetailsById(Account updatedDetails);
 
  
  
  
	

}