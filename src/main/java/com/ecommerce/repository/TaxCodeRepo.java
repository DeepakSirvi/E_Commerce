package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.TaxCode;

public interface TaxCodeRepo extends JpaRepository<TaxCode, String> {

}
