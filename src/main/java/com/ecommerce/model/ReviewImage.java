package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ReviewImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String imageName;
	
	@ManyToOne
	private ProductReview imageReview;
}
