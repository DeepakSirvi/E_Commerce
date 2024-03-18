package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.UserCard;

public interface UserCardRepo extends JpaRepository<UserCard, String> {

}
