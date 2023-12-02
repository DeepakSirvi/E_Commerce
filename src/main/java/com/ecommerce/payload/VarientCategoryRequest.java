package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.User;
import com.ecommerce.model.VarientCategoryAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
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
public class VarientCategoryRequest {
	
	private Long id;
	private String name;
	private Set<VarientCategoryAttributeRequest> categoryAttributes=new HashSet<>();
	private UserRequest user;

}
