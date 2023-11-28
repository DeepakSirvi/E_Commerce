package com.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Long id;
	
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
