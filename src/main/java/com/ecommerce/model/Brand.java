package com.ecommerce.model;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Brand extends Audit {
	
	 @Id 
     @GeneratedValue(strategy = GenerationType.UUID )
	
	private String  id;

	@Column(unique=true)
    private String brandName;
    
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)

	private Status status; 
	
	private String brandImage;
	
	@Column(length=100000)
	private String brandDescription;
    
    @ManyToOne
    private User user ;

//    @OneToMany(mappedBy = "brand")
//    private List<Product> products;


    


}
