package com.ecommerce.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.model.Status;
import com.ecommerce.model.Varient;
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

	private String id;
	private String varientName;
	private Float price;
	private Integer stock;
	private ProductResponse product;
	private Status status;

	private List<VarientCategoryJoinResonse> categoryJoins = new ArrayList<>();
	private List<ProductImageRespone> productImage = new ArrayList<>();

	public VarientResponse varientToVarientResponse(Varient varient) {
		this.setId(varient.getId());
		this.setPrice(varient.getPrice());
		this.setStock(varient.getStock());
		this.setVarientName(varient.getVarientName());
		
		
		this.setProductImage(varient.getProductImage().stream().map(productImage -> {
			ProductImageRespone imageRespone = new ProductImageRespone();
			return imageRespone.imageToImageResponse(productImage);
		}).collect(Collectors.toList()));
		
		
		this.setCategoryJoins(varient.getCategoryJoins().stream().map(catJoin -> {
					VarientCategoryJoinResonse joinResonse=new VarientCategoryJoinResonse();
					return joinResonse.varientJoinToResponse(catJoin);
		}).collect(Collectors.toList()));
		return this;
	}

	public VarientResponse varientToVarientResponseForCard(Varient varient) {
		this.setId(varient.getId());
		this.setPrice(varient.getPrice());
		this.setStock(varient.getStock());
		this.setVarientName(varient.getVarientName());
		this.setProductImage(varient.getProductImage().stream().map(productImage -> {
			ProductImageRespone imageRespone = new ProductImageRespone();
			return imageRespone.imageToImageResponse(productImage);
		}).collect(Collectors.toList()));
		
		this.setProduct(new ProductResponse().productToProductResponse(varient.getProduct()));
		return this;
	}

}
