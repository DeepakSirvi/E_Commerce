package com.ecommerce.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, String> {
	
	public boolean existsByUserAndRole(User user, Role role);

    @Query("SELECT ur.user FROM UserRole ur WHERE ur.role.roleName = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

}
