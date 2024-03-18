package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.UserGroup;

public interface UserGroupRepo extends JpaRepository<UserGroup, String> {

}
