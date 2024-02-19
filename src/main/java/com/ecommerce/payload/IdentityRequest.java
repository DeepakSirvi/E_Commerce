package com.ecommerce.payload;

import com.ecommerce.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

	private String id;
	@NotBlank
	private String idCardName;
	@NotBlank
	private String idCardNumber;
	@NotBlank
	private String description;
	@NotBlank
	private String image;
	private Status status;
}
