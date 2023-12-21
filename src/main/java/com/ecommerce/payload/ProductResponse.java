package com.ecommerce.payload;

import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.model.Product;
import com.ecommerce.model.ProductDescription;
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
	private ProductDescription description;
	
	public ProductResponse productToProductResponse(Product product) {
		this.setId(product.getId());
		this.setProductName(product.getProductName());
		this.setListingStatus(product.getListingStatus());
		this.setBrand(product.getBrand());
		this.setFullfillmentBy(product.getFullfillmentBy());
		this.setShippingProvider(product.getShippingProvider());
		this.setDeliveryCharge(product.getDeliveryCharge());
		this.setProductWeight(product.getProductWeight());
		this.setProductHeight(product.getProductHeight());
		this.setProductWidth(product.getProductWidth());
		this.setProductLength(product.getProductLength());
		this.setTaxCode(product.getTaxCode());
		this.setCountryOfOrigin(product.getCountryOfOrigin());
		this.setProductType(product.getProductType());
		this.setVarient( product.getVarient().stream()
				.map(varient-> {
					VarientResponse varientResponse=new VarientResponse();
				  return varientResponse.varientToVarientResponse(varient);
				}).collect(Collectors.toSet()));
		return this;
	}
	

	  public ProductResponse productToProductResponseList(Product product) {
		this.setId(product.getId());
		this.setProductName(product.getProductName());
		this.setListingStatus(product.getListingStatus());
		this.setBrand(product.getBrand());
		this.setCountryOfOrigin(product.getCountryOfOrigin());
		this.setProductType(product.getProductType());
		this.setCreatedAt(product.getCreatedAt());
		this.setVerified(product.getVerified());
		return this;
	}
}
