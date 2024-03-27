package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.UserUPI;

public interface UserUPIRepo extends JpaRepository<UserUPI, String> {

}
