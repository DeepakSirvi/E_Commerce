package com.ecommerce.model;

import java.util.Set;

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
public class TermsAndCondition extends Audit {
	@Id
	private Long id;
	
	private String title;
	
	
	@OneToMany(mappedBy = "terms")
	private Set<TermDescription> desc;
	
	@ManyToOne
	private User user;

}
