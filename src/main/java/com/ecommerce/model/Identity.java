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
public class Identity extends Audit{
	
	@Id
	private Long id;
	private String idCardName;
	@Column(unique = true)
	private String idCardNumber;
	private String description;
	private String image;
	
	@ManyToOne
	private User user;
	
	

}
