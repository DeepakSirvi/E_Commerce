package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Identity;
import com.ecommerce.model.User;

public interface IdentityRepo extends JpaRepository<Identity, Long> {

	
	Optional<Identity> findByIdCardNumber(String idCardNumber);
	
	Optional<Identity> findByUser(User user);
	
	Optional<Identity> findById(String identityId);
}
