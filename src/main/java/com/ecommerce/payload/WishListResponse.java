package com.ecommerce.payload;

import com.ecommerce.model.Varient;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class WishListResponse {
	
        private String id;
	
	  private Varient varient;


}
