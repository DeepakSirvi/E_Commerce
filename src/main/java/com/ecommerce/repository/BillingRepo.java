package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Billing;

public interface BillingRepo extends JpaRepository<Billing, String> {

}
