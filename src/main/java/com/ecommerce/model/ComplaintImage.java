package com.ecommerce.model;

import jakarta.persistence.Entity;
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
public class ComplaintImage {
	
	@Id
	private String id;
	private String imageName;
	
	@ManyToOne
	private Complaint complaint;
}
