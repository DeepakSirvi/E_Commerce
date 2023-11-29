package com.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends Audit {

	@Id
	private Long id;
	
	@Column(unique = true)
	private String accountNumber;
	private String accountHolderName;
	private String bankName;
	private String bankIFSCcode;
	@Column(unique = true)
	private String venderGSTnumber;
	@Column(unique = true)
	private String panNumber;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private User user;
}
