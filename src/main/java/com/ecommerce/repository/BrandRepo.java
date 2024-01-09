package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Brand;
import com.ecommerce.payload.BrandRequest;

public interface BrandRepo  extends JpaRepository<Brand, String> {

	


	
		
}
