package com.ecommerce.payload;

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
	private String deliveryCharge;
	private String productWeight;
	private String productLength;
	private String productWidth;
	private String productHeight;
	private String taxCode;
	private String countryOfOrigin;
	private String productType;
    private SubCategoryRequest subCategory;
	private ProductDescriptionRequest description;
	
	private String productImage;
	
	private Float basicPrice;

}
