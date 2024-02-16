package com.ecommerce.payload;

import com.ecommerce.model.Status;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String mobile;
	@NotBlank
	private Integer pincode;
	@NotBlank
	private String locality;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	@NotBlank
	private String landMark;
	private String alternateMobile;
	@NotBlank
	private String addressType;
	@NotBlank
	private Status status;

}
