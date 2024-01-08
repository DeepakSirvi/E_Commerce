package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Role;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, String> {
	
   public	Boolean existsByUserMobile( String userMobile);

   public	Boolean existsByUserEmail( String email);
	
   public Optional<User> findByUserMobile(String userMobile);

   public Optional<User> findByIdAndStatus(String userId, Status active);

  



}
