package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CategoryRequest {
	
	  private Long id;
	  @NotBlank
	  @Size(min=1 , max=255)
	 private String categoryName;
	 private Set<SubCategoryRequest> subCategory = new HashSet<>();
}
