package com.ecommerce.payload;

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
public class VarientCategoryJoinResonse {

	private Long id;
	private VarientCategoryAttributeResponse varAttribute;	
	private VarientResponse varient;
	
	public VarientCategoryJoinResonse varientJoinToResponse(VarientCategoryJoin catJoin) {
		this.setId(catJoin.getId());
		VarientCategoryAttributeResponse attributeResponse=new VarientCategoryAttributeResponse();
		this.setVarAttribute(attributeResponse.vatAttributeToResponse(catJoin.getVarAttribute()));
		return this;
	}
}
