package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private Integer numberOfStar;
	private String title;

	@Column(length = 10000)
	private String description;

	@OneToMany(mappedBy = "imageReview", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "imageReview" })
	private List<ReviewImage> image = new ArrayList<>();;

	@ManyToOne
	private User user;

	@ManyToOne
	// @JsonIgnore
	private Product product;
}
