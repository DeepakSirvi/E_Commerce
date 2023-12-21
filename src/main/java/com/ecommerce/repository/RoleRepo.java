package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.User;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	 public Boolean existsByRoleName(RoleName roleName);

	public Optional<Role> findByRoleName(RoleName role);


}
