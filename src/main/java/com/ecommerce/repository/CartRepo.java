package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;

import jakarta.transaction.Transactional;

public interface CartRepo extends JpaRepository<Cart, String> {

	public Optional<Cart> findByUserAndVarient(User user, Varient varient);

	public List<Cart> findByUser(User user);

	@Query(value = "SELECT 'cart' as carts, COUNT(*) as count FROM cart  WHERE user_id = :userId " + "UNION ALL "
			+ "SELECT 'wishlist' as wishlist, COUNT(*) as count FROM wish_list_product  WHERE user_id = :userId ", nativeQuery = true)
	public List<Object> fetchCountofWishAndCartItem(@Param("userId") String userId);

	@Transactional
	public Integer deleteByVarient(Varient varient);

}
