package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;
import com.ecommerce.model.WishListProduct;


public interface WishListRepo  extends JpaRepository< WishListProduct, Long>{

	boolean existsByVarientIdAndUserId(Long varientId, Long userId);

	void deleteByVarientIdAndUserId(Long varientId, Long userId);

	WishListProduct findByUserId(Long userId);

	Optional<WishListProduct> findByUser(User user);

	
	


 

	 
	

}
