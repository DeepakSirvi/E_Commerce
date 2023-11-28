package com.ecommerce.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	Boolean existsByUserMobile(@NotBlank String userMobile);

	Boolean existsByUserEmail(@NotBlank String email);
	
	Optional<User> findByUserMobile(String userMobile);

}
