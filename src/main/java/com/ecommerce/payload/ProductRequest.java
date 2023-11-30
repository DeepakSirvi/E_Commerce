package com.ecommerce.payload;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProductRequest {
	
	private Long id;
	private String productName;
	private Boolean listingStatus;
	private String brand;
	private String fullfillmentBy;
	private String shippingProvider;
	private Float deliveryCharge;
	private Float productWeight;
	private Float productLength;
	private Float productWidth;
	private Float productHeight;
	private String taxCode;
	private String countryOfOrigin;
	private String productType;
    
	private SubCategoryRequest subCategory;
	private Set<VarientRequest> varient;
	private ProductDescriptionRequest description;

}
