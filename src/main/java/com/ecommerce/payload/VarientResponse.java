package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class VarientResponse {
	
	
	private Long id;
	private String varientName;
	private Float price;
	private Integer stock;
	
	private ProductResponse product;
	
	private Set<VarientCategoryAttributeResponse> attribute;
	
	private Status status;
	
	private Set<VarientCategoryJoinResonse> categoryJoins=new HashSet<>();
	
	private Set<ProductImageRespone> productImage=new HashSet<>();

}
