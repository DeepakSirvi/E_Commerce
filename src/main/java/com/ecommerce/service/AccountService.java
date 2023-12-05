package com.ecommerce.service;

import com.ecommerce.payload.AccountRequest;
import com.ecommerce.payload.AccountResponse;
import com.ecommerce.payload.ApiResponse;

public interface AccountService {

 public ApiResponse addaccount(AccountRequest accountRequest);
 
 public AccountResponse updateaccount (AccountRequest accountRequest);
  

}
