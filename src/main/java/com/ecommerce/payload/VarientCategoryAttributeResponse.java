package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryJoin;
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
public class VarientCategoryAttributeResponse {

	private Long id;
	private String attributeName;
	private VarientCategory varientCategory;
	private Set<VarientCategoryJoinResonse> categoryJoins = new HashSet<>();
}
