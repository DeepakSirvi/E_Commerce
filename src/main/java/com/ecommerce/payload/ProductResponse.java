package com.ecommerce.payload;

import java.util.Set;

import com.ecommerce.model.Status;
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
public class ProductResponse extends AuditResponse {
	
	private Long id;
	private String productName;
	private Boolean listingStatus;
	private Status verified;
	private String brand;
	private String fullfillmentBy;
	private String shippingProvider;
	private String deliveryCharge;
	private String productWeight;
	private String productLength;
	private String productWidth;
	private String productHeight;
	private String taxCode;
	private String countryOfOrigin;
	private String productType;
    
	private UserResponse vendor;
	private SubCategoryResponse subCategory;
	private Set<VarientResponse> varient;
	private ProductDescriptionResponse description;
}
