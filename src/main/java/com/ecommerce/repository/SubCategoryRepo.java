package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;

public interface SubCategoryRepo extends JpaRepository<SubCategory, String> {
	
	public Boolean existsBySubCategoryAndCategory(String subCategory,Category category);

	public SubCategory findByIdAndCategory(String id, Category category);

}
