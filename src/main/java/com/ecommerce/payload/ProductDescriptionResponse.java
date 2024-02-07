package com.ecommerce.payload;

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
public class ProductDescriptionResponse {

	private String id;
	private String description;
//    private Set<MapProductDescriptionResponse> mapProductDescriptions;
	private ProductResponse product;

}
