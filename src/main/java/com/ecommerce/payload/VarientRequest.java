package com.ecommerce.payload;

import java.util.Set;

import com.ecommerce.model.Status;
import com.ecommerce.model.VarientCategoryJoin;
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
public class VarientRequest {
	
	private String varientName;
	private Float price;
	private Integer stock;
	
	private ProductRequest product;
	
	private Status status;
	
	private Set<VarientCategoryJoinRequest> attribute;
	
	private Set<ProductImageRequest> imageRequests;
	
}
