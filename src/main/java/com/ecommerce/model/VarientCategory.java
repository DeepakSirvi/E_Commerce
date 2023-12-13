package com.ecommerce.model;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class VarientCategory {
	
	public VarientCategory(Long id) {
		this.id=id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "varient_Category_Id")
	private Set<VarientCategoryAttribute> categoryAttributes = new HashSet<>();
	
	@ManyToOne
	private User user;



}
