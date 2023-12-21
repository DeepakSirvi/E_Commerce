package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	public Boolean existsByCategoryName(String categoryName);

	public boolean existsByCategoryNameAndIdNot(String categoryName, Long id);


	@Query("SELECT c FROM Category c LEFT JOIN c.subCategory s " +
	           "WHERE LOWER(c.categoryName) LIKE LOWER(concat('%', :searchTerm, '%')) OR " +
	           "LOWER(s.subCategory) LIKE LOWER(concat('%', :searchTerm, '%'))")
	public Page<Category> getAllCategorySearch(@Param("searchTerm") String search, Pageable pageable);

}
