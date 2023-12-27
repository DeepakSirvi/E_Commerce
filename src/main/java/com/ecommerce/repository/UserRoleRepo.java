package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, String> {
	
	public boolean existsByUserAndRole(User user, Role role);

}
