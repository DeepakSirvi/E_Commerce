package com.ecommerce.payload;

import com.ecommerce.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BrandResponse {
	
	
	private String Id;
	
	 private String brandName;
		
     private Status status;
	
	 private String brandImage;
	
	 private String brandDescription;
	 
	 private UserResponse  user;

}
