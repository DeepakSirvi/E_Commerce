package com.ecommerce.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Column(nullable = false)
	private Boolean listingStatus;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status verified;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private String fullfillmentBy;
	@Column(nullable = false)
	private String shippingProvider;
	@Column(nullable = false)
	private String deliveryCharge;
	@Column(nullable = false)
	private String productWeight;
	@Column(nullable = false)
	private String productLength;
	@Column(nullable = false)
	private String productWidth;
	@Column(nullable = false)
	private String productHeight;
	@Column(nullable = false)
	private String taxCode;
	@Column(nullable = false)
	private String countryOfOrigin;
	@Column(nullable = false)
	private String productType;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User vendor;

	@ManyToOne
	@JoinColumn(nullable = false)
	private SubCategory subCategory = new SubCategory();

	@Column(nullable = false, unique = true)
	private String productImage;

	@Column(nullable = false)
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

	public Product(String id) {
		this.id = id;
	}

}
