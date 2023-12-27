package com.ecommerce.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;
import com.ecommerce.model.WishListProduct;


public interface WishListRepo  extends JpaRepository< WishListProduct, Long>{

	boolean existsByVarientIdAndUserId(Long varientId, Long userId);

	void deleteByVarientIdAndUserId(Long varientId, Long userId);

	List<WishListProduct>  findByUser(User u);



	
	


 

	 
	

}
