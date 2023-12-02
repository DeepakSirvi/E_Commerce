package com.ecommerce.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
   public	Boolean existsByUserMobile(@NotBlank String userMobile);

   public	Boolean existsByUserEmail(@NotBlank String email);
	
   public Optional<User> findByUserMobile(String userMobile);

   public Optional<User> findByIdAndStatus(Long userId, Status active);



}
