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
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(length=10)
	private Integer otp;
	private LocalDateTime createdAt;
	private LocalDateTime experiedAt;
	private String phoneNumber;
}
