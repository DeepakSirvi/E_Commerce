package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Status;
import com.ecommerce.model.VarientCategoryJoin;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	private ProductRequest product;
	
	private Status status;
	
	private Set<VarientCategoryJoinRequest> categoryJoins=new HashSet<>();
	
	private Set<ProductImageRequest> productImage=new HashSet<>();
	
	
}
