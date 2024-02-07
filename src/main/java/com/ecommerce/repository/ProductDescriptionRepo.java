package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.ProductDescription;

public interface ProductDescriptionRepo extends JpaRepository<ProductDescription, String> {

}
