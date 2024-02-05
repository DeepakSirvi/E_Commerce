package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	
	@Column(unique = true,nullable = false)
	private String userMobile;
	
	@Column(unique = true,nullable=false )
	private String userEmail;
	
	@Column(nullable = false)
	private String firstName;
	
	private String LastName;
	
	private String gender;
	
	@Column(updatable = false,nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = "user")
	private List<UserRole> userRole = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<Category> category;
	
	@OneToMany(mappedBy = "user")
	private List<VarientCategory> varientCategory;


	
	@OneToMany(mappedBy="userAddress")
	private List<Address> userAddress = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List <Identity> user= new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<Account> accountId = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "vendor")
	private List<Product> product = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<Cart> cart;
	
	
	@OneToMany(mappedBy = "user")
	private List<ProductReview> productReview;
	
	@OneToMany(mappedBy = "user")
	private List<ProductSaveForLater> saveLater;
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private List<Notifications> notification;
	
	@OneToMany(mappedBy = "user")
	private List<TermsAndCondition> termsAndConditons;
	
	@OneToMany(mappedBy = "user")
	private List<Orders> order;

	public User(String id){
		this.id=id;		
	}

	public WishListProduct getWishlist() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
