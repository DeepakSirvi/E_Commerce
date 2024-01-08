package com.ecommerce.payload;

import com.ecommerce.model.ProductImage;
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
public class ProductImageRespone {
	
    private String id;
	
	private String imageUrl;
	
	private Varient varientImage;
	
   
	
	public ProductImageRespone imageToImageResponse(ProductImage productImage) {
		this.setId(productImage.getId());
		this.setImageUrl(productImage.getImageUrl());
		return this;
	}

}
