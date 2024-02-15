package com.ecommerce.payload;

import com.ecommerce.model.Complaint;
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
public class ComplaintImageResponse {
	
     private String id;
	
	  private String imageName;
	
	
	   private Complaint complaint;


}
