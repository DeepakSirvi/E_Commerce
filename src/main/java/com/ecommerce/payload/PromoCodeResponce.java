package com.ecommerce.payload;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromoCodeResponce {

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
