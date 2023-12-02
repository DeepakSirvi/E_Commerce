package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.payload.VarientCategoryRequest;

public interface VarientCategoryAttributeRepo extends JpaRepository<VarientCategoryAttribute, Long> {

	public Boolean existsByAttributeNameAndVarientCategory(VarientCategoryRequest categoryAttributes,
			VarientCategory varientCategory);

	public boolean existsByAttributeNameAndVarientCategory(String name, VarientCategory varientCategory);

	public Boolean existsByAttributeNameAndIdNot(String attributeName, Long id);

}
