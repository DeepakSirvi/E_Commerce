package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;
import com.ecommerce.model.WishListProduct;


public interface WishListRepo  extends JpaRepository< WishListProduct, String>{

	boolean existsByVarientIdAndUserId(String varientId, String userId);

	void deleteByVarientIdAndUserId(String varientId, String userId);

	List<WishListProduct> findByUserId(User user);

	Optional<WishListProduct> findByUser(User user);

	
	


 

	 
	

}
