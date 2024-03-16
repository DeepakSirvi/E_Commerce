package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.BillingMethod;

public interface BillingMethodRepo extends JpaRepository<BillingMethod, String> {

}
