package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.VarientCategory;

public interface VarientCategoryRepo extends JpaRepository<VarientCategory, Long> {

	public Boolean existsByName(String name);

	public Object existsByNameAndIdNot(String name,Long id);

}
