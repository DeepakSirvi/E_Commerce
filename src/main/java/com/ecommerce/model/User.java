package com.ecommerce.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
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
	private Set<UserRole> userRole = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private Set<Category> category;
	
	@OneToMany(mappedBy = "user")
	private Set<VarientCategory> varientCategory;


	
	@OneToMany(mappedBy="userAddress")
	private Set<Address> userAddress = new HashSet<>();
	
	@OneToOne(mappedBy = "user")
	private Identity userIdentity;
	
	@OneToMany(mappedBy = "user")
	private Set<Account> accountId = new HashSet<>();
	
	
	@OneToMany(mappedBy = "vendor")
	private Set<Product> product = new HashSet<>();
	
	@OneToOne(mappedBy = "user")
	private Cart cart;
	
	
	@OneToMany(mappedBy = "user")
	private Set<ProductReview> productReview;
	
	@OneToMany(mappedBy = "user")
	private Set<ProductSaveForLater> saveLater;
	
	@OneToMany(mappedBy = "user")
	private Set<Notifications> notification;
	
	@OneToMany(mappedBy = "user")
	private Set<TermsAndCondition> termsAndConditons;
	
	@OneToMany(mappedBy = "user")
	private Set<Orders> order;



	
}
