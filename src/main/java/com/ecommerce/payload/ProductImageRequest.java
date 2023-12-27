package com.ecommerce.payload;

import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Varient;
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
public class ProductImageRequest {

	private String id;
	
	private String imageUrl;
	
//	private VarientRequest varientImage;
}
