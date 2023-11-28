package com.ecommerce.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Address extends Audit {
	
	@Id
	private Long id;
	
	@Column(length=50)
	private String name;
	
	@Column(length=15)
	private String mobile;
	private Integer pincode;
	
	private String locatity;
	
	@Column(length=30)
	private String city;
	@Column(length=30)
	private String state;
	private String landMark;
	@Column(length=15)
	private String alternateMobile;
	
	@Column(length=15)
	private String addressType;
	
	@ManyToOne
	private User userAddress;
	
	@OneToMany(mappedBy = "address")
	private Set<Orders> order;
	
	
}
