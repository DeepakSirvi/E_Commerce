package com.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

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
public class VarientCategoryAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)

	private String id;

	@Column(nullable = true)
	private String attributeName;

	@ManyToOne
	@JoinColumn(nullable = true)
	private VarientCategory varientCategory;

	@OneToMany(mappedBy = "varAttribute")
	private Set<VarientCategoryJoin> categoryJoins = new HashSet<>();
}
