package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	public Boolean existsByCategoryName(String categoryName);

	public boolean existsByCategoryNameAndIdNot(String categoryName, Long id);

}
