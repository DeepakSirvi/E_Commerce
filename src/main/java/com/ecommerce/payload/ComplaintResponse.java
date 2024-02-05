package com.ecommerce.payload;

import java.util.List;
import com.ecommerce.model.ComplaintImage;

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
public class ComplaintResponse {
	
	private String id;
	
	private String title;
	 
	private String  description;
	
	private String productId ;
	
	private List<ComplaintImage> image;

}
