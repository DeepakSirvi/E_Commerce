package com.ecommerce.payload;

import java.util.Set;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SubCategoryRequest {

    private Long id;
	private String subCategory;
	private CategoryRequest category;
	
	
}
