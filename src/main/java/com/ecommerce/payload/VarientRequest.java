package com.ecommerce.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	
	private String id;
	private String varientName;
	private Float price;
	private Integer stock;
	
	private String productId;
	
	private Status status;
	
	private List<VarientCategoryJoinRequest> categoryJoins=new ArrayList<>();
	
	private List<ProductImageRequest> productImage=new ArrayList<>();
	
	
}
