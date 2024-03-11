package com.ecommerce.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Complaint extends Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	
	private String id;
	private String title;
	
	
	@Column(length = 1000)
	private String description;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	@JsonIgnore
	@ManyToOne
	private Product product;
	
	@JsonIgnore
	@OneToMany(mappedBy = "complaint")
	private List<ComplaintImage> image;


	

}
