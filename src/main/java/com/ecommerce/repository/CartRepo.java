package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;

public interface CartRepo extends JpaRepository<Cart, String> {

	public Optional<Cart> findByUserAndVarient(User user, Varient varient);

	public List<Cart> findByUser(User user);

}
