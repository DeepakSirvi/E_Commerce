package com.ecommerce.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Brand;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;

public interface UserRepo extends JpaRepository<User, String> {

	public Boolean existsByUserMobile(String userMobile);

	public Boolean existsByUserEmail(String email);

	public Optional<User> findByUserMobile(String userMobile);

	public Optional<User> findByIdAndStatus(String userId, Status active);

	public List<Brand> getBrandsById(String userId);

	@Query("SELECT CASE WHEN (COUNT(u) > 0) THEN true ELSE false END FROM User u WHERE u.id = :userId AND u.status = :active")
	public boolean findByUserIdAndStatus(@Param("userId") String userId, @Param("active") Status active);

	@Query("SELECT u FROM User u INNER JOIN UserRole ur ON u.id = ur.user.id INNER JOIN Role r ON ur.role.id = r.id WHERE r.roleName = :roleName")	
	Page<User> findByRoleName(@Param("roleName") RoleName roleName, Pageable pageable);
	
}
