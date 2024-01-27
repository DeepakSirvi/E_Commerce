package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Identity;
import com.ecommerce.model.User;

public interface IdentityRepo extends JpaRepository<Identity, Long> {

	
	Optional<Identity> findByIdCardNumber(String idCardNumber);
	
	Optional<Identity> findByUser(User user);
	
	Optional<Identity> findById(String identityId);

	List<Identity> findAllByUserId(String userId);
	
	@Query("SELECT   I  FROM   Identity I WHERE I.status = ACTIVE")
	Page<Identity> findAllActiveIdentity(Pageable pageable);
}
