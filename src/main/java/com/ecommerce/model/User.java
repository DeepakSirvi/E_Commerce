package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(unique = true, nullable = false)
	private String userMobile;

	@Column(unique = true, nullable = false)
	private String userEmail;

	@Column(nullable = false)
	private String firstName;

	private String LastName;

	@Column(nullable = false)
	private String gender;

	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JsonIgnoreProperties(value = "user")
	@JsonIgnore
	private List<UserRole> userRole = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Category> category;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<VarientCategory> varientCategory;

	@OneToMany(mappedBy = "userAddress")
	@JsonIgnore
	private List<Address> userAddress = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Identity> user = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Account> accountId = new ArrayList<>();

	@OneToMany(mappedBy = "vendor")
	@JsonIgnore
	private List<Product> product = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Cart> cart;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<ProductReview> productReview;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<ProductSaveForLater> saveLater;

	@OneToMany
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private List<Notifications> notification;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<TermsAndCondition> termsAndConditons;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Orders> order;

	public User(String id) {
		this.id = id;
	}

}
