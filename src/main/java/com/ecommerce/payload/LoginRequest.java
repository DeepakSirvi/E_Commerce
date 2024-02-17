package com.ecommerce.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

	@NotNull
	@Max(value = 9999)
	@Min(value = 1000)
	private Integer otp;
	@NotBlank
	@Pattern(regexp = "^\\d{10}$")
	private String mobileNumber;
}
