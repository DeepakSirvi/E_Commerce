package com.ecommerce.payload;

import java.util.Set;

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
public class ProductImageRespone {
	
    private Long id;
	
	private String imageUrl;
	
	private Varient varientImage;

}
