package com.ecommerce.model;

import java.util.List;
import jakarta.persistence.Column;
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
public class Complaint extends Audit {
	
	@Id
	private String id;
	private String title;
	
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private User user;
	
	
	@ManyToOne
	private Product product;
	
	
	@OneToMany(mappedBy = "complaint")
	private List<ComplaintImage> image;


	public void setComplaintImage(String uploadImage) {
		
		
	}

}
