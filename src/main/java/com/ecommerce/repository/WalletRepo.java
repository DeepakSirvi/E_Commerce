package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Wallet;

public interface WalletRepo extends JpaRepository<Wallet, String> {

}
