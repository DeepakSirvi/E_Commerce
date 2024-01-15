package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Brand;
import com.ecommerce.model.Status;
import com.ecommerce.payload.BrandRequest;

public interface BrandRepo  extends JpaRepository<Brand, String> {

	

	List<Brand> findAllByUserId(String userId);

	
	// @ Query("SELECT b from Brand b"+ " WHERE b . Verified = : VerifiedStatus " +"AND b. brand.Id= :brandId" )
  // Page<Brand> findAllByBrandIdAndVerified(String brandId, Boolean true1, Status verified, Pageable pageable);


	//Optional<Brand> findByIdAndStatus(String brandId);

	


	
		
}
