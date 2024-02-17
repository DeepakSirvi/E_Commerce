package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class VarientCategory extends Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(unique = true, nullable = true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "varient_Category_Id")
	private List<VarientCategoryAttribute> categoryAttributes = new ArrayList<>();

	@ManyToOne
	@JoinColumn(nullable = true)
	private User user;

	public VarientCategory(String id) {
		this.id = id;
	}

}
