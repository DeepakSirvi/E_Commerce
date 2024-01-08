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
public class ProductReview extends Audit {
	
	@Id
	private String id;
	
	private Integer numberOfStar;
	private String title;
	
	@Column(length=10000)
	private String description;
	
	@OneToMany(mappedBy ="imageReview")
	private Set<ReviewImage> image;
		
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Product product;
	

}
