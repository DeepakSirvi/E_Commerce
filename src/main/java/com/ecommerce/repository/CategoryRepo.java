package com.ecommerce.repository;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	public Boolean existsByCategoryName(String categoryName);

}
