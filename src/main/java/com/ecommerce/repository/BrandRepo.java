package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Brand;

public interface BrandRepo extends JpaRepository<Brand, String> {

	List<Brand> findAllByUserId(String userId);

	@Query("SELECT   b  FROM   Brand b WHERE b.status = ACTIVE")
	Page<Brand> findAllVerfiedBrand(Pageable pageable);
     @Query (" SELECT b FROM  Brand b where b.status = UNVERIFIED")
	Page<Brand> findByStatusNot(Pageable pageable, String string);

}
