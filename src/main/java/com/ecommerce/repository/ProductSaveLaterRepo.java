package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.ProductSaveForLater;


public interface ProductSaveLaterRepo extends JpaRepository<ProductSaveForLater , String> {

	
		List<ProductSaveForLater> findByUserId(String uid);

		Optional<ProductSaveForLater> findById(String id);

		

		boolean deleteById(ProductSaveForLater product);

		boolean existsByUserIdAndVarientId(String userId, String vid);

	

		
}
