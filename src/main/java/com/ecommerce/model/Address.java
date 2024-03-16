package com.ecommerce.model;

import java.util.List;

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

	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(length = 50, nullable = true)
	private String name;

	@Column(length = 15, nullable = true)
	private String mobile;
	@Column(nullable = true)
	private Integer pincode;

	private String locality;

	@Column(length = 30, nullable = true)
	private String city;
	@Column(length = 30, nullable = true)
	private String state;
	@Column(nullable = true)
	private String landMark;
	@Column(length = 15)
	private String alternateMobile;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private AddressType addressType;

	private boolean status;

	@ManyToOne
	@JoinColumn(nullable = true)
	private User userAddress;

	@OneToMany(mappedBy = "address")
	private List<Orders> order;

}
