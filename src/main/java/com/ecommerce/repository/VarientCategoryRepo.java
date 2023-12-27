package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.VarientCategory;

public interface VarientCategoryRepo extends JpaRepository<VarientCategory, String> {

	public Boolean existsByName(String name);

	public Object existsByNameAndIdNot(String name,String id);
	
	public Page<VarientCategory> findByName(String search, Pageable pageable);

	@Query("SELECT c FROM VarientCategory c LEFT JOIN c.categoryAttributes s " +
	           "WHERE LOWER(c.name) LIKE LOWER(concat('%', :searchTerm, '%')) OR " +
	           "LOWER(s.attributeName) LIKE LOWER(concat('%', :searchTerm, '%'))")
	public Page<VarientCategory> getAllVarientList(@Param("searchTerm") String search, Pageable pageable);
}