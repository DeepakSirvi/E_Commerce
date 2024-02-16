package com.ecommerce.payload;

import jakarta.validation.constraints.NotBlank;
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
public class ProductRequest {

	private String id;
	@NotBlank
	private String productName;
	private Boolean listingStatus;
	@NotBlank
	private String brand;
	@NotBlank
	private String fullfillmentBy;
	@NotBlank
	private String shippingProvider;
	@NotBlank
	private String deliveryCharge;
	@NotBlank
	private String productWeight;
	@NotBlank
	private String productLength;
	@NotBlank
	private String productWidth;
	@NotBlank
	private String productHeight;
	@NotBlank
	private String taxCode;
	@NotBlank
	private String countryOfOrigin;
	@NotBlank
	private String productType;
	@NotBlank
	private String productImage;
	@NotBlank
	private Float basicPrice;

	private SubCategoryRequest subCategory;
	private ProductDescriptionRequest description;
}
