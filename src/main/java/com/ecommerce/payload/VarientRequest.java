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
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class VarientRequest {
	
	
	private Long id;
	private String varientName;
	private Float price;
	private Integer stock;
	
	private Long productId;
	
	private Status status;
	
	private Set<VarientCategoryJoinRequest> categoryJoins=new HashSet<>();
	
//	private Set<ProductImageRequest> productImage=new HashSet<>();
	
	
}
