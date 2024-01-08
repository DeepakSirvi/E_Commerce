package com.ecommerce.service;


import java.util.Map;

import com.ecommerce.model.Account;
import com.ecommerce.model.Status;
import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.ApiResponse;

public interface AccountService {

  public ApiResponse addaccount(AccountRequest accountRequest);
  
  public ApiResponse updateAccountStatus(String accountId, Status newStatus);
  
  public Map<String, Object> getAccountById(String accountId);
  
  public  Map<String, Object> getAllAccountsByUserId(String userId);
  
  public  Map<String, Object> getAccountByStatusAndUserId(String userId, Status status);
  
  public  Map<String, Object> updateAccountDetailsById(Account updatedDetails);
 
  
  
  
	

}