package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Boolean existsByRoleName(RoleName roleName);

}
