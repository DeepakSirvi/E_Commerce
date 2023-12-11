package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Account;
import com.ecommerce.model.Status;

public interface AccountRepo extends JpaRepository<Account, Long>{

	boolean existsByAccountNumber(String accountNumber);

	List<Account> findAllByUserId(Long userId);

	List<Account> findByStatusAndUserId(Status status, Long userId);
	
	

}
