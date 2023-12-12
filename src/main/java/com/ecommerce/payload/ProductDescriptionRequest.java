package com.ecommerce.payload;

import java.util.List;

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
public class ProductDescriptionRequest {
	
	private Long id;
	private String description;
	private ProductRequest product;
//	private List<MapProductDescriptionRequest> mapProductDescriptions;

}
