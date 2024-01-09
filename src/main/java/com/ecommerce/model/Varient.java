package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.payload.VarientResponse;

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
public class Varient extends Audit {
	
	public Varient(String id2) {
		this.id=id2;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)

	private String id;
	@Column(unique = true)
	private String varientName;
	private Float price;
	private Integer stock;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private Product product;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "varient_Id")
	private List<VarientCategoryJoin> categoryJoins;
	
	@OneToMany(mappedBy = "varientImage",cascade = CascadeType.ALL)
	private List<ProductImage> productImage = new ArrayList<>();
	
	@OneToMany(mappedBy = "varient") 
	private List<Cart> cart;
	
	@OneToMany(mappedBy = "varient")
	private List<WishListProduct> wishList;
	
	@OneToMany(mappedBy = "varient")
	private List<ProductSaveForLater> saveLater;
	
}
