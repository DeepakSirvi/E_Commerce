package com.ecommerce.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Account;
import com.ecommerce.model.Status;

public interface AccountRepo extends JpaRepository<Account, String>{

	boolean existsByAccountNumber(String accountNumber);

	List<Account> findAllByUserId(String userId);

	List<Account> findByStatusAndUserId(Status status, String userId);
	
	

}
