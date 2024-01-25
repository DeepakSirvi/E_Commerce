package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.WishListProduct;

import jakarta.transaction.Transactional;


public interface WishListRepo  extends JpaRepository< WishListProduct, String>{

	boolean existsByVarientIdAndUserId(String varientId, String userId);

	@Transactional
	void deleteByVarientIdAndUserId(String varientId, String userId);

	List<WishListProduct> findByUserId(String userId);

	Optional<WishListProduct> findByUser(User user);

	@Transactional
	public void deleteByVarient(Varient varient);


	
	


 

	 
	

}
