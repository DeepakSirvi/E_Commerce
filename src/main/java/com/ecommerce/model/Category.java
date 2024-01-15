package com.ecommerce.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Category extends Audit{

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String categoryName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_Id")
	private List<SubCategory> subCategory=new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	public Category(String id){
		this.id=id;
	}
}
