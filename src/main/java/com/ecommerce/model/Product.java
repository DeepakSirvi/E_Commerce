package com.ecommerce.model;

import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String productName;
	private Boolean listingStatus;
	
	private String fullfillmentBy;
	private String shippingPovider;
	private Float deliveryCharge;
	private Float productWeight;
	private Float productLength;
	private Float productWidth;
	private Float productHeight;
	private String taxCode;
	private String countryOfOrigin;
	private String productType;
    
	

	@ManyToOne
	private User vendor;
	
	@ManyToOne
	private SubCategory subCategory;

	
	@OneToOne(mappedBy = "product")
	private ProductDescription description;
	
	@OneToMany(mappedBy = "product") 
	private Set<CartItem> cart;
	
	@OneToMany(mappedBy = "product")
	private Set<ProductReview> productReview;
	
	@OneToMany(mappedBy = "product")
	private Set<Complaint> complaint;
	
	@OneToMany(mappedBy = "product")
	private Set<ProductFAQ> faq;
	
	@OneToMany(mappedBy = "product")
	private Set<WishListProduct> wishList;
	
	@OneToMany(mappedBy = "product")
	private Set<ProductSaveForLater> saveLater;
	
	@OneToMany(mappedBy = "product")
	private Set<OrderItem> orderItem;
	
	@OneToMany(mappedBy = "product")
	private Set<Varient> varient;
}
