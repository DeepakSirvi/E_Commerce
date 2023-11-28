package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Identity;

public interface IdentityRepo extends JpaRepository<Identity, Long> {

}
