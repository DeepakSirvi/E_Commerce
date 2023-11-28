package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Account;

public interface AccountRepo extends JpaRepository<Account, Long>{

}
