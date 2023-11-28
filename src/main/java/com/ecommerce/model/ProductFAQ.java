package com.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class ProductFAQ {
	
	@Id
	private Long id;
	
	@Column(length = 1000)
	private String question;
	
	@Column(length=1000)
	private String answer;
	
	@ManyToOne
	private Product product;

}
