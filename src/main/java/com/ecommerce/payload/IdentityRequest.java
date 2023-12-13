package com.ecommerce.payload;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class IdentityRequest {

	@NotBlank
	private String idCardName;
	@NotBlank
	private String idCardNumber;
	private String description;
	@NotBlank
	private String image;
	private Status status;
	
}
