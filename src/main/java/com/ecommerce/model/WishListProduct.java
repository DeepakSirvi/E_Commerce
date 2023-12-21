package com.ecommerce.model;

import java.util.List;

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
public class WishListProduct extends Audit {
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private Long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Varient varient;

	

}
