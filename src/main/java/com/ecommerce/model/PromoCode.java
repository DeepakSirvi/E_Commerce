package com.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PromoCode extends Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String code;
	private Integer discountPrice;
	private Integer discountRate;
	private Integer minPurchase;
	private Integer maxDiscount;
	
	private LocalDateTime startDate; 
	private LocalDateTime endDate; 
	private Integer minUserAllow;
	@Column(length=500)
	private String message;
	
	private String status;
	private String repeatAllow;

}
