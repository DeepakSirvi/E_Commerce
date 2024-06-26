package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Login;

public interface LoginRepo extends JpaRepository<Login, Long> {
	
	Boolean existsByPhoneNumber( String userMobile);
	Optional<Login> findByPhoneNumber(String phoneNumber);
	Optional<Login> findByPhoneNumberAndOtp(String phoneNumber,Integer otp);
	

}
