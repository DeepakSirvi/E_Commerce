package com.ecommerce.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(unique = true)
	private String productName;
	private Boolean listingStatus;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
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

	@ManyToOne
	private User vendor;

	@ManyToOne
	private SubCategory subCategory;

	private String productImage;

	private Float basicPrice;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "description_Id", referencedColumnName = "id")
	private ProductDescription description;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Varient> varient;

	@OneToMany(mappedBy = "product")
	private List<ProductReview> productReview;

	@OneToMany(mappedBy = "product")
	private List<Complaint> complaint;

	@OneToMany(mappedBy = "product")
	private List<ProductFAQ> faq;

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItem;

	public Product(String id) {
		this.id = id;
	}

}
