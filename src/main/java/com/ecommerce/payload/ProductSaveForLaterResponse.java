package com.ecommerce.payload;


import com.ecommerce.model.ProductSaveForLater;
import com.ecommerce.model.User;
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
public class ProductSaveForLaterResponse {
	private String id;
	private VarientResponse varient;
//	private User user;
	
	
	 public ProductSaveForLaterResponse savedLaterToResponse(ProductSaveForLater savedLaterProduct) {
		this.setId(savedLaterProduct.getId());
		
		VarientResponse varientResponse = new VarientResponse();
		
      this.setVarient(varientResponse.varientToVarientResponse(savedLaterProduct.getVarient()));
		
		return this;
	}
	 
}
